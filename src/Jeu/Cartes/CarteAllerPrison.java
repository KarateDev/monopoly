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
public class CarteAllerPrison extends Carte {

	public CarteAllerPrison(String intitule) {
		super(intitule);
	}

	@Override
	public void action(Joueur j, Controleur ctrl) {
		ctrl.getMonopoly().envoyerEnPrison(j);
	}
	
}
