package Visualization2D;

import Core.*;

import java.text.DecimalFormat;
import java.util.Date;

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

            SolverLargeur solverLargeur = new SolverLargeur(20, 500);
            SolverIterations solver = new SolverIterations(solverLargeur, 5);
            SolverConcurrent solverConcurrent = new SolverConcurrent(solver, 8);
            Thread t = new Thread(new ProgressObserver(solverConcurrent));
            t.start();
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

    private static class ProgressObserver implements Runnable
    {
        private Solver solver;

        public ProgressObserver(Solver s)
        {
            this.solver = s;
        }

        public void run()
        {
            double completion = 0;
            while(completion < 1)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                completion = solver.completion();
                DecimalFormat df = new DecimalFormat("#");
                System.out.println(new Date() +  " - " + df.format(completion * 100) + "% - " + solver.getMeilleurScore());
            }

            System.out.println("Meilleur score : " + solver.getMeilleurScore());
        }
    }
}
