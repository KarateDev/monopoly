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
public class CarteDeplacementSemiAbsolu extends Carte {
		
	private final String classCible;

	public CarteDeplacementSemiAbsolu(String intitule, String classCible) {
		super(intitule);
		this.classCible = classCible;
	}

	@Override
	public void action(Joueur j, Controleur ctrl) {
		System.out.println("Le joueur se déplace de façon semi-absolue");
		/*deplacement = 0;
		int nombreDeTour = 0;	//pour faire le modulo permetant de rester dans le vecteur

		if (carte.get(2).equals("gare")){	// si on doit aller sur la gare la plus proche
			while (getCarreau(j.getPositionCourante().getNumero() + deplacement).getClass() != Gare.class){
				deplacement ++;
				if (j.getPositionCourante().getNumero() + deplacement > monopoly.getCarreaux().size()){
					deplacement -= monopoly.getCarreaux().size();
					nombreDeTour ++;
				}
			}

		}else if (carte.get(2).equals("compagnie")){	//si on doit aller sur la compagnie la plus proche
			while (getCarreau(j.getPositionCourante().getNumero() + deplacement).getClass() != Compagnie.class){
				deplacement ++;
				if (j.getPositionCourante().getNumero() + deplacement > monopoly.getCarreaux().size()){
					deplacement -= monopoly.getCarreaux().size();
					nombreDeTour ++;
				}
			}

		}

		deplacerJoueur(j, deplacement + nombreDeTour*monopoly.getCarreaux().size());	//deplacement du joueur
		ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));	//affiche le carreau
		interactionCarreau(j);*/
	}
}
