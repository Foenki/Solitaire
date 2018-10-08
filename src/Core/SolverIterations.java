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

    public Solver clone()
    {
        return new SolverIterations(solver.clone(), iterations);
    }

    protected Chemin doSolve(Solitaire solitaire)
    {
        for(int i = 0; i < iterations; ++i)
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
