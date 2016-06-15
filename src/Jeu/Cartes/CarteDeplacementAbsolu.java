package Jeu.Cartes;

import Jeu.Carreau;
import Jeu.Joueur;
import Jeu.Monopoly;
import static UI.Message.*;
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
		int deplacement = 0;	//calcul du deplacement
		if (caseCible.getNumero() - j.getPositionCourante().getNumero() < 0){  //le deplacement doit etre positif
			deplacement = caseCible.getNumero() - j.getPositionCourante().getNumero() + monopoly.getCarreaux().size();
		}else{
			deplacement = caseCible.getNumero() - j.getPositionCourante().getNumero();
		}

		monopoly.deplacerJoueur(j, deplacement); //deplacement du joueur
		
		observateur.notifier(AFFICHER_CARREAU);
		
		monopoly.interactionCarreau(j);
		
		System.out.println("Le joueur se déplace de façon absolue sur le carreau "+ j.getPositionCourante().getNomCarreau());
		System.out.println("deplacement =  "+ deplacement);
	
	}
}
