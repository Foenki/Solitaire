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

	private int pleine;			// 1:pleine; 0:vide
	private int poidsCase = 1;
	
	//Constructeur de case.
	public Case() {
	}

	//Methode qui renvoie un booleen selon que la case est pleine ou non.
	public Boolean isPleine(){
		if(this.pleine==1){
			return true;
		}
		else{
			return false;
		}
	}

	//Methodes liees aux attributs de case.
	public void setPleine(int pleine){
		this.pleine= pleine;
	}
	
	public int getPleine(){
		return this.pleine;
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
		
		if(pleine==1){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			Point2D center = new Point2D.Float(largeur/2, hauteur/2);
		    float radius = largeur/2;
		    float[] dist = {0.0f, 0.2f, 0.6f, 1.0f};
		    Color[] colors = {Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.BLACK};
		    RadialGradientPaint whiteToBlack =
		         new RadialGradientPaint(center, radius,
		                                 dist, colors,
		                                 CycleMethod.NO_CYCLE);
		    g2d.setPaint(whiteToBlack);
			g2d.fillOval(getWidth()/6, getHeight()/6, largeur, hauteur);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(1));
			g.drawOval(getWidth()/6, getHeight()/6, largeur, hauteur);
		}
		else{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			Point2D center = new Point2D.Float(largeur/3, hauteur/3);
		    float radius = largeur;
		    float[] dist = {0.0f, 0.2f, 0.6f, 1.0f};
		    Color[] colors = {Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, Color.WHITE};
		    RadialGradientPaint whiteToBlack =
		         new RadialGradientPaint(center, radius,
		                                 dist, colors,
		                                 CycleMethod.NO_CYCLE);
		    
		    g2d.setPaint(whiteToBlack);
			g2d.fillOval(getWidth()/6, getHeight()/6, largeur, hauteur);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(1));
			g.drawOval(getWidth()/6, getHeight()/6, largeur, hauteur);
		}
	}
	
}
