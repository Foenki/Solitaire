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
        SolitaireState root = new SolitaireState();

        Solitaire clone = new Solitaire(solitaire);
        expandNode(clone, root);

        for(currentIteration = 0; currentIteration < iterations; ++currentIteration)
        {
            SolitaireState state = chooseNodeToExpand(root);
            expandNode(clone, state);
        }

        return getMeilleurChemin();
    }

    public void expandNode(Solitaire solitaire, SolitaireState state)
    {
        solitaire.jouerChemin(state.chemin);
        List<Coup> coupsPossibles = getCoupsPossibles(solitaire);

        for (Coup coup : coupsPossibles)
        {
            SolitaireState newChild = new SolitaireState();
            state.addEdge(new GraphEdge(coup, newChild));
            solitaire.jouerCoup(coup);
            runSimulationFromState(solitaire, newChild);
            solitaire.jouerCoupInverse(coup);
        }

        solitaire.jouerCheminInverse(state.chemin);
    }

    private void runSimulationFromState(Solitaire solitaire, SolitaireState state)
    {
        int scoreInitial = solitaire.getScore();
        Chemin cheminAleatoire = new Chemin();
        List< Coup > coupsPossibles = getCoupsPossibles(solitaire);

        while(coupsPossibles.size() > 0)
        {
            Coup coup = coupsPossibles.get((int)(Math.random() * coupsPossibles.size()));
            cheminAleatoire.add(coup);
            solitaire.jouerCoup(coup);
            coupsPossibles = getCoupsPossibles(solitaire);
        }

        state.addScore(scoreInitial - solitaire.getScore());

        if(solitaire.getScore() < getMeilleurScore())
        {
            setMeilleurScore(solitaire.getScore());
            Chemin meilleurChemin = new Chemin();
            meilleurChemin.add(state.chemin);
            meilleurChemin.add(cheminAleatoire);
            setMeilleurChemin(meilleurChemin);
        }

        solitaire.jouerCheminInverse(cheminAleatoire);
    }

    public SolitaireState chooseNodeToExpand(SolitaireState root)
    {
        SolitaireState currentState = root;
        while(!currentState.isLeaf())
        {
            double currentMax = 0;
            SolitaireState currentBest = null;
            for(SolitaireState childState : currentState.getNextStates())
            {
                double averageScore = childState.averageScore();
                if(averageScore > currentMax)
                {
                    currentMax = averageScore;
                    currentBest = childState;
                }
            }

            currentState = currentBest;
        }

        return currentState;
    }


    private class SolitaireState
    {
        public int nbScores;
        public int scoreSum;
        public List< GraphEdge > edges;
        public SolitaireState parent;
        public Chemin chemin;

        public SolitaireState()
        {
            this.nbScores = 0;
            this.scoreSum = 0;
            this.edges = new ArrayList<>();
            this.chemin = new Chemin();
        }

        public void addScore(int score)
        {
            scoreSum += score;
            nbScores++;

            if(parent != null)
            {
                parent.addScore(score);
            }
        }

        public double averageScore()
        {
            return (double)scoreSum / (double)nbScores;
        }

        public void addEdge(GraphEdge edge)
        {
            edges.add(edge);
            edge.endState.parent = this;
            edge.endState.chemin = new Chemin(chemin);
            edge.endState.chemin.add(edge.coup);
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
}
