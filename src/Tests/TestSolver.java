package Tests;

import Core.Chemin;
import Core.Solitaire;
import Core.SolverMonteCarlo;

public class TestSolver
{
    public static void main(String[] args)
    {
        Solitaire solitaire = new Solitaire("data/boards/classique1.sol");
        SolverMonteCarlo solver = new SolverMonteCarlo(5);

        Chemin chemin = solver.solve(solitaire);
        System.out.println(chemin);
    }
}
