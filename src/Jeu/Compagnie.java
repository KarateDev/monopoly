package Jeu;

public class Compagnie extends Propriete {

    public Compagnie(int numcase, String nomcarreau,int prixachat) {
        super(numcase, nomcarreau, prixachat);
    }

    @Override
    public int calculLoyer(int valeurdes) {
        if (this.getProprietaire().getnbServicePublique() < 2){
            return 4*valeurdes;
        }else{
            return 10*valeurdes;
        }
    }
    
}
