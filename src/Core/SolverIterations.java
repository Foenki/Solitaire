package Core;

public class SolverIterations extends Solver
{
    private Solver solver;
    private int iterations;
    private int currentIter;

    public SolverIterations(Solver solver, int iterations)
    {
        this.solver = solver;
        this.iterations = iterations;
        this.currentIter = 0;
    }

    public Solver clone()
    {
        return new SolverIterations(solver.clone(), iterations);
    }

    public double completion()
    {
        return Math.min(1, ((double)currentIter + solver.completion()) / (double)iterations);
    }

    protected Chemin doSolve(Solitaire solitaire)
    {
        for(currentIter = 0; currentIter < iterations; ++currentIter)
        {
            Chemin currentChemin = solver.solve(solitaire);

            if(solver.getMeilleurScore() < getMeilleurScore())
            {
                setMeilleurChemin(currentChemin);
                setMeilleurScore(solver.getMeilleurScore());
            }
        }

        return getMeilleurChemin();
    }
}
