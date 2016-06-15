package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CarteAllerPrison extends Carte {

	public CarteAllerPrison(String intitule) {
		super(intitule);
	}

	@Override
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		monopoly.envoyerEnPrison(j);
	}
	
}
