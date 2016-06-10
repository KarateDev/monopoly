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
	 * MA QUESTION PREFEREE: KESKE JE VAI FER DE TOUTE SETT AUSEILLE ????
     */
    public static void main(String[] args) {
	// TODO code application logic here
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(800,800);
	
	Controleur controleur = new Controleur();
	IHMMenu menu = new IHMMenu(controleur);
	
	controleur.setObservateur(menu);

	frame.getContentPane().setLayout(new BorderLayout());
	frame.add(menu, BorderLayout.CENTER);
	frame.setVisible(true);
    }

}
