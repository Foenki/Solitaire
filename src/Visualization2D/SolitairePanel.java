package Visualization2D;

import Core.Case;
import Core.Solitaire;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;

public class SolitairePanel extends JPanel
{
    private Solitaire solitaire;

    public SolitairePanel(Solitaire solitaire)
    {
        this.solitaire = solitaire;
        setLayout(new GridLayout(solitaire.getDimensions()[0], solitaire.getDimensions()[1]));
        for(int i = 0; i < solitaire.getDimensions()[0]; ++i)
        {
            for(int j = 0; j < solitaire.getDimensions()[1]; ++j)
            {
                Case currentCase = solitaire.getCase(i, j);

                if(currentCase == null)
                {
                    JPanel vide = new JPanel();
                    vide.setOpaque(false);
                    add(vide);
                }
                else
                {
                    add(new CaseLabel(currentCase));
                }
            }
        }

    }

    //Methode qui override la methode paint component pour afficher le plateau de solitaire (vide).
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fill3DRect(0, 0, getWidth(), getHeight(), true);
    }
}
