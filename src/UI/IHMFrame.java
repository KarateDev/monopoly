/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.Joueur;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author sorindoc
 */
public class IHMFrame extends JFrame /*implements Observateurimplements Observateur */{
    
    private Controleur controleur;
    private IHMMenu menu;
    private IHMJeu jeu;
    private JButton demarrer;
    private JPanel boutonsMenu;
	private JPanel panelFenetre;
   /* @Override
    public void notifier(Message message) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
    
    public IHMFrame(Controleur controleur){
	super("Monopoly");
	panelFenetre = new JPanel(new BorderLayout());
	this.controleur = controleur;
		
    }
    
    
    public void afficherMenu(Controleur controleur){
	
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(900,900);
	
	menu = new IHMMenu(controleur);
	controleur.setObservateur(menu);
	boutonsMenu = menu.getpBouttons();
	initBoutonDemarrer();
	boutonsMenu.add(demarrer);

	panelFenetre.add(menu, BorderLayout.CENTER);
	this.add(panelFenetre);

	this.setVisible(true);
	
	
    }
    
    
    public void afficherJeu(Controleur controleur){
	controleur.setJoueurCourant(controleur.getMonopoly().getJoueurs().get(0));
	this.setSize(1400,900);
	jeu = new IHMJeu(controleur,this);
	controleur.setObservateur(jeu);
	panelFenetre.removeAll();
	panelFenetre.add(jeu,BorderLayout.CENTER);
	panelFenetre.revalidate();
	
    }
    
    public void initBoutonDemarrer(){
	demarrer = new JButton(" Demarrer ");
	demarrer.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(menu.isPret()){
			System.out.println(controleur.getMonopoly().getJoueurs().get(0).getNomJoueur());
		    System.out.println(controleur.getMonopoly().getJoueurs().get(1).getNomJoueur());
		    afficherJeu(controleur);
		    System.out.println(" pret ");
		    
		}else{
		    JOptionPane.showConfirmDialog(   null, 
				    " Il faut d'abord enregistrer les joueurs !", 
				    "Erreur",
				    JOptionPane.DEFAULT_OPTION, 
				    JOptionPane.ERROR_MESSAGE);
		    System.out.println(" pas pret ");
		}
	    }
	});
	
	menu.getpBouttons().add(demarrer);
	
    }
        
    public static void main(String[] args) {
	    Controleur controleur = new Controleur();
	    IHMFrame ihm = new IHMFrame(controleur);
	    ihm.afficherMenu(controleur);

	    }

   
}
