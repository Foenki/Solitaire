package Visualization2D;

import Core.*;

public class Visualization2D
{
    private static final int DURATION = 20000;
    private static final int MAX_FRAMERATE = 30;

    public static void main(String[] args)
    {
        Solitaire solitaire = new Solitaire("data/boards/board1_concours.sol");
        try
        {
            Fenetre fenetre = new Fenetre(solitaire);

            Solver solver = new SolverMonteCarlo(50);
            SolverIterations solverIt = new SolverIterations(solver, 5);
            SolverConcurrent solverConcurrent = new SolverConcurrent(solverIt, 2);
            ProgressObserver.launch(solverConcurrent);
            Chemin chemin = solverConcurrent.solve(solitaire);

            int interval = Math.max(1000 / MAX_FRAMERATE, DURATION / chemin.size());
            for(Coup coup : chemin.getCoups())
            {
                Thread.sleep(interval);
                solitaire.jouerCoup(coup);
                fenetre.repaint();
            }

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

}
