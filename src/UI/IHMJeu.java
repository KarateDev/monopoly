/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.*;
import Jeu.Cartes.Carte;

import Jeu.Joueur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;

/**
 *
 * @author bouchval
 */
public class IHMJeu extends JPanel{
	
	private AchatBatimentIhm achatBatimentIhm;
	
	private Controleur controleur;
	
	private JPanel panelInteraction;
		
	private boolean aLanceLesDes = false; // pour savoir si le joueur à lancé les dés
	
	private Plateau plateau;
	
	private ArrayList<JLabel> labelAutreJoueurs;
	
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
	
	private JTextArea information;
	
	private JTable tablePropriete;
	private JButton boutonAcheterBatiment;
	
	private JLabel labelDe1;
	private JLabel labelDe2;
	private JLabel labelSommeDes;
	
	public IHMJeu(Controleur controleur, HashMap<Integer, Carreau> carreaux, ArrayList<Joueur> joueurs, Joueur joueur){
		
		this.controleur = controleur;
		
		this.setLayout(new BorderLayout(10,10));
		
		initPartiePlateau(carreaux, joueurs);
		
		this.add(initPartieJeu(),BorderLayout.CENTER);
		
		ajouterListner(joueurs, joueur);
		
		initialisationDebutTour(joueurs, joueur);
	}
	
	public AchatBatimentIhm getIhmAchatBatiment(){
		return achatBatimentIhm;
	}
	

	private void initPartiePlateau(HashMap<Integer, Carreau> carreaux, ArrayList<Joueur> joueurs) {
		int nbJoueur = joueurs.size();
		labelAutreJoueurs = new ArrayList<>();
		
		
		JPanel panelPlateau = new JPanel(new BorderLayout());
			
			JPanel panelHaut = new JPanel(new GridLayout(2,1));
				panelHaut.add(new JLabel("Autre joueur :"));
				JPanel panelAutreJoueur = new JPanel(new GridLayout(1,nbJoueur-1));
					for (int i = 1; i <= nbJoueur-1 ;i++){
						JPanel panelLabel = new JPanel(new BorderLayout());
						panelLabel.setBorder(new LineBorder(Color.black));
							JLabel labelAutreJoueur = new JLabel("", SwingConstants.CENTER);
							labelAutreJoueur.setPreferredSize(new Dimension(0, 25));
							panelLabel.add(labelAutreJoueur,BorderLayout.CENTER);
							panelAutreJoueur.add(panelLabel);
							
						labelAutreJoueurs.add(labelAutreJoueur);
					}
				panelHaut.add(panelAutreJoueur);

			panelPlateau.add(panelHaut,BorderLayout.NORTH);
		
			plateau = new Plateau(carreaux,joueurs,700);
			panelPlateau.add(plateau,BorderLayout.SOUTH);
						
		this.add(panelPlateau,BorderLayout.EAST);
	}


	private JPanel initPartieJeu() {
				
		panelInteraction = new JPanel(new BorderLayout(0,20));
			
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
						
					JPanel panelInteractionInfoCarreauBouton = new JPanel(new GridLayout(2,1,10,10));
						JPanel panelInformation = new JPanel(new BorderLayout());
							information = new JTextArea();
							information.setEditable(false);
							information.setLineWrap(true); // retour à la ligne automatique
							information.setBorder(new TitledBorder("Information"));
							information.setPreferredSize(information.getSize());
							panelInformation.add(information,BorderLayout.CENTER);
							
							panelInteractionInfoCarreauBouton.add(panelInformation);
							
						JPanel panelBouton = new JPanel(new GridLayout(2, 2,5,5));
							boutonLancerDes = new JButton("Lancer les dés");
							panelBouton.add(boutonLancerDes);

							boutonActionCarreau = new JButton("Action");
							panelBouton.add(boutonActionCarreau);

							boutonAbandonner = new JButton("Abandonner");
							panelBouton.add(boutonAbandonner);

							boutonAide = new JButton("Aide");
							panelBouton.add(boutonAide);
							
							panelInteractionInfoCarreauBouton.add(panelBouton);
					
						panelInteractionInfoCarreau.add(panelInteractionInfoCarreauBouton,BorderLayout.EAST);
						
					JPanel panelInfoDes = new JPanel(new GridLayout(1,5));
						panelInfoDes.add(new JPanel());
						
						labelDe1 = new JLabel();
						panelInfoDes.add(labelDe1);
						
						labelDe2 = new JLabel();
						panelInfoDes.add(labelDe2);
						
						labelSommeDes = new JLabel();
						panelInfoDes.add(labelSommeDes);
						
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
		
		return panelInteraction;
		
	}
	
	
	private void ajouterListnerAutreJoueur(ArrayList<Joueur> joueurs, Joueur joueur){
		for (JLabel label : labelAutreJoueurs){
			
			label.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
				}
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					Joueur joueur = null;
					for (Joueur j : joueurs){ // pour recuperer le joueur à qui apartien, le label
						if (j.getNomJoueur().equals(((JLabel)e.getComponent()).getText())){
							joueur = j;
						}
					}
					((JLabel)e.getComponent()).setText(joueur.getCash()+"€");
				}

				@Override
				public void mouseExited(MouseEvent e) {
					int indice = labelAutreJoueurs.indexOf(e.getComponent());
					int numero = joueurs.indexOf(joueur)+indice+1;
					if (numero > labelAutreJoueurs.size()){
						numero -= (labelAutreJoueurs.size()+1);
					}
					((JLabel)e.getComponent()).setText(joueurs.get(numero).getNomJoueur());
				}
			});
		}
	}
	
	private void ajouterListner(ArrayList<Joueur> joueurs, Joueur joueur) {
		
		ajouterListnerAutreJoueur(joueurs, joueur);
		
		boutonLancerDes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (! aLanceLesDes){ // si il n'a pas deja lancé les des, il peut les lancer
					
					information.setText("");

					//------------------------------------------------------------------------------- action du controleur a faire (lancer les dés et avancer)

				}else{ // si il a deja lancé les des on affiche un message
					information.setText("Vous avez dejà lancé les dés pour ce tour !");
				}
		
			}
		});
		
		boutonTourSuivant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				information.setText("");
				if (aLanceLesDes){ // si il a lancé les dés
					
					// --------------------------------------------------------------------------------- action du controleur a faire (passer au tour suivant)
					
				}else{ // si il n'a pas lancé les dés
					information.setText("Vous devez lancer les dés avant de passer au joueur suivant !");
				}
				
			}
		});
		
		boutonActionCarreau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// -------------------------------------------------------------------------------------- action du controleur a faire (interaction avec le carreau)
			}
		});
			
		
		
		boutonAcheterBatiment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// --------------------------------------------------------------------------------------- action controleur a faire (achat batiment (initialise la liste des batiments constructible))
				
			}
		});
		
		boutonAide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showConfirmDialog(   null, 
						"NON je ne vous aiderai pas !!!!!  HAHAHAHAHAAH", 
						"Aide",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		boutonAbandonner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (JOptionPane.showConfirmDialog(   null, 
						"Etes vous sur de vouloire abandonner ?", 
						"...",
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE) == 0){ // si il abandonne
					
					// --------------------------------------------------------------------------------action du controleur (abandonner)
				
				}
			}
		});
		
	}
	
	private void initialisationDebutTour(ArrayList<Joueur> joueurs, Joueur joueur){
		
		// partie info joueur --------------------------------------------------------------
		
		labelNom2.setText(joueur.getNomJoueur());
		labelNom2.setForeground(joueur.getColorJoueurIhm());
		
		labelArgent.setText(" Argent : "+joueur.getCash()+"€");
		
		// partie info autre joueur ---------------------------------------------------------
		
		for (int i = 0; i < joueurs.size()-1; i++){
			int numero = joueurs.indexOf(joueur)+i+1;
			if (numero > joueurs.size()-1){
				numero -= joueurs.size();
			}
			labelAutreJoueurs.get(i).setText(joueurs.get(numero).getNomJoueur());
			labelAutreJoueurs.get(i).setForeground(joueurs.get(numero).getColorJoueurIhm());
		}
		
		// partie info case -------------------------------------------------------------------
		
		afficherInfoCarreau(joueur.getPositionCourante(), 0);
		
		// partie bouton action ---------------------------------------------------------------
		
		boutonActionCarreau.setText("Aucune action");
		boutonActionCarreau.setEnabled(false);
		information.setText("");
		
		// partie info des ---------------------------------------------------------------------
		
		labelDe1.setText("De n°1 : X");
						
		labelDe2.setText("De n°2 : X");
						
		labelSommeDes.setText("Somme : X");
		
		// partie propriete du joueur ----------------------------------------------------------
		
		actualiserPropriete(joueur);
		
		// -------------------------------------------------------------------------------------
		
	}
	
	private void actualiserArgentAutreJoueur(Joueur joueur){
		
		for (JLabel label : labelAutreJoueurs){
			if (label.getText().equals(joueur.getNomJoueur())){
				label.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						label.setText(joueur.getCash()+"€");
					}

					@Override
					public void mouseExited(MouseEvent e) {
						label.setText(joueur.getNomJoueur());
					}
				});
			}
		}
		
	}
	
	private void actualiserArgent(Joueur joueur){
		
		labelArgent.setText("Argent : "+joueur.getCash()+"€");
		
	}
	
	private void actualiserPropriete(Joueur joueur){
		DefaultTableModel model = new DefaultTableModel(){ 
                @Override
                public boolean isCellEditable(int row, int column){ //pour rendre les cellules non modifiable
                return false;
            }};
        String [] colonnes = {"Nom","Loyer","Batiment","Couleur"};
        model.setColumnIdentifiers(colonnes);
            
        for (Propriete p : joueur.getProprietes()){
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
	}
	
	
	
	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------ fonction que le notifieur devra appeler
	// -------------------------------------------------------------------------------------------
	
	
	
	public void afficherTourDuJoueur(ArrayList<Joueur> joueurs, Joueur joueur){
					
					aLanceLesDes = false;
					initialisationDebutTour(joueurs, joueur);
					ajouterListner(joueurs, joueur);
	}
	
	public void afficherActionDesEtCarreau(Carreau carreau, int de1, int de2, int nbTourEnPrison){
		boutonActionCarreau.setEnabled(true);
			if (carreau.getClass() == Propriete.class && ((Propriete)carreau).getProprietaire() == null){
				boutonActionCarreau.setText("Acheter");
			}else if (carreau.getClass() == Pioche.class){
				boutonActionCarreau.setText("Piocher une carte");
			}else if (carreau.getClass() == Prison.class && nbTourEnPrison > 0){
				boutonActionCarreau.setText("Utiliser une carte");
			}else{
				boutonActionCarreau.setText("Aucune action");
				boutonActionCarreau.setEnabled(false);
			}
					
			labelDe1.setText("De n°1 : "+de1);
			labelDe2.setText("De n°2 : "+de2);
			labelSommeDes.setText("Somme: "+(de1+de2));

			aLanceLesDes = true;
			
			afficherInfoCarreau(carreau, (de1+de2));
	}

	private void afficherInfoCarreau(Carreau carreau, int sommeDes) {
		
		numeroCase.setText("Numero de carreau : "+carreau.getNumero());
		nomCase.setText("Nom du carreau : "+carreau.getNomCarreau());
		
		if (carreau.getClass() == Propriete.class){
			proprietaireCase.setText("Proprietaire : "+((Propriete)carreau).getProprietaire().getNomJoueur());
			if (((Propriete)carreau).getProprietaire() == null){
				loyerCase.setText("Prix d'achat : "+((Propriete)carreau).getPrix());
			}else{
				loyerCase.setText("Loyer : "+((Propriete)carreau).calculLoyer(sommeDes));
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
	}
	
	public void afficherEnvoyerEnPrison(Joueur joueur){ // à appeler apres afficherActionDesEtCarreau
		information.setText("Vous etes envoyé en prison ...");
		boutonActionCarreau.setText("Aucune action");
		boutonActionCarreau.setEnabled(false);
	}
	
	public void afficherAFaitUnDouble(Joueur joueur){
		information.setText("Félicitation, vous avez fait un double ! Vous pouvez donc rejouer !");
		aLanceLesDes = false;
	}
	
	public void affichePassageDepart(int argentGagne, Joueur joueur){
		information.setText("Vous passez par la case départ et empochez "+argentGagne+"€");
		actualiserArgent(joueur);
		
	}
	
	public void afficherPayerLoyer(Joueur joueur1, Joueur joueur2, int loyer) {
		 
		JOptionPane.showConfirmDialog(null, 
			"Loyer", 
			"Vous vous etes arreté sur la propriete de "
				+joueur2.getNomJoueur()
				+" et payez un loyer de "+loyer+"€",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
		
		actualiserArgent(joueur1);
		actualiserArgentAutreJoueur(joueur2);
		
	}
	
	public void afficherPayerTaxe(Joueur joueur , Taxe taxe){
		
		JOptionPane.showConfirmDialog(null, 
			"Loyer", 
			"Vous payez une taxe de "+taxe+"€",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
		
		actualiserArgent(joueur);
		
    }
	
	public void afficherDemandeAcheterPropriete(Propriete p){
		if (JOptionPane.showConfirmDialog(null, 
				"Acheter", 
				"Etes vous sur de vouloir acheter cette propriete pour "+p.getPrix()+"€",
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE) == 0){ // si il veut acheter
			
			// ---------------------------------------------------------------------------------- action du controleur a faire (acheter la propriete)
			
		}
		
    }
    
    public void afficherAchatPropriete(ArrayList<Joueur> joueurs, Joueur joueur){
        information.setText("Vous avez acheter une propriété");
		actualiserArgent(joueur);
		actualiserPropriete(joueur);
		plateau.repaintCarreau(joueur.getPositionCourante(), joueurs);
        
    }
	
	public void afficherJoueurElimine(HashMap<Integer, Carreau> carreaux, ArrayList<Joueur> joueurs){
        JOptionPane.showConfirmDialog(null, 
			"Elimination", 
			"Domage, vous n'avez plus d'argent... Vous etes éliminé !",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
		plateau.repaintPlateau(carreaux, joueurs);
		
    }
	
	public void afficherJoueur3double(){
        JOptionPane.showConfirmDialog(null, 
			"En prison", 
			"Domage, vous avez fait 3 doubles ... Vous etes envoyé en prison !",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
    }
	
	public void afficherCarteChance(Carte carte){
		JOptionPane.showConfirmDialog(null, 
			"Carte chance", 
			carte.getIntitule(),
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void afficherCarteCaisseDeCommunaute(Carte carte) {
		JOptionPane.showConfirmDialog(null, 
			"Carte caisse de communauté", 
			carte.getIntitule(),
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void afficherFinDePartie(Joueur joueur){
		JOptionPane.showConfirmDialog(null, 
			"Fin de partie", 
			"La partie est terminée ! Le joueur "
			+joueur.getNomJoueur()+" a gagné avec "
			+joueur.getCash()+"€",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
	}
	
	public void afficherPasDeTerrainConstructible() {
		JOptionPane.showConfirmDialog(null, 
			"Construction", 
			"Desolé, vous n'avez pas toute les propriétés d'une couleur",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void afficherLibereDePrison() {
		JOptionPane.showConfirmDialog(null, 
			"Prison", 
			"Vous etes libere de prison",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void afficherDernierTourEnPrison(Joueur joueur) {
		JOptionPane.showConfirmDialog(null, 
			"Prison", 
			"C'était votre dernier tour en prison et vous n'avez pas fait de double ..."
				+ "Vous payer donc une amande de 50€ pour etre libéré",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
		actualiserArgent(joueur);
		
	}
	
	public void interactionAcheterBatiment(Joueur joueur, ArrayList<Propriete> proprietes){
		
		achatBatimentIhm = new AchatBatimentIhm(controleur, joueur, proprietes);
		panelInteraction.removeAll();
		panelInteraction = achatBatimentIhm;
	}
	
	public void quitterAcheterBatiment(ArrayList<Joueur> joueurs, Joueur joueur,Carreau carreau, int de1, int de2){
		
		panelInteraction.removeAll();
		panelInteraction = initPartieJeu();
		ajouterListner(joueurs, joueur);
		actualiserPropriete(joueur);
		afficherActionDesEtCarreau(carreau, de1, de2, joueur.getNbTourEnPrison());
	}


}
