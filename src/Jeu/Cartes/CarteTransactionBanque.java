/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ParcPublic;
import UI.Message;
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
		System.out.println("Il y a transaction entre le joueur et la banque");
		j.recevoirCash(this.montant);
		if(this.montant < 0){
			ParcPublic parc = (ParcPublic) monopoly.getParcPublic();
			parc.encaisser(this.montant);
		}
		Message msg = new Message(Message.Type.AFFICHER_ARGENT_RESTANT);
		msg.joueur = j;
		observateur.notifier(msg);
	}
}
