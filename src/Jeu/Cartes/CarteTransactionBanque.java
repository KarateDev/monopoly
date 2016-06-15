package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ParcPublic;
import static UI.Message.*;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CarteTransactionBanque extends Carte {
	private int montant;

	public CarteTransactionBanque(String intitule, int montant) {
		super(intitule);
		this.montant = montant;
	}

	@Override
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		j.recevoirCash(this.montant);
		if(this.montant < 0){
			ParcPublic parc = (ParcPublic) monopoly.getParcPublic();
			parc.encaisser(this.montant);
		}
		observateur.notifier(AFFICHER_ARGENT_RESTANT);
	}
}
