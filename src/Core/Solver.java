package Core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Solver
{
    private Chemin meilleurChemin = new Chemin();
    private int meilleurScore = Integer.MAX_VALUE;

    public Chemin solve(Solitaire solitaire)
    {
        meilleurChemin = new Chemin();
        meilleurScore = Integer.MAX_VALUE;

        return doSolve(solitaire);
    }

    //Methode qui determine les coups possibles suivant les regles du solitaire.
    public List<Coup> getCoupsPossibles(Solitaire solitaire){
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

    public Chemin getMeilleurChemin()
    {
        return meilleurChemin;
    }

    public int getMeilleurScore()
    {
        return meilleurScore;
    }

    public void setMeilleurChemin(Chemin chemin)
    {
        meilleurChemin = chemin;
    }

    public void setMeilleurScore(int score)
    {
        meilleurScore = score;
    }

    protected abstract Chemin doSolve(Solitaire solitaire);
}
