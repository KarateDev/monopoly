package Jeu;

public class ServicePublic extends Compagnie {

    public ServicePublic(int numcase, String nomcarreau) {
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