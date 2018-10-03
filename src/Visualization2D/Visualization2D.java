package Visualization2D;

import Core.Coup;
import Core.Solitaire;

import java.io.IOException;
import java.util.List;

public class Visualization2D
{
    public static void main(String[] args)
    {
        Solitaire solitaire = new Solitaire("data/boards/classique1.sol");
        try
        {
            Fenetre fenetre = new Fenetre(solitaire);

            List<Coup> coups = solitaire.solveLargeur(100,10,50);

            for(Coup coup : coups)
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
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
