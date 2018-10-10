package Tests;

import Core.*;

public class TestSolver
{
    public static void main(String[] args)
    {
        Solitaire solitaire = new Solitaire("data/boards/board1_concours.sol");
        System.out.println("Score initial: " + solitaire.getScore());

        SolverMonteCarlo solver = new SolverMonteCarlo(50);

        ProgressObserver.launch(solver);
        Chemin chemin = solver.solve(solitaire);
        solitaire.jouerChemin(chemin);
    }
}
