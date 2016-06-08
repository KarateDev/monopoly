/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Jeu.CouleurPropriete;


/**
 *
 * @author sorindoc
 */
public class IHMMenu extends JPanel{
 
    private Controleur controleur;
    
    private JPanel joueurs; 
    private JPanel titre;
    private JPanel pBouttons;
    
    private ArrayList<JPanel> listePanelNom;
    private ArrayList<JTextField> listeChampNom;
    private final CouleurPropriete[] listeCouleurs = {CouleurPropriete.bleuFonce, CouleurPropriete.orange, CouleurPropriete.mauve, CouleurPropriete.violet, CouleurPropriete.bleuCiel, CouleurPropriete.jaune, CouleurPropriete.vert, CouleurPropriete.rouge};
    private final String[] choixCouleur = {"Couleur","Beu Ciel","Orange","Mauve","Violet","Bleu Fonce","Jaune","Vert","Rouge"};
    private ArrayList<CouleurPropriete> couleursSelect;
    private ArrayList<JComboBox> listeChoixCouleurs;
    
    private JButton AjouterJ;
    private JButton RetirerJ;
    private JButton Demarrer;
    
    private int nbJoueurs;

	
    
    
    public IHMMenu(Controleur controleur){
	super();
	this.controleur = controleur;
	
	
	InitUIComponents();
	
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
	JLabel labelTitre = new JLabel("MONOPOLY");
	labelTitre.setForeground(Color.red);
	labelTitre.setFont(new Font(labelTitre.getFont().getName(), labelTitre.getFont().getStyle(), 80));

	titre.add(labelTitre);
	
	this.add(titre, BorderLayout.NORTH);

	
	
	listePanelNom = new ArrayList<>();
	listeChampNom = new ArrayList<>();
	listeChoixCouleurs = new ArrayList<>();
	couleursSelect = new ArrayList<>();
	
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
		demarrer();
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
    
    private JComboBox initComboBox(){
	JComboBox cb = new JComboBox();
	for(int i = 0; i < 9; i++){
	    cb.addItem(choixCouleur[i]);
	}
	return cb;
    }
    
    //vérifie les données entrés par les joueur et lance la partie
    private void demarrer(){
	boolean complet = true;
	    for(int i = 0; i <= (nbJoueurs-1); i++){
		if(listeChampNom.get(i).getText().equals("") && complet){
		    complet = false;
		    
		}
	    }
	    
	couleursSelect.removeAll(couleursSelect);
	boolean couleursAttribués = true;
	boolean	couleurDoublons = true;
	for(int i = 0; i < (nbJoueurs); i++){
	    if(listeChoixCouleurs.get(i).getSelectedIndex() == 0){
		System.out.println(" couleursAttribué = false");
		couleursAttribués = false;
	
	    }else if(couleursSelect.contains(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()])){
		System.out.println(" couleurDoublons = false");
		couleurDoublons = false;
	    }else{
	    couleursSelect.add(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()]);
	    }
	    System.out.println(i);
	}
	
	if(couleursAttribués && couleurDoublons && complet){
	    for(int i = 0; i <= (nbJoueurs-1); i++){
		couleursSelect.add(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()]);
		controleur.initialiserUnJoueur((listeChampNom.get(i).toString()),couleursSelect.get(i));
	    }
	    JOptionPane.showConfirmDialog(   null, 
		"Les joueurs ont été ajoutés", 
		"Dema",
		JOptionPane.DEFAULT_OPTION, 
		JOptionPane.INFORMATION_MESSAGE);
	}else if(!complet && !couleursAttribués){
	    JOptionPane.showConfirmDialog(   null, 
		"Il faut saisir un nom et sélectionner une couleur pour chaque joueurs", 
		"Erreur",
		JOptionPane.DEFAULT_OPTION, 
		JOptionPane.ERROR_MESSAGE);
	}else if(!complet){
	    JOptionPane.showConfirmDialog(   null, 
		"Il faut saisir un nom pour chaque joueur", 
		"Erreur",
		JOptionPane.DEFAULT_OPTION, 
		JOptionPane.ERROR_MESSAGE);
	}else if(!couleursAttribués){
		JOptionPane.showConfirmDialog(   null, 
			"Il faut sélectionner une couleur pour chaque joueur !", 
			"Erreur",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.ERROR_MESSAGE);
	}else if(!couleurDoublons){
		    JOptionPane.showConfirmDialog(   null, 
				    "Deux joueurs ne peuvent pas voir la même couleur !", 
				    "Erreur",
				    JOptionPane.DEFAULT_OPTION, 
				    JOptionPane.ERROR_MESSAGE);
	}
	    
    }
    
    
    
}
		
		
		
		
		
		
		/*
		if(complet){
		    couleursSelect.removeAll(couleursSelect);
		    boolean couleursCorrecte = true;		    
		    for(int i = 0; i < (nbJoueurs-1); i++){
			if(listeChoixCouleurs.get(i).getSelectedIndex() == 0){
			    couleursCorrecte = false;
			}else{
			    if(couleursSelect.contains(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()-1]) && couleursCorrecte){
				    JOptionPane.showConfirmDialog(   null, 
						    "Deux joueurs ne peuvent pas voir la même couleur !", 
						    "Erreur",
						    JOptionPane.DEFAULT_OPTION, 
						    JOptionPane.ERROR_MESSAGE);
				    couleursCorrecte = false;
			    }
			}
			if(couleursCorrecte){
			     couleursSelect.add(listeCouleurs[listeChoixCouleurs.get(i).getSelectedIndex()-1]);
			     controleur.initialiserUnJoueur((listeChampNom.get(i).toString()),couleursSelect.get(i));
			}
		    }
			   
		    System.out.println("Joueurs Ajoutées");
		}else{
		    JOptionPane.showConfirmDialog(   null, 
					"Il faut saisir un nom pour chaque joueur", 
					"Erreur",
					JOptionPane.DEFAULT_OPTION, 
					JOptionPane.ERROR_MESSAGE);
		}
	    
    }
    */
		
		

  
