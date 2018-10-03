package Core;

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
public class Case
{
	private boolean isPleine;
	private int poidsCase = 1;
	
	//Constructeur de case.
	public Case(boolean isPleine)
	{
		this.isPleine = isPleine;
	}

	//Methode qui renvoie un booleen selon que la case est pleine ou non.
	public boolean isPleine()
	{
		return this.isPleine;
	}

	//Methodes liees aux attributs de case.
	public void setPleine(boolean pleine)
	{
		this.isPleine = pleine;
	}

	public void setPoidsCase(int poids)
	{
		this.poidsCase= poids;
	}
	
	public int getPoidsCase()
	{
		return this.poidsCase;
	}
}
