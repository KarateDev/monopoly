/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author sorindoc
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	// TODO code application logic here
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(800,800);
	
	IHMMenu menu = new IHMMenu();
	
	Controleur controleur = new Controleur();
	controleur.setObservateur(menu);
	menu.setControleur(controleur);

	frame.getContentPane().setLayout(new BorderLayout());
	frame.add(menu, BorderLayout.CENTER);
	frame.setVisible(true);
    }

}
