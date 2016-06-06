/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import UI.Controleur;

/**
 *
 * @author mourerj
 */
public abstract class Carte {
	private final String intitule;

	Carte(String intitule) {
		this.intitule = intitule;
	}
	public abstract void action(Joueur j, Controleur ctrl);

	/**
	 * @return the intitule
	 */
	public String getIntitule() {
		return intitule;
	}
}
