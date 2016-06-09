/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author bouchval
 */
public class testIHMJeu {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1300,800);
		
		// ----------------------------------
	
		
		Controleur controleur = new Controleur(null);
	
		controleur.creerPlateau("./src/Data/data.txt");
		controleur.initialiserCartes("./src/Data/dataCartes.txt");
		
		// pour les tests -------------------
		controleur.getMonopoly().addJoueur(new Joueur("joueur1", CouleurPropriete.rouge, controleur.getMonopoly().getCarreau(1)));
		controleur.getMonopoly().addJoueur(new Joueur("joueur2", CouleurPropriete.bleuFonce, controleur.getMonopoly().getCarreau(1)));
		controleur.getMonopoly().addJoueur(new Joueur("joueur3", CouleurPropriete.bleuCiel, controleur.getMonopoly().getCarreau(1)));
		controleur.getMonopoly().addJoueur(new Joueur("joueur4", CouleurPropriete.violet, controleur.getMonopoly().getCarreau(1)));

		// ----------------------------------
	
		IHMJeu jeu = new IHMJeu(controleur, controleur.getMonopoly().getCarreaux(), controleur.getMonopoly().getJoueurs(), controleur.getMonopoly().getJoueurs().get(0));
		frame.getContentPane().setLayout(new BorderLayout());
        frame.add(jeu, BorderLayout.CENTER);
		frame.setVisible(true);
    }
    
	
}
