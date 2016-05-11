package NoyauFonctionnel;

import java.util.*;
import NoyauFonctionnel.*;

public class Joueur {

	private Carreau positionCourante;
	HashSet<Propriete> proprietes;
	private String nomJoueur;
	private int cash = 1500;

	/**
	 * 
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
		return this.proprietes;
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

}