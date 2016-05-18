
import UI.Controleur;
import Jeu.Joueur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bouchval
 */
public class jeu {
    
    public static void main(String[] args) {
		// TODO code application logic here
		Controleur controleur = new Controleur();
		controleur.creerPlateau("./data.txt");
		controleur.initialiserUnePartie();
                
                int numeroJoueur = 0;
                while (controleur.getMonopoly().getJoueurs().size() > 1){ //boucle du jeu tant qu'il reste plus un joueur
                    Joueur j = controleur.getMonopoly().getJoueurs().get(numeroJoueur);
                    controleur.jouerUnCoup(j);
                    numeroJoueur += 1;
                    if (numeroJoueur > controleur.getMonopoly().getJoueurs().size()-1){
                        numeroJoueur = 0;
                    }
                }
	}
    
}
