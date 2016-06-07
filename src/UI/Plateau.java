/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author bouchval
 */
public class Plateau extends JPanel{
	
	private HashMap<Integer, CarreauIhm> carreauxIhm;
	
	private int largeurCarreau; // largeur d'un carreau (dépend de la fenetre)
	private int hauteurCarreau; // hauteur d'un carreau ()
	
	public Plateau(HashMap<Integer, Carreau> carreaux, ArrayList<Joueur> joueurs, int largeur){
		
		this.setSize(largeur, largeur);
		
		this.carreauxIhm = new HashMap<>();
		
        this.setLayout(new BorderLayout(0,-1));
        
        int nbCarreau = (carreaux.size()/4)+1; //nombre de case par ligne
		
		double proportionHauteurLargeur = 2; // hauteur d'une case par rapport à sa largeur
		largeurCarreau = (int) (getSize().width/(9+2*proportionHauteurLargeur)-2);
		hauteurCarreau = (int) (largeurCarreau*proportionHauteurLargeur);
        
		CarreauIhm p;
		
        JPanel panelhaut = new JPanel();        //partie haute
        panelhaut.setLayout(new BorderLayout(-1,0));
            JPanel gridhaut = new JPanel(new GridLayout(1,nbCarreau-2));
                for (int i =(nbCarreau-1)*2 +2;i <= nbCarreau*3 -3;i++){
                    p = construireCarreau(carreaux.get(i), largeurCarreau,hauteurCarreau);
					carreauxIhm.put(i, p);
                    gridhaut.add(p);
                }
            panelhaut.add(gridhaut,BorderLayout.CENTER);
			p = construireCarreau(carreaux.get(nbCarreau+nbCarreau-1), hauteurCarreau,hauteurCarreau); // carreau haut-gauche
			carreauxIhm.put(nbCarreau+nbCarreau-1, p);
            panelhaut.add(p,BorderLayout.WEST);
			p = construireCarreau(carreaux.get(nbCarreau+nbCarreau+nbCarreau-2), hauteurCarreau,hauteurCarreau); // carreau haut-droit
			carreauxIhm.put(nbCarreau+nbCarreau+nbCarreau-2, p);
            panelhaut.add(p,BorderLayout.EAST);
        this.add(panelhaut,BorderLayout.NORTH);
        
        JPanel panelbas = new JPanel();     //partie basse
        panelbas.setLayout(new BorderLayout(-1,0));
            JPanel gridbas = new JPanel(new GridLayout(1,nbCarreau-2));
                for (int i =nbCarreau-1;i >= 2;i--){
                    p = construireCarreau(carreaux.get(i), largeurCarreau,hauteurCarreau);
					carreauxIhm.put(i, p);
                    gridbas.add(p);
                }
            panelbas.add(gridbas,BorderLayout.CENTER);
			p = construireCarreau(carreaux.get(nbCarreau), hauteurCarreau,hauteurCarreau); // carreau bas-gauche
			carreauxIhm.put(nbCarreau, p);
            panelbas.add(p,BorderLayout.WEST);
			p = construireCarreau(carreaux.get(1), hauteurCarreau,hauteurCarreau); // carreau bas-droit
			carreauxIhm.put(1, p);
            panelbas.add(p,BorderLayout.EAST);
        this.add(panelbas,BorderLayout.SOUTH);
        
        JPanel paneldroit = new JPanel();       //partie droite
        paneldroit.setLayout(new BorderLayout());
            JPanel griddroit = new JPanel(new GridLayout(nbCarreau-2,1));
                for (int i =  nbCarreau*3 -1;i <= nbCarreau*4 -4;i++){
                    p = construireCarreau(carreaux.get(i), hauteurCarreau,largeurCarreau);
					carreauxIhm.put(i, p);
                    griddroit.add(p);
                }
            paneldroit.add(griddroit,BorderLayout.CENTER);
        this.add(paneldroit,BorderLayout.EAST);
        
        JPanel panelgauche = new JPanel();      //partie gauche
        panelgauche.setLayout(new BorderLayout());
            JPanel gridgauche = new JPanel(new GridLayout(nbCarreau-2,1));
                for (int i = (nbCarreau-1)*2;i >= nbCarreau+1;i--){
                    p = construireCarreau(carreaux.get(i), hauteurCarreau,largeurCarreau);
					carreauxIhm.put(i, p);
                    gridgauche.add(p);
                }
            panelgauche.add(gridgauche,BorderLayout.CENTER);
        this.add(panelgauche,BorderLayout.WEST);
	}
	
	
	public CarreauIhm construireCarreau(Carreau c, int x, int y){ // pour construire le bon type de carreau
		
		CarreauIhm carreau;
		if (c.getClass() == Taxe.class){
			carreau = new CarreauTaxeIhm(c, x, y);
		}else if (c.getClass() == ProprieteAConstruire.class){
			carreau = new CarreauProprieteAConstruireIhm(c, x, y);
		}else if (c.getClass() == Compagnie.class || c.getClass() == Gare.class){
			carreau = new CarreauProprieteIhm(c, x, y);
		}else{ // pour les autre carreaux
			carreau = new CarreauIhm(c, x, y);
		}
		return carreau;
		
	}
	
	
	public void repaintPlateau(HashMap<Integer, Carreau> c, ArrayList<Joueur> joueurs){
		
		for (int i = 1; i <= c.size(); i++){
			this.carreauxIhm.get(i).setCarreau(c.get(i));
			this.carreauxIhm.get(i).setJoueurs(joueurs);
			this.carreauxIhm.get(i).repaint();
		}
		
	}
	
	public void repaintCarreau(Carreau c, ArrayList<Joueur> joueurs){
		
		this.carreauxIhm.get(c.getNumero()).setCarreau(c);
		this.carreauxIhm.get(c.getNumero()).setJoueurs(joueurs);
		this.carreauxIhm.get(c.getNumero()).repaint();
		
	}
	
}
