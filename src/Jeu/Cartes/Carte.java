package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public abstract class Carte {
	private final String intitule;

	Carte(String intitule) {
		this.intitule = intitule;
	}
	public abstract void action(Joueur j, Observateur observateur, Monopoly monopoly);

	/**
	 * @return the intitule
	 */
	public String getIntitule() {
		return intitule;
	}
}
