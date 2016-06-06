/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Carreau;
import Jeu.Joueur;
import UI.Controleur;

/**
 *
 * @author mourerj
 */
public class CarteDeplacementAbsolu extends Carte {
	
	private final Carreau caseCible;

	public CarteDeplacementAbsolu(String intitule, Carreau cible) {
		super(intitule);
		this.caseCible = cible;
	}

	@Override
	public void action(Joueur j, Controleur ctrl) {
		System.out.println("Le joueur se déplace de façon absolue");
		/*int deplacement = 0;	//calcul du deplacement
		if (Integer.valueOf(carte.get(2)) - j.getPositionCourante().getNumero() < 0){  //le deplacement doit etre positif
			deplacement = Integer.valueOf(carte.get(2)) - j.getPositionCourante().getNumero() + monopoly.getCarreaux().size();
		}else{
			deplacement = Integer.valueOf(carte.get(2)) - j.getPositionCourante().getNumero();
		}

		deplacerJoueur(j, deplacement); //deplacement du joueur
		ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));
		interactionCarreau(j);*/
	}
}
