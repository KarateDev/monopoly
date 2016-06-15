package Jeu;

import java.awt.Color;
import java.util.*;

public class Joueur {

	private Carreau positionCourante;
	private HashSet<Propriete> proprietes;
	private String nomJoueur;
	private int cash = 1500;
	private boolean aFaitUnDouble ;
	
	private int nbCarteLibereDePrison = 0;
	private int nbTourEnPrison = 0;
	private CouleurPropriete couleurJoueur;
        
    public Joueur(String nomjoueur,CouleurPropriete couleur,Carreau depart){ 
        proprietes = new HashSet<>();
        this.nomJoueur = nomjoueur;
        this.positionCourante = depart;
	this.couleurJoueur = couleur;
    }
	
	public int getNbCarteLibereDePrison(){
		return nbCarteLibereDePrison;
	}
	
	public void ajouterCarteLibereDePrison(){
		this.nbCarteLibereDePrison ++;
	}
	
	public void retirerCarteLibereDePrison(){
		this.nbCarteLibereDePrison --;
	}

	/**
	 * @param l
	 */
	public void payerCash(int l) {
            setCash(getCash()-l);
	}
	
	public void payerTaxe(int t) {
	    setCash(getCash() +t);
	}

	/**
	 * 
	 * @param l
	 */
	public void recevoirCash(int l) {
            setCash(getCash()+l);
	}

	public int getnbgare() {
		HashSet proprietes = getProprietes();
                int nbgare = 0;
                for(Propriete p : this.proprietes){
                    if(p.getClass()== Gare.class){
                        nbgare += 1;
                    }
                }
                return nbgare;
	}
        
        public int getnbServicePublique() {
			HashSet proprietes = getProprietes();
            int nbservicepublique = 0;
			for(Propriete p : this.proprietes){
				if(p.getClass()== Compagnie.class) {
					nbservicepublique += 1;
                }
            }
            return nbservicepublique;
	}

	public HashSet<Propriete> getProprietes() {
		return this.proprietes;
	}

	/**
	 * 
	 * @param propriété
	 */
	public void achatPropriété(Propriete propriete) {
		if(getCash() >= propriete.getPrix()){
            propriete.setProprietaire(this);
            addPropriete(propriete);
            setCash(getCash()-propriete.getPrix());
        }
	}
        
    public void achatMaisonSurPropriete(ProprieteAConstruire propriete){
		payerCash(propriete.getPrixBatiment());
		propriete.addMaison();
    }
	
	public boolean asToutesLesProprietesDeCouleur(CouleurPropriete couleur){
		int nbmaisonmemecouleur = 0;
        for (Propriete p : proprietes){                     // pour savoir si le joueur possede tout les terrains de meme couleur
            if (p.getClass() == ProprieteAConstruire.class){
                if (((ProprieteAConstruire) p).getCouleur() == couleur){
                    nbmaisonmemecouleur +=1;
                }
            }
        }
        
        return (nbmaisonmemecouleur == 3 || (couleur == CouleurPropriete.bleuFonce && nbmaisonmemecouleur == 2) || (couleur == CouleurPropriete.mauve && nbmaisonmemecouleur == 2));

	}

	public int getCash() {
		return this.cash;
	}

	public void addPropriete(Propriete propriete) {
		this.proprietes.add(propriete);
	}

	public Carreau getPositionCourante() {
		return this.positionCourante;
	}

	/**
	 * 
	 * @param pos
	 */
	public void setPositionCourante(Carreau pos) {
		this.positionCourante = pos;
	}

	public boolean isAFaitUnDouble() {
	    return aFaitUnDouble;
	}

	public void setAFaitUnDouble(boolean aFaitUnDouble) {
	    this.aFaitUnDouble = aFaitUnDouble;
	}

        /**
         * @return the nomJoueur
         */
        public String getNomJoueur() {
            return nomJoueur;
        }

        /**
         * @param cash the cash to set
         */
        private void setCash(int cash) {
            this.cash = cash;
        }

	/**
	 * @return the nbTourEnPrison
	 */
	public int getNbTourEnPrison() {
		return nbTourEnPrison;
	}

	/**
	 * @param nbTourEnPrison the nbTourEnPrison to set
	 */
	public void setNbTourEnPrison(int nbTourEnPrison) {
		this.nbTourEnPrison = nbTourEnPrison;
	}

	/**
	 * @return the couleurJoueur
	 */
	public CouleurPropriete getCouleurJoueur() {
		return couleurJoueur;
	}
	
	public Color getColorJoueurIhm(){ // pour avoire la couleur du joueur
		switch (getCouleurJoueur()){
			case bleuFonce :
				return Color.blue;
			case orange :
				return Color.orange;
			case mauve :
				return Color.magenta;
			case violet :
				return Color.pink;
			case bleuCiel :
				return Color.cyan;
			case jaune :
				return Color.yellow;
			case vert :
				return Color.green;
			case rouge :
				return Color.red;
				
		}
		return null;
	}
}
