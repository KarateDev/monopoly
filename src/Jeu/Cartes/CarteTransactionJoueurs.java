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
public class CarteTransactionJoueurs extends Carte {
	private int montant;

	public CarteTransactionJoueurs(String intitule, int montant) {
		super(intitule);
		this.montant = montant;
	}

	@Override
	public void action(Joueur j, Controleur ctrl) {
		if (this.montant < 0){ //vous donnez de l'argent aux autes joueur
			for (Joueur j2 : ctrl.getMonopoly().getJoueurs()){
				if (!j.equals(j2)){
					j.payerCash(-this.montant);
					j2.recevoirCash(-this.montant);
				}
			}
		} else {	// les autes joueur vous donnent de l'argent
			for (Joueur j2 : ctrl.getMonopoly().getJoueurs()){
				if (!j.equals(j2)){
					j.recevoirCash(this.montant);
					j2.payerCash(this.montant);
				}
			}
		}
		ctrl.getIhm().afficherArgentRestant(j);
	}
}
