public class Coup {

	public enum Direction
    {
        UP,
        RIGHT,
        LEFT,
        DOWN
    };

	private int iCasePleine;
	private int jCasePleine;
	private Direction direction;
	private int poidsCoup;

	//Constructeur de coup.
	public Coup(int iCasePleine, int jCasePleine, Direction direction){

		this.iCasePleine= iCasePleine;
		this.jCasePleine= jCasePleine;
		this.direction = direction;
	}

	public String toString()
    {
        return new String(jCasePleine + " " + iCasePleine + " " + this.direction.name());
    }

	//Methodes liees aux attributs de coup.
	public int getIVide(){
		switch(this.direction){
            case LEFT:
            case RIGHT:
                return this.iCasePleine;
            case DOWN:
                return this.iCasePleine + 2;
            case UP:
                return this.iCasePleine - 2;
            default:
                assert(false);
                return -1;
        }
	}

	public int getJVide()
    {
		switch(this.direction)
        {
            case UP:
            case DOWN:
                return this.jCasePleine;
            case LEFT:
                return this.jCasePleine - 2;
            case RIGHT:
                return this.jCasePleine + 2;
            default:
                assert (false);
                return -1;
        }
    }

	public int getIPleine(){
		return this.iCasePleine;
	}

	public int getJPleine(){
		return this.jCasePleine;
	}

	public Direction getDirection(){
		return this.direction;
	}

	//Methode qui calcule et attribut le poids a un coup.
	public void setPoidsCoup(Solitaire solitaire){

		int poidsDepart=solitaire.getCase(this.iCasePleine, this.jCasePleine).getPoidsCase();
		int poidsInter=solitaire.getCase((getIVide()+this.iCasePleine)/2, (getJVide()+this.jCasePleine)/2).getPoidsCase();
		int poidsArrivee=solitaire.getCase(this.getIVide(), getJVide()).getPoidsCase();
		this.poidsCoup=poidsDepart + poidsInter + poidsArrivee;
	}

	public int getPoidsCoup(){
		return this.poidsCoup;
	}

}
