/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import static UI.Message.*;
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
		
		observateur.notifier(AFFICHER_CARREAU);

		monopoly.interactionCarreau(j);
	}
}