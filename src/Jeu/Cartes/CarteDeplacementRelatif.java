/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import UI.Message;
import static UI.Message.Type.*;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CarteDeplacementRelatif extends Carte {
	private final int deplacement;

	public CarteDeplacementRelatif(String intitule, int deplacement) {
		super(intitule);
		this.deplacement = deplacement;
	}

	@Override
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		monopoly.deplacerJoueur(j, this.deplacement);
		
		Message msg = new Message(AFFICHER_CARREAU);
		msg.joueur = j;
		msg.de1 = monopoly.getDes().get(0);
		msg.de2 = monopoly.getDes().get(1);
		observateur.notifier(msg);

		monopoly.interactionCarreau(j, observateur);
	}
}