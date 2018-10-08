package Core;

public class SolverIterations extends Solver
{
    private Solver solver;
    public int iterations;

    public SolverIterations(Solver solver, int iterations)
    {
        this.solver = solver;
        this.iterations = iterations;
    }

    protected Chemin doSolve(Solitaire solitaire)
    {
        int bestScore = Integer.MAX_VALUE;

        for(int i = 0; i < iterations; ++i)
        {
            Chemin currentChemin = solver.solve(solitaire);

            if(solver.getMeilleurScore() < bestScore)
            {
                bestScore = solver.getMeilleurScore();
                setMeilleurChemin(currentChemin);
                setMeilleurScore(bestScore);
            }
        }

        return getMeilleurChemin();
    }
}
