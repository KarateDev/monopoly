package Jeu;

import java.util.HashSet;

public class Gare extends Propriete {

    public Gare(int numcase, String nomcarreau,int prixachat) {
        super(numcase, nomcarreau,prixachat);
    }

    @Override
    public int calculLoyer(int valeurdes) {
        int nbgare = getProprietaire().getnbgare();
        return 25 * nbgare;
    }
    
}