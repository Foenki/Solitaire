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

            SolverLargeur solverLargeur = new SolverLargeur(10, 100);
            SolverIterations solver = new SolverIterations(solverLargeur, 5);
            SolverConcurrent solverConcurrent = new SolverConcurrent(solver, 8);
            Chemin chemin = solverConcurrent.solve(solitaire);
            System.out.println("Meilleur score : " + solverConcurrent.getMeilleurScore());

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
