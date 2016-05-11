package Jeu;

import java.util.HashSet;

public class Gare extends Compagnie {

    public Gare(int numcase, String nomcarreau) {
        super(numcase, nomcarreau);
    }

    @Override
    public int calculLoyer(int valeurdes) {
        int nbgare = getProprietaire().getnbgare();
        return 25 * nbgare;
    }
    
}