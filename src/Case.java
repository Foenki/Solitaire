import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Case extends JLabel {

	private boolean isPleine;
	private int poidsCase = 1;

	private static float[] DISTANCES = {0.0f, 0.2f, 0.6f, 1.0f};
	private static Color[] WHITE_TO_BLACK = {Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.BLACK};
    private static Color[] BLACK_TO_WHITE = {Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, Color.WHITE};
	
	//Constructeur de case.
	public Case() {
	}

	//Methode qui renvoie un booleen selon que la case est pleine ou non.
	public boolean isPleine(){
		return this.isPleine;
	}

	//Methodes liees aux attributs de case.
	public void setPleine(boolean pleine){
		this.isPleine = pleine;
	}

	public void setPoidsCase(int poids){
		this.poidsCase= poids;
	}
	
	public int getPoidsCase(){
		return this.poidsCase;
	}
	
	//Methode qui override paintComponent afin d'afficher la representation des cases selon leurs attributs.
	public void paintComponent(Graphics g){ 
		//On centre horizontalement l'image dans la grille
		int largeur=2*getWidth()/3;  
		//On centre verticalement l'image dans la grille
		int hauteur=2*getHeight()/3;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Point2D center;
        float radius;
        Color[] colors;

        if(isPleine){
			center = new Point2D.Float(largeur/2, hauteur/2);
		    radius = largeur/2;
		    colors = WHITE_TO_BLACK;
		}
		else{
			center = new Point2D.Float(largeur/3, hauteur/3);
		    radius = largeur;
		    colors = BLACK_TO_WHITE;
		}


        RadialGradientPaint gradient =
                new RadialGradientPaint(center, radius,
                        DISTANCES, colors,
                        CycleMethod.NO_CYCLE);

        g2d.setPaint(gradient);
        g2d.fillOval(getWidth()/6, getHeight()/6, largeur, hauteur);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g.drawOval(getWidth()/6, getHeight()/6, largeur, hauteur);
	}
	
}
