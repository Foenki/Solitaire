public class Coup {

	private int iCaseVide;
	private int jCaseVide;
	private int iCasePleine;
	private int jCasePleine;
	private String direction;
	private int poidsCoup;

	//Constructeur de coup.
	public Coup(int iCaseVide, int jCaseVide, int iCasePleine, int jCasePleine, String direction){
		this.iCaseVide= iCaseVide;
		this.jCaseVide= jCaseVide;
		this.iCasePleine= iCasePleine;
		this.jCasePleine= jCasePleine;
		this.direction= direction;
	}

	//Methodes liees aux attributs de coup.
	public int getIVide(){
		return this.iCaseVide;
	}

	public int getJVide(){
		return this.jCaseVide;
	}

	public int getIPleine(){
		return this.iCasePleine;
	}

	public int getJPleine(){
		return this.jCasePleine;
	}

	public String getDir(){
		return this.direction;
	}

	//Methode qui calcule et attribut le poids a un coup.
	public void setPoidsCoup(Solitaire solitaire){

		int poidsDepart=solitaire.getCases()[this.iCasePleine][this.jCasePleine].getPoidsCase();
		int poidsInter=solitaire.getCases()[(this.iCaseVide+this.iCasePleine)/2][(this.jCaseVide+this.jCasePleine)/2].getPoidsCase();
		int poidsArrivee=solitaire.getCases()[this.iCaseVide][this.jCaseVide].getPoidsCase();
		this.poidsCoup=poidsDepart + poidsInter + poidsArrivee;
		
	}

	public int getPoidsCoup(){
		return this.poidsCoup;
	}

}
