package Jeu;

public class ServicePublique extends Propriete {

    public ServicePublique(int numcase, String nomcarreau,int prixachat) {
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