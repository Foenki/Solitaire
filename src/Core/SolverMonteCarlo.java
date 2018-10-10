package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverMonteCarlo extends Solver
{
    private int currentIteration;
    private int iterations;

    public SolverMonteCarlo(int iterations)
    {
        this.currentIteration = 0;
        this.iterations = iterations;
    }

    public Solver clone()
    {
        return new SolverMonteCarlo(iterations);
    }

    public double completion()
    {
        return (double)currentIteration / (double)iterations;
    }

    public Chemin doSolve(Solitaire solitaire)
    {
        StateGraph graph = new StateGraph();
        for(currentIteration = 0; currentIteration < iterations; ++currentIteration)
        {
            StateChemin stateChemin = graph.chooseNodeToExpand();

            Solitaire clone = new Solitaire(solitaire);
            clone.jouerChemin(stateChemin.chemin);
            List< Coup > coupsPossibles = getCoupsPossibles(clone);

            Coup coupToChild = coupsPossibles.get((int)(Math.random() * coupsPossibles.size()));
            SolitaireState newChild = new SolitaireState();
            stateChemin.state.addEdge(new GraphEdge(coupToChild, newChild));

            clone.jouerCoup(coupToChild);
            Chemin cheminAleatoire = jouerCheminAleatoire(clone);

            newChild.addScore(clone.getScore());

            if(clone.getScore() < getMeilleurScore())
            {
                setMeilleurScore(clone.getScore());
                Chemin meilleurChemin = new Chemin();
                meilleurChemin.add(stateChemin.chemin);
                meilleurChemin.add(coupToChild);
                meilleurChemin.add(cheminAleatoire);
                setMeilleurChemin(meilleurChemin);
            }

        }

        return getMeilleurChemin();
    }

    private Chemin jouerCheminAleatoire(Solitaire clone)
    {
        Chemin result = new Chemin();
        List< Coup > coupsPossibles = getCoupsPossibles(clone);

        while(coupsPossibles.size() > 0)
        {
            Coup coup = coupsPossibles.get((int)(Math.random() * coupsPossibles.size()));
            result.add(coup);
            clone.jouerCoup(coup);
            coupsPossibles = getCoupsPossibles(clone);
        }

        return result;
    }


    private class SolitaireState
    {
        public List< Integer > obtainedScores;
        public List< GraphEdge > edges;
        public SolitaireState parent;

        public SolitaireState()
        {
            this.obtainedScores = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

        public void addScore(int score)
        {
            obtainedScores.add(score);

            if(parent != null)
            {
                parent.addScore(score);
            }
        }

        public double averageScore()
        {
            double result = 0;

            for(Integer score : obtainedScores)
            {
                result += score / obtainedScores.size();
            }

            return result;
        }

        public void addEdge(GraphEdge edge)
        {
            edges.add(edge);
            edge.endState.parent = this;
        }

        public List< SolitaireState > getNextStates()
        {
            ArrayList< SolitaireState > result = new ArrayList<>();

            for(GraphEdge edge : edges)
            {
                result.add(edge.endState);
            }

            return result;
        }

        public boolean isLeaf()
        {
            return edges.isEmpty();
        }
    }

    private class GraphEdge
    {
        public Coup coup;
        public SolitaireState endState;


        public GraphEdge(Coup coup, SolitaireState endState)
        {
            this.coup = coup;
            this.endState = endState;
        }
    }

    private class StateChemin
    {
        public SolitaireState state;
        public Chemin chemin;

        public StateChemin(SolitaireState state, Chemin chemin) {
            this.state = state;
            this.chemin = chemin;
        }
    }

    private class StateGraph
    {
        public SolitaireState root;

        public StateGraph()
        {
            this.root = new SolitaireState();
        }

        public StateChemin chooseNodeToExpand()
        {
            SolitaireState currentState = root;
            Chemin chemin = new Chemin();
            while(!currentState.isLeaf())
            {
                chemin.add(currentState.edges.get(0).coup);
                currentState = currentState.getNextStates().get(0);
            }

            return new StateChemin(currentState, chemin);
        }
    }

}