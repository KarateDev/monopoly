package Jeu;

public class ServicePublique extends Compagnie {

    public ServicePublique(int numcase, String nomcarreau) {
        super(numcase, nomcarreau);
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