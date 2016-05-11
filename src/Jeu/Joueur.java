package Jeu;

import java.util.*;

public class Joueur {

	private Carreau positionCourante;
	private HashSet<Propriete> proprietes;
	private String nomJoueur;
	private int cash = 1500;
        
        public Joueur(String nomjoueur){
            proprietes = new HashSet<>();
            this.nomJoueur = nomjoueur;
        }

	/**
	 * test git
	 * @param l
	 */
	public void payerLoyer(int l) {
		// TODO - implement Joueur.payerLoyer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param l
	 */
	public void recevoirLoyer(int l) {
		// TODO - implement Joueur.recevoirLoyer
		throw new UnsupportedOperationException();
	}

	public int getnbgare() {
		// TODO - implement Joueur.getnbgare
		throw new UnsupportedOperationException();
	}

	public HashSet<Propriete> getproprietes() {
		return this.getProprietes();
	}

	/**
	 * 
	 * @param propriété
	 */
	public boolean achatPropriété(ProprieteAConstruire propriété) {
		// TODO - implement Joueur.achatPropriété
		throw new UnsupportedOperationException();
	}

	public int getCash() {
		return this.cash;
	}

	public void addPropriete() {
		// TODO - implement Joueur.addPropriete
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prop
	 */
	public void payePropriete(Propriete prop) {
		// TODO - implement Joueur.payePropriete
		throw new UnsupportedOperationException();
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

    /**
     * @return the proprietes
     */
    public HashSet<Propriete> getProprietes() {
        return proprietes;
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
    public void setCash(int cash) {
        this.cash = cash;
    }

}