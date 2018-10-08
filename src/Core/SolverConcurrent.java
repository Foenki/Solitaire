package Core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SolverConcurrent extends Solver
{
    private Solver solver;
    private int maxNbThreads;
    private Semaphore semaphore;

    public SolverConcurrent(Solver solver, int maxNbThreads)
    {
        this.solver = solver;
        this.maxNbThreads = maxNbThreads;
        this.semaphore = new Semaphore(1);
    }

    public Solver clone()
    {
        return new SolverConcurrent(solver.clone(), maxNbThreads);
    }

    protected Chemin doSolve(Solitaire solitaire)
    {
        ArrayList< Thread > threads = new ArrayList<Thread>();
        for(int i = 0; i < maxNbThreads; ++i)
        {
            Thread t = new Thread(new RunnableSolve(solver.clone(), solitaire));
            t.start();
            threads.add(t);
        }

        for(int i = 0; i < maxNbThreads; ++i)
        {
            try
            {
                threads.get(i).join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        return getMeilleurChemin();
    }

    private class RunnableSolve implements Runnable
    {
        private Solver solver;
        private Solitaire solitaire;

        public RunnableSolve(Solver solver, Solitaire solitaire)
        {
            this.solver = solver;
            this.solitaire = solitaire;
        }

        public void run()
        {
            Chemin chemin = solver.solve(solitaire);
            int bestScore = solver.getMeilleurScore();

            if(bestScore < getMeilleurScore())
            {
                try
                {
                    semaphore.acquire();
                    if(bestScore < getMeilleurScore())
                    {
                        setMeilleurChemin(chemin);
                        setMeilleurScore(bestScore);
                    }
                    semaphore.release();

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
