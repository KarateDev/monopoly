/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Compagnie;
import Jeu.Gare;
import Jeu.Joueur;
import Jeu.Monopoly;
import static UI.Message.*;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CarteDeplacementSemiAbsolu extends Carte {
		
	private final String classCible;

	public CarteDeplacementSemiAbsolu(String intitule, String classCible) {
		super(intitule);
		this.classCible = classCible;
	}

	@Override
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		System.out.println("Le joueur se déplace de façon semi-absolue");
		int deplacement = 0;
		int nombreDeTour = 0;	//pour faire le modulo permetant de rester dans le vecteur

		if (classCible.equals("gare")){	// si on doit aller sur la gare la plus proche
			while (!(monopoly.getCarreau(j.getPositionCourante().getNumero() + deplacement) instanceof Gare)) {
				deplacement ++;
				if (j.getPositionCourante().getNumero() + deplacement > monopoly.getCarreaux().size()){
					deplacement -= monopoly.getCarreaux().size();
					nombreDeTour ++;
				}
			}

		} else if (classCible.equals("compagnie")){	//si on doit aller sur la compagnie la plus proche
			while (!(monopoly.getCarreau(j.getPositionCourante().getNumero() + deplacement) instanceof Compagnie)) {
				deplacement ++;
				if (j.getPositionCourante().getNumero() + deplacement > monopoly.getCarreaux().size()){
					deplacement -= monopoly.getCarreaux().size();
					nombreDeTour ++;
				}
			}

		}

		monopoly.deplacerJoueur(j, deplacement + nombreDeTour*monopoly.getCarreaux().size());	//deplacement du joueur
		
		observateur.notifier(AFFICHER_CARREAU);
		
		monopoly.interactionCarreau(j);
	}
}
