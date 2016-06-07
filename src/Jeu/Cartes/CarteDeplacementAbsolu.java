/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.Monopoly;
import UI.Message;
import UI.Observateur;

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
	public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		System.out.println("Le joueur se déplace de façon absolue");
		int deplacement = 0;	//calcul du deplacement
		if (caseCible.getNumero() - j.getPositionCourante().getNumero() < 0){  //le deplacement doit etre positif
			deplacement = caseCible.getNumero() - j.getPositionCourante().getNumero() + monopoly.getCarreaux().size();
		}else{
			deplacement = caseCible.getNumero() - j.getPositionCourante().getNumero();
		}

		monopoly.deplacerJoueur(j, deplacement); //deplacement du joueur
		
		Message msg  = new Message(Message.Type.AFFICHER_CARREAU);
		msg.joueur = j;
		msg.de1 = monopoly.getDes().get(0);
		msg.de2 = monopoly.getDes().get(1);
		observateur.notifier(msg);
		
		monopoly.interactionCarreau(j);
	}
}