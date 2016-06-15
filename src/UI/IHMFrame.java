package UI;

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
    
    private JButton boutonMenu;
    
    private JPanel boutonsMenu;
    private JPanel panelFenetre;
    private JPanel boutonsJeu;

    
    //initialise la frame
    public IHMFrame(Controleur controleur){
		super("Monopoly");
		panelFenetre = new JPanel(new BorderLayout());
		this.controleur = controleur;
    }
    
    //ajuste la taille de la frame et initilaise puis affiche l'IHM du menu de sélection des joueurs
    public void afficherMenu(Controleur controleur,IHMFrame frame){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,900);

		menu = new IHMMenu(controleur,frame);
		controleur.setObservateur(menu);
		boutonsMenu = menu.getpBouttons();
		panelFenetre.add(menu, BorderLayout.CENTER);
		this.add(panelFenetre);

		this.setVisible(true);
		}

		//ajuste la taille de la frame et initialise puis affiche l'IHM du jeu
		public void afficherJeu(Controleur controleur,IHMFrame frame){
		controleur.setJoueurCourant(controleur.getMonopoly().getJoueurs().get(0));
		this.setSize(1500,1000);
		jeu = new IHMJeu(controleur,this);
		controleur.setObservateur(jeu);
		panelFenetre.removeAll();
		panelFenetre.add(jeu,BorderLayout.CENTER);
		panelFenetre.revalidate();
		initBoutonMenu(controleur,frame);
    }

    
    //initialise et place dans la denètre le bouton menu(pour le jeu)
    public void initBoutonMenu(Controleur controleur,IHMFrame frame){
	boutonsJeu = new JPanel();
	boutonMenu = new JButton("Menu");
	boutonMenu.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int rep = JOptionPane.showConfirmDialog(   null, 
				    " Voulez vous retourner au menu ? Votre progression sera perdu", 
				    "Retour au menu ",
				    JOptionPane.OK_OPTION, 
				    JOptionPane.QUESTION_MESSAGE);
		if(rep == 0){
		    panelFenetre.removeAll();
		    controleur.getMonopoly().getJoueurs().clear();
		    afficherMenu(controleur,frame);
		}
	    }
	});
	boutonsJeu.add(boutonMenu);
	panelFenetre.add(boutonMenu, BorderLayout.NORTH);
    }
        
    //main du programme
    public static void main(String[] args) {
	    Controleur controleur = new Controleur();
	    IHMFrame ihm = new IHMFrame(controleur);
	    ihm.afficherMenu(controleur,ihm);

	}

   
}
