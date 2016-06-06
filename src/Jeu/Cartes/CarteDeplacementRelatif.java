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
public class CarteDeplacementRelatif extends Carte {
	private final int deplacement;

	public CarteDeplacementRelatif(String intitule, int deplacement) {
		super(intitule);
		this.deplacement = deplacement;
	}

	@Override
	public void action(Joueur j, Controleur ctrl) {
		ctrl.deplacerJoueur(j, this.deplacement);
		ctrl.getIhm().afficherCarreau(j.getPositionCourante(), ctrl.getMonopoly().getDes().get(0), ctrl.getMonopoly().getDes().get(1));
		ctrl.interactionCarreau(j);
	}
}