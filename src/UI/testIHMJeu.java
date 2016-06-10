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
		frame.setSize(1400,900);
		
		// ----------------------------------
	
		Controleur controleur = new Controleur();
	
		controleur.creerPlateau("./src/Data/data.txt");
		controleur.initialiserCartes("./src/Data/dataCartes.txt");
		
		IHMMenu menu = new IHMMenu(controleur);
		

		// ----------------------------------
	
		IHMJeu jeu = new IHMJeu(controleur);
		frame.getContentPane().setLayout(new BorderLayout());
        frame.add(jeu, BorderLayout.CENTER);
		frame.setVisible(true);
    }
    
	
}
