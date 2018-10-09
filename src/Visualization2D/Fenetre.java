package Visualization2D;

import Core.Solitaire;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Fenetre extends JFrame
{
	//Constructeur de la fenetre qui constitue l'interface graphique du solitaire.
	public Fenetre(Solitaire solitaire) throws InterruptedException
	{
		this.setTitle("Solitaire");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel();
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		container.add(new SolitairePanel(solitaire), BorderLayout.CENTER);
        this.add(container);

		this.setSize(solitaire.getDimensions()[1]*40, solitaire.getDimensions()[0]*40);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		this.setVisible(true);
	}

}
