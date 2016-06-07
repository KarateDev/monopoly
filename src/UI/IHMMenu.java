/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *
 * @author sorindoc
 */
public class IHMMenu extends JPanel{
 
    Controleur controleur;
    
    JPanel joueurs; 
    JPanel titre;
    JPanel pBouttons;
    
    ArrayList<JPanel> listePanelNom;
    ArrayList<JTextField> listeChampNom;

    
    JButton AjouterJ;
    JButton RetirerJ;
    JButton Demarrer;
    
    int nbJoueurs;

	
    
    
    public IHMMenu(Controleur controleur){
	super();
	this.controleur = controleur;
    
	InitUIComponents();
	
    }
    
    public void InitUIComponents(){
	this.setLayout(new BorderLayout());
	titre = new JPanel();
	this.add(titre, BorderLayout.NORTH);
	
	joueurs = new JPanel();
	joueurs.setLayout(new GridLayout(8,1));
	JPanel panelVide = new JPanel();
	joueurs.add(panelVide);
	
	JPanel titre = new JPanel();
	try {
		BufferedImage myPicture = ImageIO.read(new File("./src/Data/Monopoly_pack_logo.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		titre.add(picLabel);
	} catch (IOException err) {
		System.err.println("Mdr trolol l'image elle load pas xptdr");
	}

	
	this.add(titre, BorderLayout.NORTH);

	
	
	listePanelNom = new ArrayList<>();
	listeChampNom = new ArrayList<>();
	
	RetirerJ = new JButton(" Retire un joueur ");
	AjouterJ = new JButton(" Ajouter un joueur ");
	Demarrer = new JButton(" Demarrer la partie ");
	
	AjouterJ.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(nbJoueurs < 6){
		    nbJoueurs += 1;
		    joueurs.add(listePanelNom.get(nbJoueurs-1));
		    revalidate();
		    repaint();
		}
	    }
	});
	
	RetirerJ.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(nbJoueurs >2){
		    joueurs.remove(listePanelNom.get(nbJoueurs-1));
		    listeChampNom.get(nbJoueurs-1).setText("");
		    nbJoueurs -= 1;
		    revalidate();
		    repaint();
		}
	    }
	});
	
	Demarrer.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		boolean complet = true;
		for(int i = 0; i <= (nbJoueurs-1); i++){
		    if(listeChampNom.get(i).getText().equals("")){
			complet = false;
		    }
		}
		
		if(complet){
		    for(int i = 0; i < (nbJoueurs-1); i++){
			controleur.initialiserUnJoueur((listeChampNom.get(i).toString()));
		    }
		    
		    System.out.println("Joueurs Ajoutées");
		}else{
		            JOptionPane.showConfirmDialog(   null, 
                                                "Il est impossible d'avoir un nom vide", 
                                                "Erreur",
                                                JOptionPane.DEFAULT_OPTION, 
                                                JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
	    
	
	
	// initialise les champs pour entrée le nom des joueurs et ajoute sur l'écran les deux premier champ de saisie des noms de joueurs

	for(int i = 0; i < 6; i++){
	    initPanelNom("Joueur "+(i+1));
	}
	
	joueurs.add(listePanelNom.get(0));
	joueurs.add(listePanelNom.get(1));
	nbJoueurs = 2;
	

	this.add(joueurs,BorderLayout.CENTER);
	
	pBouttons = new JPanel();
	pBouttons.add(AjouterJ);
	pBouttons.add(RetirerJ);
	pBouttons.add(Demarrer);
	
	this.add(pBouttons,BorderLayout.SOUTH);
	

	
	
    }
    
    private JPanel initPanelNom(String message){
	JPanel panel = new JPanel();
	JLabel label = new JLabel(message);
	listePanelNom.add(panel);
	JTextField champNom = new JTextField(30);
	listeChampNom.add(champNom);
	panel.add(label);
	panel.add(champNom);
	

	
	return panel;
    }
    
  
}
