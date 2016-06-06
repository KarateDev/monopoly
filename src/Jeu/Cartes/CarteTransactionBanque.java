/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.ParcPublic;
import UI.Controleur;

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
	public void action(Joueur j, Controleur ctrl) {
		System.out.println("Il y a transaction entre le joueur et la banque");
		j.recevoirCash(this.montant);
		if(this.montant < 0){
			ParcPublic parc = (ParcPublic) ctrl.getMonopoly().getParcPublic();
			parc.encaisser(this.montant);
		}
		ctrl.getIhm().afficherArgentRestant(j);
	}
}
