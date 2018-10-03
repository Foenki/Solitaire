import java.util.ArrayList;
import java.util.List;

public class IA extends Thread{
	
	private List<Coup> meilleurChemin= new ArrayList<Coup>();
	private int meilleurScore=(int) Double.POSITIVE_INFINITY;
	private static int pas;
	private static int faisceau;
	private Solitaire solitaire;

	//Constructeur de la classe IA qui clone le solitaire initial et le met en attribut.
	public IA(Solitaire solitaire){
		this.solitaire= new Solitaire();
		this.solitaire.clone(solitaire);
	}

	//Methode permettant la mise a jour du meilleur score.
	public void setMeilleurScore(Solitaire solitaire){
		this.meilleurScore=solitaire.getScore();
	}

	//Methode permettant d'obtenir le meilleur score.
	public int getMeilleurScore(){
		return this.meilleurScore;
	}

	//Methode qui permet d'obtenir le meilleur chemin.
	public List<Coup> getMeilleurChemin(){
		return this.meilleurChemin;
	}

	public static void setPas(int pas){
		IA.pas=pas;
	}

	public static void setFaisceau(int faisceau){
		IA.faisceau=faisceau;
	}

	//Methode qui determine les coups possibles suivant les regles du solitaire.
	public List<Coup> coupsPossibles(Solitaire solitaire){
		List<Coup> coups= new ArrayList<Coup>();
		for(int i=0; i<solitaire.getDimensions()[0]; i++){
			for(int j=0; j<solitaire.getDimensions()[1]; j++){
				if(solitaire.getCase(i, j)!=null && !solitaire.getCase(i, j).isPleine()){
					if(i+2<solitaire.getDimensions()[0] && solitaire.getCase(i+1, j)!=null && solitaire.getCase(i+1, j).isPleine() && solitaire.getCase(i+2, j)!=null && solitaire.getCase(i+2, j).isPleine()){
						Coup coup=new Coup(i+2,j, Coup.Direction.UP);
						coups.add(coup);
					}
					if(i-2>=0 && solitaire.getCase(i-1, j)!=null && solitaire.getCase(i-1, j).isPleine() && solitaire.getCase(i-2, j)!=null && solitaire.getCase(i-2, j).isPleine()){
						Coup coup=new Coup(i-2,j,Coup.Direction.DOWN);
						coups.add(coup);
					}
					if(j+2<solitaire.getDimensions()[1] && solitaire.getCase(i, j+1)!=null && solitaire.getCase(i, j+1).isPleine() && solitaire.getCase(i, j+2)!=null && solitaire.getCase(i, j+2).isPleine()){
						Coup coup= new Coup(i,j+2,Coup.Direction.LEFT);
						coups.add(coup);
					}
					if(j-2>=0 && solitaire.getCase(i, j-1)!=null && solitaire.getCase(i, j-1).isPleine() && solitaire.getCase(i, j-2)!=null && solitaire.getCase(i, j-2).isPleine()){
						Coup coup=new Coup(i,j-2, Coup.Direction.RIGHT);
						coups.add(coup);
					}
				}
			}
		}
		return coups;
	}

	//Methode qui lance la recherche en profondeur du meilleur chemin.
	public List<Coup> lancerRecherche(Solitaire solitaire, int pas, int faisceau){
		List<Coup> chemin=new ArrayList<Coup>();
		IA.pas=pas;
		IA.faisceau=faisceau;
		recherche(solitaire, chemin);
		return this.meilleurChemin;
	}

	//Methode qui realise la recherche en profondeur le meilleur chemin.
	public void recherche(Solitaire solitaire, List<Coup> chemin){ 
		//On initialise les variables du probleme.
		List<Coup> coupsPossibles=this.coupsPossibles(solitaire);
		solitaire.setPoidsCasesPleines();
		solitaire.setPoidsCasesVides();
		double poidsTotal;
		int i=0;
		//On ne visite qu'un certain nombre de plateaux fils (nombre egal a la taille du faisceau de recherche).
		while(i<IA.faisceau && coupsPossibles.size()!=0 && this.meilleurScore>1){
			List<Coup> coupsJoues= new ArrayList<Coup>();
			//On realise un certain nombre de coups par iteration (nombre egal au pas de recherche).
			for(int nbCoups=0; nbCoups<IA.pas; nbCoups++){
				//On met a jour la liste des coups possibles a chaque coup joue.
				coupsPossibles=this.coupsPossibles(solitaire);
				//On tire un coup "au hasard" selon un aleatoire biaise.
				if(coupsPossibles.size()!=0){
					solitaire.setPoidsCasesPleines();
					solitaire.setPoidsCasesVides();
					poidsTotal=0;
					for(Coup coup: coupsPossibles){
						coup.setPoidsCoup(solitaire);
						poidsTotal=poidsTotal + Math.exp(coup.getPoidsCoup());
					}
					double rand= Math.random();
					double poidsCourant=0;
					int j = 0;
					while(j<coupsPossibles.size()){
						poidsCourant=poidsCourant+ Math.exp(coupsPossibles.get(j).getPoidsCoup());
						if(rand <= poidsCourant/poidsTotal){
							solitaire.jouerCoup(coupsPossibles.get(j));
							coupsJoues.add(coupsPossibles.get(j));
							chemin.add(coupsPossibles.get(j));
							break;
						}
						j++;
					}
				}
			}

			//On met a jour le meilleur score et le meilleur chemin si besoin est.
			if(solitaire.getScore()<this.meilleurScore){
				this.setMeilleurScore(solitaire);
				this.meilleurChemin.clear();
				this.meilleurChemin=new ArrayList<Coup>(chemin);
			}

			//On appelle a nouveau la recherche (methode de type backtracking).
			this.recherche(solitaire, chemin);
			//On realise les coups inverses, on supprime ces coups du chemin courant.
			while(coupsJoues.size()!=0){
				solitaire.jouerCoupInverse(coupsJoues.get(coupsJoues.size()-1));
				coupsJoues.remove(coupsJoues.size()-1);
				chemin.remove(chemin.size()-1);

			}
			i++;
		}

	}

	//Methode qui lance la recherche en largeur du meilleur chemin.
	public List<Coup> lancerRechercheLargeur(int pas, int faisceau){
		List<Coup> chemin=new ArrayList<Coup>();
		IA.pas=pas;
		IA.faisceau=faisceau;
		rechercheLargeur(this.solitaire, chemin);
		return this.meilleurChemin;
	}

	//Methode qui realise la recherche en largeur du meilleur chemin.
	public void rechercheLargeur(Solitaire solitaire, List<Coup> chemin){
		//On initialise les variables du probleme.
		List<Coup> coupsPossibles= new ArrayList<Coup>();
		List<Coup> meilleursCoups= new ArrayList<Coup>();
		double poidsTotal;
		int i=0;
		int PPPS=(int) Double.POSITIVE_INFINITY;
		//On ne visite qu'un certain nombre de plateaux fils (nombre egal a la taille du faisceau de recherche).
		while(i<IA.faisceau && this.meilleurScore>1){
			List<Coup> coupsJoues= new ArrayList<Coup>();
			//On realise un certain nombre de coups par iteration (nombre egal au pas de recherche).
			for(int nbCoups=0; nbCoups<IA.pas; nbCoups++){
				//On met a jour la liste des coups possibles a chaque coup joue.
				coupsPossibles=this.coupsPossibles(solitaire);
				//On tire un coup "au hasard" selon un aleatoire biaise.
				if(coupsPossibles.size()!=0){
					solitaire.setPoidsCasesPleines();
					solitaire.setPoidsCasesVides();
					poidsTotal=0;
					for(Coup coup: coupsPossibles){
						coup.setPoidsCoup(solitaire);
						poidsTotal=poidsTotal + Math.exp(coup.getPoidsCoup());
					}
					double rand= Math.random();
					double poidsCourant=0;
					int j = 0;
					while(j<coupsPossibles.size()){
						poidsCourant=poidsCourant+ Math.exp(coupsPossibles.get(j).getPoidsCoup());
						if(rand <= poidsCourant/poidsTotal){
							solitaire.jouerCoup(coupsPossibles.get(j));
							coupsJoues.add(coupsPossibles.get(j));
							break;
						}
						j++;
					}
				}
			}
			//On met a jour le poids des cases.
			solitaire.setPoidsCasesPleines();
			solitaire.setPoidsCasesVides();

			//On met a jour la meilleure solution, les meilleurs coups et le plus grand nombre de coups possibles.
			if((solitaire.getPoidsSolitaire()<PPPS && solitaire.getScore()<=this.meilleurScore) || solitaire.getScore()<this.meilleurScore){
				PPPS=solitaire.getPoidsSolitaire();
				meilleursCoups.clear();
				meilleursCoups= new ArrayList<Coup>(coupsJoues);
				this.setMeilleurScore(solitaire);

			}
			//On joue les coups inverses avant iteration.
			while(coupsJoues.size()!=0){
				solitaire.jouerCoupInverse(coupsJoues.get(coupsJoues.size()-1));
				coupsJoues.remove(coupsJoues.size()-1);
			}
			
			i++;
		}
		//On joue les meilleurs coups determines.
		for(Coup coup: meilleursCoups){
			solitaire.jouerCoup(coup);
			chemin.add(coup);
		}
		//On met a jour le meilleurScore et le meilleurChemin.
		this.setMeilleurScore(solitaire);
		this.meilleurChemin.clear();
		this.meilleurChemin=new ArrayList<Coup>(chemin);
		
		//On rappelle la methode si il reste des coups a jouer.
		if(this.coupsPossibles(solitaire).size()>0){
			rechercheLargeur(solitaire, chemin);
		}

	}

	//Methode run utilisee dans le cas du multithreading
	public void run(){
		this.lancerRecherche(this.solitaire, IA.pas, IA.faisceau);
		solitaire.getSemaphore().release();
	}
}






