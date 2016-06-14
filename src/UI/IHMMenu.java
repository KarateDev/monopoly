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
import Jeu.CouleurPropriete;


/**
 *
 * @author sorindoc
 */
public class IHMMenu extends JPanel implements Observateur {
 
    private Controleur controleur;
    
    private JPanel joueurs; 
    private JPanel titre;
    private JPanel pBouttons;
    private IHMFrame frame;
    
    private ArrayList<JPanel> listePanelNom;
    private ArrayList<JTextField> listeChampNom;
    private ArrayList<CouleurPropriete> couleursSelect;
    private ArrayList<String> nomSelect;
    private ArrayList<JComboBox> listeChoixCouleurs;
    
    private final CouleurPropriete[] listeCouleurs = {CouleurPropriete.bleuFonce, CouleurPropriete.orange, CouleurPropriete.mauve, CouleurPropriete.violet, CouleurPropriete.bleuCiel, CouleurPropriete.jaune, CouleurPropriete.vert, CouleurPropriete.rouge};
    private final String[] choixCouleur = {"Couleur","Bleu Foncé","Orange","Violet","Rose","Bleu Ciel","Jaune","Vert","Rouge"};
    
    private JButton ajouterJ;
    private JButton retirerJ;
    private JButton enregistrer;
    
    private int nbJoueurs;
    private boolean pret;


    public IHMMenu(Controleur controleur, IHMFrame frame){
		super();
		
		this.controleur = controleur;
		InitUIComponents();
		this.frame = frame;
		pret = false;
    }
	

    
	
	
    //initialise les composants de la fenètre
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
		System.err.println("Erreur: l'image de page de garde ne load pas");
	}

	
	this.add(titre, BorderLayout.NORTH);

	
	
	listePanelNom = new ArrayList<>();
	listeChampNom = new ArrayList<>();
	listeChoixCouleurs = new ArrayList<>();
	couleursSelect = new ArrayList<>();
	nomSelect = new ArrayList<>();
	
	retirerJ = new JButton(" Retirer un joueur ");
	ajouterJ = new JButton(" Ajouter un joueur ");
	enregistrer = new JButton(" Démarrer ");
	
	ajouterJ.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(nbJoueurs < 6){
		    nbJoueurs += 1;
		    joueurs.add(listePanelNom.get(nbJoueurs-1));
		    pret = false;
		    controleur.getMonopoly().getJoueurs().clear();
		    revalidate();
		    repaint();
		}
	    }
	});
	
	retirerJ.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(nbJoueurs >2){
		    joueurs.remove(listePanelNom.get(nbJoueurs-1));
		    listeChampNom.get(nbJoueurs-1).setText("");
		    nbJoueurs -= 1;
		    pret = false;
		    controleur.getMonopoly().getJoueurs().clear();
		    revalidate();
		    repaint();
		}
	    }
	});
	
	enregistrer.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		pret = demarrer();
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
	pBouttons.add(ajouterJ);
	pBouttons.add(retirerJ);
	pBouttons.add(enregistrer);
	
	this.add(pBouttons,BorderLayout.SOUTH);
	

	
	
    }
    
    //initialise les panels qui contiennent les champs de nom des joueurs
    private JPanel initPanelNom(String message){
	JPanel panel = new JPanel();
	JLabel label = new JLabel(message);
	listePanelNom.add(panel);
	JTextField champNom = new JTextField(30);
	listeChampNom.add(champNom);
	JComboBox cb = initComboBox();
	listeChoixCouleurs.add(cb);
	panel.add(label);
	panel.add(champNom);
	panel.add(cb);
	
	return panel;
    }

    //n'est pas utilisé par la classe IHMMenu
    @Override
    public void notifier(Message message) {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // initialise les combobox qui permetent de choisir la couleur du joueurs
    private JComboBox initComboBox(){
	JComboBox cb = new JComboBox();
	for(int i = 0; i < 9; i++){
	    cb.addItem(choixCouleur[i]);
	}
	return cb;
    }
    
    //vérifie les données entrés par les joueur et enregistre les joueurs dans le monopoly
    private boolean demarrer(){
	nomSelect.removeAll(nomSelect);
	boolean complet = true;
	boolean nomDoublons = true;
	//si un joueur n'as pas entrez de nom , complet devient faux
	    for(int i = 0; i <= (nbJoueurs-1); i++){
		if(listeChampNom.get(i).getText().equals("") && complet){
		    complet = false;	    
		}else if(nomSelect.contains(listeChampNom.get(i).getText().toLowerCase())){
		    nomDoublons = false;
		}else{
		    nomSelect.add(listeChampNom.get(i).getText().toLowerCase());
		}
	    }
	    
	couleursSelect.removeAll(couleursSelect);
	boolean couleursAttribues = true; 
	boolean	couleurDoublons = true;
	//parcours les joueurs 
	for(int i = 0; i < (nbJoueurs); i++){
	    // si un joueurs n'a pas elctionner de couleur , couleursAttribues devient faux
	    if(listeChoixCouleurs.get(i).getSelectedIndex() == 0){
		couleursAttribues = false;
	
		//si un joueur choisi la même couleur qu'un autre joueur , couleurDoublons devient faux
	    }else if(couleursSelect.contains(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()-1])){
		couleurDoublons = false;
	    }else{
		// si aucune erreur n'est relevé pour le joueur , on ajoute sa couleur a la liste des couleurs selectionnés
	    couleursSelect.add(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()-1]);
	    }
	}
	// si tout les bouléns sont vrais , les joueurs sont ajouté dans le monopoly, sinon un message d'erreur sera affiché
	if(couleursAttribues && couleurDoublons && complet && nomDoublons){
	    controleur.getMonopoly().getJoueurs().clear();	    
	    int rep = JOptionPane.showConfirmDialog(   null, 
			"Voulez vous lancer la partie ?", 
			"Lancement",
			JOptionPane.OK_OPTION, 
			JOptionPane.QUESTION_MESSAGE);
	    if(rep == 0){
		for(int i = 0; i <= (nbJoueurs-1); i++){
		    couleursSelect.add(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()-1]);
		    controleur.initialiserUnJoueur((listeChampNom.get(i).getText()),couleursSelect.get(i));
		}
		frame.afficherJeu(controleur, frame);
	    }
	}else if(!complet && !couleursAttribues){
	    JOptionPane.showConfirmDialog(   null, 
		"Il faut saisir un nom et séléctionner une couleur pour chaque joueurs", 
		"Erreur",
		JOptionPane.DEFAULT_OPTION, 
		JOptionPane.ERROR_MESSAGE);
	}else if(!complet){
	    JOptionPane.showConfirmDialog(   null, 
		"Il faut saisir un nom pour chaque joueur", 
		"Erreur",
		JOptionPane.DEFAULT_OPTION, 
		JOptionPane.ERROR_MESSAGE);
	}else if(!couleursAttribues){
	    JOptionPane.showConfirmDialog(   null, 
		    "Il faut sélectionner une couleur pour chaque joueur !", 
		    "Erreur",
		    JOptionPane.DEFAULT_OPTION, 
		    JOptionPane.ERROR_MESSAGE);
	}else if(!couleurDoublons){
	    JOptionPane.showConfirmDialog(   null, 
			    "Deux joueurs ne peuvent pas être de la même couleur !", 
			    "Erreur",
			    JOptionPane.DEFAULT_OPTION, 
			    JOptionPane.ERROR_MESSAGE);
	}else if(!nomDoublons){
	    JOptionPane.showConfirmDialog(   null, 
				    "Deux joueurs ne peuvent pas avoir le même nom !", 
				    "Erreur",
				    JOptionPane.DEFAULT_OPTION, 
				    JOptionPane.ERROR_MESSAGE);
	}
	    
	return complet && couleurDoublons && couleursAttribues;
    }

    //retourne le panel contenant les boutons 
    public JPanel getpBouttons() {
	return pBouttons;
    }

    // retourne vrai si les joueurs on été enregistrées et qu'aucun joueur n'a été rajouté
    public boolean isPret() {
	return pret;
    }
    
    
    
    
}
