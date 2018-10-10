package Tests;

import Core.Chemin;
import Core.Solitaire;
import Core.SolverConcurrent;
import Core.SolverMonteCarlo;

public class TestSolver
{
    public static void main(String[] args)
    {
        Solitaire solitaire = new Solitaire("data/boards/board1_concours.sol");
        System.out.println("Score initial: " + solitaire.getScore());

        SolverMonteCarlo solver = new SolverMonteCarlo(50);

        Chemin chemin = solver.solve(solitaire);
        System.out.println("Meilleur score : " + solver.getMeilleurScore());
    }
}
