package Core;

import java.util.ArrayList;
import java.util.List;

public class SolverLargeur extends Solver
{
    private int pas;
    private int dimFaisceau;

    public SolverLargeur(int pas, int dimFaisceau)
    {
        this.pas = pas;
        this.dimFaisceau = dimFaisceau;
    }

    public Solver clone()
    {
        return new SolverLargeur(pas, dimFaisceau);
    }

    protected Chemin doSolve(Solitaire solitaire)
    {
        Chemin chemin = new Chemin();
        Solitaire clone = new Solitaire(solitaire);

        doSolveRecursive(clone, chemin);

        return chemin;
    }

    private void doSolveRecursive(Solitaire solitaire, Chemin chemin)
    {
        //On initialise les variables du probleme.
        List<Coup> coupsPossibles= new ArrayList<Coup>();
        List<Coup> meilleursCoups= new ArrayList<Coup>();
        double poidsTotal;
        int i=0;
        int PPPS=(int) Double.POSITIVE_INFINITY;
        //On ne visite qu'un certain nombre de plateaux fils (nombre egal a la taille du faisceau de recherche).
        while(i<dimFaisceau && getMeilleurScore()>1){
            List<Coup> coupsJoues= new ArrayList<Coup>();
            //On realise un certain nombre de coups par iteration (nombre egal au pas de recherche).
            for(int nbCoups=0; nbCoups<pas; nbCoups++){
                //On met a jour la liste des coups possibles a chaque coup joue.
                coupsPossibles=getCoupsPossibles(solitaire);
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
            if((solitaire.getPoidsSolitaire()<PPPS && solitaire.getScore()<=getMeilleurScore()) || solitaire.getScore()<getMeilleurScore()){
                PPPS=solitaire.getPoidsSolitaire();
                meilleursCoups.clear();
                meilleursCoups= new ArrayList<Coup>(coupsJoues);
                this.setMeilleurScore(solitaire.getScore());

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
        setMeilleurScore(solitaire.getScore());
        setMeilleurChemin(new Chemin(chemin));

        //On rappelle la methode si il reste des coups a jouer.
        if(getCoupsPossibles(solitaire).size()>0){
            doSolveRecursive(solitaire, chemin);
        }
    }
}
