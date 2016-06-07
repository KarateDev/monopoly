/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import UI.Controleur;
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
