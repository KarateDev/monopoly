package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CarteLiberationPrison extends Carte {

	public CarteLiberationPrison(String intitule) {
		super(intitule);
	}

	@Override
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		j.ajouterCarteLibereDePrison();
	}
	
	
}
