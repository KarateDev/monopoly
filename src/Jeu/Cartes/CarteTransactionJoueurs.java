package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import static UI.Message.*;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CarteTransactionJoueurs extends Carte {
	private int montant;

	public CarteTransactionJoueurs(String intitule, int montant) {
		super(intitule);
		this.montant = montant;
	}

	@Override
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		if (this.montant < 0){ //vous donnez de l'argent aux autes joueur
			for (Joueur j2 : monopoly.getJoueurs()){
				if (!j.equals(j2)){
					j.payerCash(-this.montant);
					j2.recevoirCash(-this.montant);
				}
			}
		} else {	// les autes joueur vous donnent de l'argent
			for (Joueur j2 : monopoly.getJoueurs()){
				if (!j.equals(j2)){
					j.recevoirCash(this.montant);
					j2.payerCash(this.montant);
				}
			}
		}
		observateur.notifier(AFFICHER_ARGENT_RESTANT);
	}
}
