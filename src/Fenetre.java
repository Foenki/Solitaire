import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Fenetre extends JFrame{
	private JPanel container = new JPanel();

	//Constructeur de la fenetre qui constitue l'interface graphique du solitaire.
	public Fenetre(Solitaire solitaire) throws InterruptedException{
		this.setTitle("Solitaire");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		container.add(solitaire, BorderLayout.CENTER);;
		this.setSize(solitaire.getDimensions()[1]*40, solitaire.getDimensions()[0]*40);
		this.add(container);
		this.setVisible(true);
	}

}