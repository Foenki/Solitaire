package Visualization2D;

import Core.*;

public class Visualization2D
{
    public static void main(String[] args)
    {
        Solitaire solitaire = new Solitaire("data/boards/classique1.sol");
        try
        {
            Fenetre fenetre = new Fenetre(solitaire);

            SolverLargeur solverLargeur = new SolverLargeur(10, 50);
            SolverIterations solver = new SolverIterations(solverLargeur, 1);
            SolverConcurrent solverConcurrent = new SolverConcurrent(solver, 3);
            Chemin chemin = solverConcurrent.solve(solitaire);

            for(Coup coup : chemin.getCoups())
            {
                Thread.sleep(500);
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
