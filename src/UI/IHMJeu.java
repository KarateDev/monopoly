/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.*;

import Jeu.Joueur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bouchval
 */
public class IHMJeu extends JPanel{
	
	private Controleur controleur;
	
	private int numeroJoueur = 2; // numero du joueur actuel
	
	private Plateau plateau;
	
	private ArrayList<JButton> boutonsJoueurs;
	
	private JLabel labelNom;
	private JLabel labelNom2;
	private JLabel labelArgent;
	
	private JButton boutonLancerDes;
	private JButton boutonTourSuivant;
	
	private JLabel numeroCase;
	private JLabel nomCase;
	private JLabel couleurCase;
	private JLabel proprietaireCase;
	private JLabel nombreBatimentCase;
	private JLabel loyerCase;
	
	private JButton boutonActionCarreau;
	private JButton boutonAbandonner;
	private JButton boutonAide;
	
	private JTable tablePropriete;
	private JButton boutonAcheterBatiment;
	
	private JLabel de1;
	private JLabel de2;
	private JLabel sommeDes;
	
	public IHMJeu(Controleur controleur){
		this.controleur = controleur;
		
		this.setLayout(new BorderLayout(10,10));
		
		initPartiePlateau();
		
		initPartieJeu();
		
		ajouterListner();
		
		initialisationDebutTour();
	}
	

	private void initPartiePlateau() {
		int nbJoueur = controleur.getMonopoly().getJoueurs().size();
		boutonsJoueurs = new ArrayList<>();
		
		
		JPanel panelPlateau = new JPanel(new BorderLayout());
			
			JPanel panelHaut = new JPanel(new GridLayout(2,1));
				panelHaut.add(new JLabel("Autre joueur :"));
				JPanel panelAutreJoueur = new JPanel(new GridLayout(1,nbJoueur-1));
					for (int i = 1; i <= nbJoueur-1 ;i++){
						JButton boutonAutreJoueur = new JButton();
						panelAutreJoueur.add(boutonAutreJoueur);
						boutonsJoueurs.add(boutonAutreJoueur);
					}
				panelHaut.add(panelAutreJoueur);

			panelPlateau.add(panelHaut,BorderLayout.NORTH);
		
			plateau = new Plateau(controleur.getMonopoly().getCarreaux(),controleur.getMonopoly().getJoueurs(),700);
			panelPlateau.add(plateau,BorderLayout.SOUTH);
			
		this.add(panelPlateau,BorderLayout.EAST);
	}


	private void initPartieJeu() {
				
		JPanel panelInteraction = new JPanel(new BorderLayout(0,20));
			
			JPanel panelInteractionCentre = new JPanel(new BorderLayout(0,20));
				JPanel panelHaut = new JPanel(new GridLayout(2,1));
				panelHaut.add(new JLabel("Joueur actuel : "));
					JPanel panelInfoJoueur = new JPanel();
						panelInfoJoueur.setBorder(new LineBorder(Color.black));
						labelNom = new JLabel("Nom :");
						panelInfoJoueur.add(labelNom);
						
						labelNom2 = new JLabel();
						panelInfoJoueur.add(labelNom2);

						labelArgent = new JLabel();
						panelInfoJoueur.add(labelArgent);

						panelHaut.add(panelInfoJoueur);
						
						panelInteraction.add(panelHaut,BorderLayout.NORTH);
			
				JPanel panelInteractionInfoCarreau = new JPanel(new BorderLayout(0,30));
					JPanel panelInteractionInfoCarreauLabel = new JPanel(new GridLayout(6,1,10,10));
					panelInteractionInfoCarreauLabel.setBorder(new TitledBorder("Case actuelle : "));
						numeroCase = new JLabel();
						panelInteractionInfoCarreauLabel.add(numeroCase);
							
						nomCase = new JLabel();
						panelInteractionInfoCarreauLabel.add(nomCase);
						
						proprietaireCase = new JLabel();
						panelInteractionInfoCarreauLabel.add(proprietaireCase);
						
						loyerCase = new JLabel();
						panelInteractionInfoCarreauLabel.add(loyerCase);
							
						couleurCase = new JLabel();
						panelInteractionInfoCarreauLabel.add(couleurCase);
							
						nombreBatimentCase = new JLabel();
						panelInteractionInfoCarreauLabel.add(nombreBatimentCase);
															
						panelInteractionInfoCarreau.add(panelInteractionInfoCarreauLabel,BorderLayout.WEST);
						
					JPanel panelInteractionInfoCarreauBouton = new JPanel(new GridLayout(2,2,20,20));
						boutonLancerDes = new JButton("Lancer les dés");
						panelInteractionInfoCarreauBouton.add(boutonLancerDes);
						
						boutonActionCarreau = new JButton("Action");
						panelInteractionInfoCarreauBouton.add(boutonActionCarreau);
						
						boutonAbandonner = new JButton("Abandonner");
						panelInteractionInfoCarreauBouton.add(boutonAbandonner);
						
						boutonAide = new JButton("Aide");
						panelInteractionInfoCarreauBouton.add(boutonAide);
					
						panelInteractionInfoCarreau.add(panelInteractionInfoCarreauBouton,BorderLayout.EAST);
						
					JPanel panelInfoDes = new JPanel(new GridLayout(1,5));
						panelInfoDes.add(new JPanel());
						
						de1 = new JLabel();
						panelInfoDes.add(de1);
						
						de2 = new JLabel();
						panelInfoDes.add(de2);
						
						sommeDes = new JLabel();
						panelInfoDes.add(sommeDes);
						
						panelInfoDes.add(new JPanel());
					
						panelInteractionInfoCarreau.add(panelInfoDes,BorderLayout.SOUTH);
					
					panelInteractionCentre.add(panelInteractionInfoCarreau,BorderLayout.NORTH);
										
				JPanel panelInteractionListePropriete = new JPanel(new BorderLayout());
					JLabel labelListePropriete = new JLabel("Propriétes du joueur :");
					labelListePropriete.setHorizontalAlignment(JTextField.CENTER);
					panelInteractionListePropriete.add(labelListePropriete,BorderLayout.NORTH);
                
					JPanel panelTableau = new JPanel(new BorderLayout());
					tablePropriete = new JTable();
					panelTableau.add(tablePropriete.getTableHeader(),BorderLayout.NORTH);
					panelTableau.add(new JScrollPane(tablePropriete),BorderLayout.CENTER);
					tablePropriete.setEnabled(true);
					tablePropriete.setColumnSelectionAllowed(false);
					tablePropriete.getTableHeader().setReorderingAllowed(false);
					panelInteractionListePropriete.add(panelTableau,BorderLayout.CENTER);
					
					boutonAcheterBatiment = new JButton("Acheter des batiments");
					panelInteractionListePropriete.add(boutonAcheterBatiment,BorderLayout.SOUTH);
					
					panelInteractionCentre.add(panelInteractionListePropriete,BorderLayout.CENTER);
				
				panelInteraction.add(panelInteractionCentre,BorderLayout.CENTER);			
			
			boutonTourSuivant = new JButton("Tour suivant");
			boutonTourSuivant.setPreferredSize(new Dimension(10, 50));
			panelInteraction.add(boutonTourSuivant,BorderLayout.SOUTH);
		
		this.add(panelInteraction,BorderLayout.CENTER);
		
	}
	
	
	private void ajouterListner() {
		
		for (JButton bouton : boutonsJoueurs){
			bouton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					afficherInfoJoueur(((JButton) e.getSource()).getText());
				}
			});
		}
		
		boutonLancerDes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Joueur joueur = controleur.getMonopoly().getJoueurs().get(numeroJoueur);
				Carreau carreau = joueur.getPositionCourante();
				
				
				
				boutonActionCarreau.setEnabled(false);
				boutonActionCarreau.setEnabled(true);
				if (carreau.getClass() == Propriete.class && ((Propriete)carreau).getProprietaire() == null){
					boutonActionCarreau.setText("Acheter");
				}else if (carreau.getClass() == Propriete.class && ((Propriete)carreau).getProprietaire() != null){
					boutonActionCarreau.setText("Payer le loyer");
				}else if (carreau.getClass() == Pioche.class){
					boutonActionCarreau.setText("Piocher une carte");
				}else if (carreau.getClass() == Prison.class && joueur.getNbTourEnPrison() > 0){
					boutonActionCarreau.setText("Utiliser une carte");
				}else if (carreau.getClass() == Taxe.class){
					boutonActionCarreau.setText("Payer la taxe");
				}else{
					boutonActionCarreau.setText("Aucune action");
					boutonActionCarreau.setEnabled(false);
				}
		
			}
		});
		
		boutonTourSuivant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		boutonActionCarreau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
			
		
		
		boutonAcheterBatiment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		boutonAide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
	}
	
	public void initialisationDebutTour(){
		
		ArrayList<Joueur> joueurs = controleur.getMonopoly().getJoueurs();
		Joueur joueur = joueurs.get(numeroJoueur);
		
		// partie info joueur --------------------------------------------------------------
		
		labelNom2.setText(joueur.getNomJoueur());
		labelNom2.setForeground(joueur.getColorJoueurIhm());
		
		labelArgent.setText(" Argent : "+joueur.getCash()+"€");
		
		// partie info autre joueur ---------------------------------------------------------
		
		for (int i = 0; i < joueurs.size()-1; i++){
			int numero = numeroJoueur+i+1;
			if (numero > joueurs.size()-1){
				numero -= joueurs.size();
			}
			boutonsJoueurs.get(i).setText(joueurs.get(numero).getNomJoueur());
			boutonsJoueurs.get(i).setForeground(joueurs.get(numero).getColorJoueurIhm());
		}
		
		// partie info case -------------------------------------------------------------------
		Carreau carreau = joueur.getPositionCourante();
		
		numeroCase.setText("Numero de carreau : "+carreau.getNumero());
		nomCase.setText("Nom du carreau : "+carreau.getNomCarreau());
		
		if (carreau.getClass() == Propriete.class){
			proprietaireCase.setText("Proprietaire : "+((Propriete)carreau).getProprietaire().getNomJoueur());
			if (((Propriete)carreau).getProprietaire() == null){
				loyerCase.setText("Prix d'achat : "+((Propriete)carreau).getPrix());
			}else{
				loyerCase.setText("Loyer : "+((Propriete)carreau).calculLoyer(controleur.getMonopoly().getSommeDes()));
			}
			if (carreau.getClass() == ProprieteAConstruire.class){
				couleurCase.setText("Couleur : "+((ProprieteAConstruire)carreau).getCouleur().toString());
				if (((ProprieteAConstruire)carreau).getNbmaison() == 5){
					nombreBatimentCase.setText("Batiment : 1 hotel");
				}else{
					nombreBatimentCase.setText("Batiments : "+((ProprieteAConstruire)carreau).getNbmaison()+" maisons");
				}
			}else{
				couleurCase.setText("");
				nombreBatimentCase.setText("");
			}
		}else{
			proprietaireCase.setText("");
			loyerCase.setText("");
		}
		
		// partie bouton action ---------------------------------------------------------------
		
		boutonActionCarreau.setText("Aucune action");
		boutonActionCarreau.setEnabled(false);
		
		// partie info des ---------------------------------------------------------------------
		
		de1.setText("De n°1 : X");
						
		de2.setText("De n°2 : X");
						
		sommeDes.setText("Somme : X");
		
		// partie propriete du joueur ----------------------------------------------------------
		
		DefaultTableModel model = new DefaultTableModel(){ 
                @Override
                public boolean isCellEditable(int row, int column){ //pour rendre les cellules non modifiable
                return false;
            }};
        String [] colonnes = {"Nom","Loyer","Batiment","Couleur"};
        model.setColumnIdentifiers(colonnes);
            
        for (Propriete p : controleur.getMonopoly().getJoueurs().get(numeroJoueur).getProprietes()){
		String [] donnees = {p.getNomCarreau(), String.valueOf(p.calculLoyer(0)), "vide", "vide"};
		if (p.getClass() == ProprieteAConstruire.class){
			donnees[3] = ((ProprieteAConstruire) p).getCouleur().toString();
			if (((ProprieteAConstruire) p).getNbmaison() == 5){
				donnees[4] = "1 hotel";
			}else{
				donnees[4] = ((ProprieteAConstruire) p).getNbmaison()+" maisons";
			}
		}
            model.addRow(donnees);
        }
        tablePropriete.setModel(model);
		
		// -------------------------------------------------------------------------------------
		
		
			
	}
	
	public void afficherInfoJoueur(String nomJoueur) { // quand on click sur un autre joueur
		Joueur joueur;
		for (Joueur j : controleur.getMonopoly().getJoueurs()){
			if (j.getNomJoueur().equals(nomJoueur)){
				joueur = j;
			}
			// a completer (affiche les information du joueur)
		}
	}
	
	
}
