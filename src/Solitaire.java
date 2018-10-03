import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Solitaire extends JPanel {

	private Case[][] cases;
	private int[] dimensions= new int[2];
	private Semaphore sem;
	
	//Le constructeur standard du plateau de solitaire.
	public Solitaire(){
	}

	//Le constructeur du plateau de solitaire a partir du fichier texte.
	public Solitaire(String f){
		//On lit le fichier contenant les informations sur le plateau.
		try{
			BufferedReader br = new BufferedReader (new InputStreamReader(new FileInputStream(f)));
			String line=br.readLine();

			//On extrait les dimensions du plateau.
			dimensions[0]= Integer.parseInt(line.split(" ")[0]);
			dimensions[1]= Integer.parseInt(line.split(" ")[1]);
			this.cases = new Case[dimensions[0]][dimensions[1]];

			//On remplie le container de cases suivant la disposition GridLayout.
			setLayout(new GridLayout(dimensions[0], dimensions[1]));
			int i=0;
			while((line = br.readLine()) != null){
				int j=0;
				for(String car: line.split(" ")){
					this.cases[i][j]= new Case();
					if(car.equals("#")){
						this.cases[i][j]=null;
						JPanel vide = new JPanel();
						vide.setOpaque(false);
						add(vide);
					}
					if(car.equals("0")){
						this.cases[i][j].setPleine(0);
						add(this.cases[i][j]);

					}
					if(car.equals("1")){
						this.cases[i][j].setPleine(1);
						add(this.cases[i][j]);
					}

					j++;
				}
				i++;
			}
			br.close();

		}
		catch (Exception e){
			System.out.println(e.toString());
		}
		//On attribut un poids a chacune des cases.
		this.setPoidsCasesPleines();
		this.setPoidsCasesVides();

	}

	//Methode utilisee par les IAs pour recopier un autre solitaire.
	public void clone(Solitaire solitaire){
		this.dimensions= solitaire.dimensions;
		this.cases=new Case[dimensions[0]][dimensions[1]];
		for(int i=0; i<this.getDimensions()[0]; i++){
			for(int j=0; j<this.getDimensions()[1]; j++){
				this.cases[i][j]= new Case();
				
				if(solitaire.getCases()[i][j]==null){
					this.cases[i][j]=null;
				}
				else{
					this.cases[i][j].setPleine(solitaire.getCases()[i][j].getPleine());
				}
			}
		}
		this.sem=solitaire.sem;
	}

	//Methode qui attribut un poids a chaque case pleine du solitaire selon l'etat des cases adjacentes.
	public void setPoidsCasesPleines(){
		for(int i=0; i<this.getDimensions()[0]; i++){
			for(int j=0; j<this.getDimensions()[1]; j++){
				if(this.getCases()[i][j]!=null && this.getCases()[i][j].isPleine()){
					int poids=2;
					if((i-1 >= 0 && (this.getCases()[i-1][j]==null || !this.getCases()[i-1][j].isPleine())) || i-1 < 0){
						poids=poids+2;
						if((i-1 >= 0 && this.getCases()[i-1][j]==null) || i-1 < 0){
							poids= poids +2;
						}
					}
					if((j-1 >= 0 && (this.getCases()[i][j-1]==null || !this.getCases()[i][j-1].isPleine())) || j-1 < 0){
						poids=poids+2;
						if((j-1 >= 0 && this.getCases()[i][j-1]==null) || j-1 < 0){
							poids=poids+2;
						}
					}
					if((i+1 < this.getDimensions()[0] && (this.getCases()[i+1][j]==null || !this.getCases()[i+1][j].isPleine())) || i+1>=this.getDimensions()[0]){
						poids=poids+2;
						if((i+1 < this.getDimensions()[0] && this.getCases()[i+1][j]==null) || i+1>=this.getDimensions()[0]){
							poids=poids+2;
						}
					}
					if((j+1 < this.getDimensions()[1] && (this.getCases()[i][j+1]==null || !this.getCases()[i][j+1].isPleine())) || j+1>=this.getDimensions()[1]){
						poids=poids+2;
						if((j+1 < this.getDimensions()[1] && this.getCases()[i][j+1]==null) || j+1>=this.getDimensions()[1]){
							poids=poids+2;
						}
					}
					if(((i-1 >= 0 && (this.getCases()[i-1][j]==null || !this.getCases()[i-1][j].isPleine())) || i-1 < 0)
				    && ((j-1 >= 0 && (this.getCases()[i][j-1]==null || !this.getCases()[i][j-1].isPleine())) || j-1 < 0)
				    && ((j+1 < this.getDimensions()[1] && (this.getCases()[i][j+1]==null || !this.getCases()[i][j+1].isPleine())) || j+1>=this.getDimensions()[1])
				    && ((i+1 < this.getDimensions()[0] && (this.getCases()[i+1][j]==null || !this.getCases()[i+1][j].isPleine())) || i+1>=this.getDimensions()[0])
				    && (((i-1 >= 0 && j-1 >= 0) && (this.getCases()[i-1][j-1]==null || !this.getCases()[i-1][j-1].isPleine())) || (i-1 < 0 && j-1 < 0))
				    && (((i-1 >= 0 && j+1 < this.getDimensions()[1]) && (this.getCases()[i-1][j+1]==null || !this.getCases()[i-1][j+1].isPleine())) || (i-1 < 0 && j+1 >= this.getDimensions()[1]))
				    && (((j-1 >= 0 && i+1 < this.getDimensions()[0]) && (this.getCases()[i+1][j-1]==null || !this.getCases()[i+1][j-1].isPleine())) || (j-1 < 0 && i+1 >= this.getDimensions()[0]))
				    && (((i+1 < this.getDimensions()[0] && j+1 < this.getDimensions()[1]) && (this.getCases()[i+1][j+1]==null || !this.getCases()[i+1][j+1].isPleine())) || (i+1 >= this.getDimensions()[0] && j+1 >= this.getDimensions()[1]))
					&& ((i-2 >= 0 && (this.getCases()[i-2][j]==null || !this.getCases()[i-2][j].isPleine())) || i-2 < 0)
					&& ((j-2 >= 0 && (this.getCases()[i][j-2]==null || this.getCases()[i][j-2].isPleine())) || j-2 < 0)
					&& ((j+2 < this.getDimensions()[1] && (this.getCases()[i][j+2]==null || !this.getCases()[i][j+2].isPleine())) || j+2>=this.getDimensions()[1])
					&& ((i+2 < this.getDimensions()[0] && (this.getCases()[i+2][j]==null || !this.getCases()[i+2][j].isPleine())) || i+2>=this.getDimensions()[0])){
						poids=poids + 4;
					}
					this.getCases()[i][j].setPoidsCase(poids);
				}
			}
		}
	}

	//Methode qui attribut un poids a chaque case vide du solitaire selon l'etat (et le poids) des cases adjacentes.
	public void setPoidsCasesVides(){
		for(int i=0; i<this.getDimensions()[0]; i++){
			for(int j=0; j<this.getDimensions()[1]; j++){
				if(this.getCases()[i][j]!=null && !this.getCases()[i][j].isPleine()){
					int poids=2;
					if(i-1 >= 0 && this.getCases()[i-1][j]!=null && this.getCases()[i-1][j].isPleine()){
						poids=poids + this.getCases()[i-1][j].getPoidsCase();
					}
					if(j-1 >= 0 && this.getCases()[i][j-1]!=null && this.getCases()[i][j-1].isPleine()){
						poids=poids + this.getCases()[i][j-1].getPoidsCase();
					}
					if(i+1 < this.getDimensions()[0] && this.getCases()[i+1][j]!=null && this.getCases()[i+1][j].isPleine()){
						poids=poids + this.getCases()[i+1][j].getPoidsCase();
					}
					if(j+1 < this.getDimensions()[1] && this.getCases()[i][j+1]!=null && this.getCases()[i][j+1].isPleine()){
						poids=poids + this.getCases()[i][j+1].getPoidsCase();
					}
					this.getCases()[i][j].setPoidsCase(poids);
				}
			}
		}
	}

	//Methodes liees aux attributs du plateau.
	public int[] getDimensions(){
		return this.dimensions;
	}

	public Case[][] getCases(){
		return this.cases;
	}

	public Semaphore getSemaphore(){
		return this.sem;
	}

	//Methode qui calcule le score du solitaire courant en comptant le nombre de cases pleines.
	public int getScore(){
		int score=0;
		for(int i=0; i<this.getDimensions()[0]; i++){
			for(int j=0; j<this.getDimensions()[1]; j++){
				if(this.getCases()[i][j]!=null && this.getCases()[i][j].isPleine()){
					score++;
				}
			}
		}
		return score;
	}

	public int getPoidsSolitaire(){
		int poids=0;
		for(int i=0; i<this.getDimensions()[0]; i++){
			for(int j=0; j<this.getDimensions()[1]; j++){
				if(this.getCases()[i][j]!=null){
					poids= poids+ this.getCases()[i][j].getPoidsCase();
				}
			}
		}
		return poids;
	}

	//Methode qui override la methode paint component pour afficher le plateau de solitaire (vide).
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fill3DRect(0, 0, getWidth(), getHeight(), true); 
	}

	//Methode permettant de realiser le coup.
	public void jouerCoup(Coup coup){
		this.getCases()[coup.getIVide()][coup.getJVide()].setPleine(1);
		this.getCases()[(coup.getIVide()+coup.getIPleine())/2][(coup.getJVide()+coup.getJPleine())/2].setPleine(0);
		this.getCases()[coup.getIPleine()][coup.getJPleine()].setPleine(0);
	}

	//Methode permettant de realiser le coup inverse.
	public void jouerCoupInverse(Coup coup){
		this.getCases()[coup.getIVide()][coup.getJVide()].setPleine(0);
		this.getCases()[(coup.getIVide()+coup.getIPleine())/2][(coup.getJVide()+coup.getJPleine())/2].setPleine(1);
		this.getCases()[coup.getIPleine()][coup.getJPleine()].setPleine(1);
	}

	//Methode de resolution en parcours en profondeur multithread.
	public void solveMulti(int iteration, int pas, int faisceau) throws InterruptedException, IOException{
		List<Coup> meilleurChemin= new ArrayList<Coup>();
		List<IA> ias= new ArrayList<IA>();
		int meilleurScore= (int) Double.POSITIVE_INFINITY;
		this.sem = new Semaphore(1-iteration, true);
		IA.setPas(pas);
		IA.setFaisceau(faisceau);
		for(int i=0; i<iteration; i++){
			IA ia=new IA(this);
			ias.add(ia);
			ia.run();
		}
		this.sem.acquire();
		for(IA ia: ias){
			if(ia.getMeilleurScore()<meilleurScore){
				meilleurScore=ia.getMeilleurScore();
				meilleurChemin.clear();
				meilleurChemin=new ArrayList<Coup>(ia.getMeilleurChemin());
			}
		}
		System.out.println(meilleurScore);
		FileWriter rapport = new FileWriter("liste_coup.txt", false);
		for(Coup coup: meilleurChemin){
			rapport.write((coup.getJPleine())+ " " + (coup.getIPleine()) + " " + coup.getDir() + "\n");
		}
		rapport.close();
		Fenetre f=new Fenetre(this);
		for(Coup coup: meilleurChemin){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
			}
			this.jouerCoup(coup);
			f.repaint();
		}
	}

	//Methode de resolution par parcours en profondeur.
	public void solveProfondeur(int iteration, int pas, int faisceau) throws InterruptedException, IOException{
		Date debut= new Date();
		System.out.println("Debut: " + debut);
		List<Coup> meilleurChemin= new ArrayList<Coup>();
		List<Coup> chemin= new ArrayList<Coup>();
		int meilleurScore= (int) Double.POSITIVE_INFINITY;
		IA.setPas(pas);
		IA.setFaisceau(faisceau);
		for(int i=0; i<iteration; i++){
			IA ia=new IA(this);
			chemin=ia.lancerRecherche(this, pas, faisceau);
			System.out.println("Score de l'IA: " + ia.getMeilleurScore());
			if(ia.getMeilleurScore()<meilleurScore){
				meilleurChemin=chemin;
				meilleurScore=ia.getMeilleurScore();
				System.out.println("Meilleur Score: " + meilleurScore);
			}
		}
		System.out.println(meilleurScore);
		Date fin= new Date();
		System.out.println("Fin: " + fin);
		FileWriter rapport = new FileWriter("liste_coup.txt", false);
		for(Coup coup: meilleurChemin){
			rapport.write((coup.getJPleine())+ " " + (coup.getIPleine()) + " " + coup.getDir() + "\n");
		}
		rapport.close();
		Fenetre f=new Fenetre(this);
		for(Coup coup: meilleurChemin){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
			}
			this.jouerCoup(coup);
			f.repaint();
		}
	}

	//Methode de resolution par parcours en largeur.
	public void solveLargeur(int iteration, int pas, int faisceau) throws InterruptedException, IOException{
		Date debut= new Date();
		System.out.println("Debut: " + debut);
		List<Coup> meilleurChemin= new ArrayList<Coup>();
		List<Coup> chemin= new ArrayList<Coup>();
		int meilleurScore= (int) Double.POSITIVE_INFINITY;
		IA.setPas(pas);
		IA.setFaisceau(faisceau);
		for(int i=0; i<iteration; i++){
			IA ia=new IA(this);
			chemin=ia.lancerRechercheLargeur(pas, faisceau);
			if(ia.getMeilleurScore()<meilleurScore){
				meilleurChemin=chemin;
				meilleurScore=ia.getMeilleurScore();
				System.out.println("Meilleur Score: " + meilleurScore);
			}
		}
		System.out.println("Meilleur Score Final: " + meilleurScore);
		Date fin= new Date();
		System.out.println("Fin: " + fin);
		
		FileWriter rapport = new FileWriter("liste_coup.txt", false);
		for(Coup coup: meilleurChemin){
			rapport.write((coup.getJPleine())+ " " + (coup.getIPleine()) + " " + coup.getDir() + "\n");
		}
		rapport.close();
		
		Fenetre f=new Fenetre(this);
		for(Coup coup: meilleurChemin){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}
			this.jouerCoup(coup);
			f.repaint();
		}
	}
}





