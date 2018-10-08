package Core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@SuppressWarnings("serial")
public class Solitaire
{
	private Case[][] cases;
	private int[] dimensions= new int[2];

    private final static char NO_CASE_SYMBOL = '#';
    private final static char EMPTY_CASE_SYMBOL = '0';
    private final static char FULL_CASE_SYMBOL = '1';

	//Le constructeur du plateau de solitaire a partir du fichier texte.
	public Solitaire(String f)
    {
		//On lit le fichier contenant les informations sur le plateau.
		try{
			BufferedReader br = new BufferedReader (new InputStreamReader(new FileInputStream(f)));
			String line=br.readLine();

			//On extrait les dimensions du plateau.
			dimensions[0]= Integer.parseInt(line.split(" ")[0]);
			dimensions[1]= Integer.parseInt(line.split(" ")[1]);
			this.cases = new Case[dimensions[0]][dimensions[1]];
			int i = 0;
			while((line = br.readLine()) != null)
            {
				int j = 0;
				for(String car: line.split(" "))
				{
				    char currentSymbol = car.charAt(0);
					if(currentSymbol == NO_CASE_SYMBOL)
					{
						this.cases[i][j]=null;
					}
					else if(currentSymbol == EMPTY_CASE_SYMBOL)
					{
                        this.cases[i][j] = new Case(false);
					}
					else if(currentSymbol == FULL_CASE_SYMBOL)
					{
                        this.cases[i][j]= new Case(true);
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
	public Solitaire(Solitaire solitaire)
    {
		this.dimensions = solitaire.dimensions;
		this.cases = new Case[dimensions[0]][dimensions[1]];
		for(int i=0; i < dimensions[0]; i++)
		{
			for(int j=0; j < dimensions[1]; j++)
			{
				if(solitaire.getCase(i, j) != null)
				{
					this.cases[i][j]= new Case(solitaire.getCase(i, j).isPleine());
				}
			}
		}
	}

	//Methode qui attribut un poids a chaque case pleine du solitaire selon l'etat des cases adjacentes.
	public void setPoidsCasesPleines(){
		for(int i=0; i<dimensions[0]; i++){
			for(int j=0; j<dimensions[1]; j++){
				if(getCase(i, j)!=null && getCase(i, j).isPleine()){
					int poids=2;
					if((i-1 >= 0 && (getCase(i-1, j)==null || !getCase(i-1, j).isPleine())) || i-1 < 0){
						poids=poids+2;
						if((i-1 >= 0 && getCase(i-1, j)==null) || i-1 < 0){
							poids= poids +2;
						}
					}
					if((j-1 >= 0 && (getCase(i, j-1)==null || !getCase(i, j-1).isPleine())) || j-1 < 0){
						poids=poids+2;
						if((j-1 >= 0 && getCase(i, j-1)==null) || j-1 < 0){
							poids=poids+2;
						}
					}
					if((i+1 < dimensions[0] && (getCase(i+1, j)==null || !getCase(i+1, j).isPleine())) || i+1>=dimensions[0]){
						poids=poids+2;
						if((i+1 < dimensions[0] && getCase(i+1, j)==null) || i+1>=dimensions[0]){
							poids=poids+2;
						}
					}
					if((j+1 < dimensions[1] && (getCase(i, j+1)==null || !getCase(i, j+1).isPleine())) || j+1>=dimensions[1]){
						poids=poids+2;
						if((j+1 < dimensions[1] && getCase(i, j+1)==null) || j+1>=dimensions[1]){
							poids=poids+2;
						}
					}
					if(((i-1 >= 0 && (getCase(i-1, j)==null || !getCase(i-1, j).isPleine())) || i-1 < 0)
				    && ((j-1 >= 0 && (getCase(i, j-1)==null || !getCase(i, j-1).isPleine())) || j-1 < 0)
				    && ((j+1 < dimensions[1] && (getCase(i, j+1)==null || !getCase(i, j+1).isPleine())) || j+1>=dimensions[1])
				    && ((i+1 < dimensions[0] && (getCase(i+1, j)==null || !getCase(i+1, j).isPleine())) || i+1>=dimensions[0])
				    && (((i-1 >= 0 && j-1 >= 0) && (getCase(i-1, j-1)==null || !getCase(i-1, j-1).isPleine())) || (i-1 < 0 && j-1 < 0))
				    && (((i-1 >= 0 && j+1 < dimensions[1]) && (getCase(i-1, j+1)==null || !getCase(i-1, j+1).isPleine())) || (i-1 < 0 && j+1 >= dimensions[1]))
				    && (((j-1 >= 0 && i+1 < dimensions[0]) && (getCase(i+1, j-1)==null || !getCase(i+1, j-1).isPleine())) || (j-1 < 0 && i+1 >= dimensions[0]))
				    && (((i+1 < dimensions[0] && j+1 < dimensions[1]) && (getCase(i+1, j+1)==null || !getCase(i+1, j+1).isPleine())) || (i+1 >= dimensions[0] && j+1 >= dimensions[1]))
					&& ((i-2 >= 0 && (getCase(i-2, j)==null || !getCase(i-2, j).isPleine())) || i-2 < 0)
					&& ((j-2 >= 0 && (getCase(i, j-2)==null || getCase(i, j-2).isPleine())) || j-2 < 0)
					&& ((j+2 < dimensions[1] && (getCase(i, j+2)==null || !getCase(i, j+2).isPleine())) || j+2>=dimensions[1])
					&& ((i+2 < dimensions[0] && (getCase(i+2, j)==null || !getCase(i+2, j).isPleine())) || i+2>=dimensions[0])){
						poids=poids + 4;
					}
					getCase(i, j).setPoidsCase(poids);
				}
			}
		}
	}

	//Methode qui attribut un poids a chaque case vide du solitaire selon l'etat (et le poids) des cases adjacentes.
	public void setPoidsCasesVides(){
		for(int i=0; i<dimensions[0]; i++){
			for(int j=0; j<dimensions[1]; j++){
				if(getCase(i, j)!=null && !getCase(i, j).isPleine()){
					int poids=2;
					if(i-1 >= 0 && getCase(i-1, j)!=null && getCase(i-1, j).isPleine()){
						poids=poids + getCase(i-1, j).getPoidsCase();
					}
					if(j-1 >= 0 && getCase(i, j-1)!=null && getCase(i, j-1).isPleine()){
						poids=poids + getCase(i, j-1).getPoidsCase();
					}
					if(i+1 < dimensions[0] && getCase(i+1, j)!=null && getCase(i+1, j).isPleine()){
						poids=poids + getCase(i+1, j).getPoidsCase();
					}
					if(j+1 < dimensions[1] && getCase(i, j+1)!=null && getCase(i, j+1).isPleine()){
						poids=poids + getCase(i, j+1).getPoidsCase();
					}
					getCase(i, j).setPoidsCase(poids);
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

    public Case getCase(int i, int j){
        return this.cases[i][j];
    }

	//Methode qui calcule le score du solitaire courant en comptant le nombre de cases pleines.
	public int getScore(){
		int score=0;
		for(int i=0; i<dimensions[0]; i++){
			for(int j=0; j<dimensions[1]; j++){
				if(getCase(i, j)!=null && getCase(i, j).isPleine()){
					score++;
				}
			}
		}
		return score;
	}

	public int getPoidsSolitaire(){
		int poids=0;
		for(int i=0; i<dimensions[0]; i++){
			for(int j=0; j<dimensions[1]; j++){
				if(getCase(i, j)!=null){
					poids= poids+ getCase(i, j).getPoidsCase();
				}
			}
		}
		return poids;
	}



	//Methode permettant de realiser le coup.
	public void jouerCoup(Coup coup){
		getCase(coup.getIVide(), coup.getJVide()).setPleine(true);
		getCase((coup.getIVide()+coup.getIPleine())/2, (coup.getJVide()+coup.getJPleine())/2).setPleine(false);
		getCase(coup.getIPleine(), coup.getJPleine()).setPleine(false);
	}

    public void jouerChemin(Chemin chemin)
    {
        for(Coup coup : chemin.getCoups())
        {
            jouerCoup(coup);
        }
    }

	//Methode permettant de realiser le coup inverse.
	public void jouerCoupInverse(Coup coup){
		getCase(coup.getIVide(), coup.getJVide()).setPleine(false);
		getCase((coup.getIVide()+coup.getIPleine())/2, (coup.getJVide()+coup.getJPleine())/2).setPleine(true);
		getCase(coup.getIPleine(), coup.getJPleine()).setPleine(true);
	}

    public void jouerCheminInverse(Chemin chemin)
    {
        for(int i = chemin.size()-1; i >= 0; --i)
        {
            jouerCoupInverse(chemin.getCoup(i));
        }
    }
}





