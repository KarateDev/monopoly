/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.Carreau;
import Jeu.ProprieteAConstruire;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author bouchval
 */
public class CarreauProprieteAConstruireIhm extends CarreauProprieteIhm{
	
	private final int hauteurCouleur = 30; // hauteur de la partie en couleur
	private final Point positionMaison = new Point(2, 2); // position de la maison pour la partie basse
	private final Point translationMaison = new Point(getLargeur()/5, 0); // translation pour obtenir le point de la seconde maison
	private final int tailleMaison = 10;  // maison : carre de taille 10
	private final int tailleHotel = 20;
	
	public CarreauProprieteAConstruireIhm(Carreau c, int x, int y){
		super(c, x, y);
		
		int largeur = getLargeur();
		int hauteur = getHauteur();
		int numero = getNumero();
		
		// pour initialiser la position des éléments suivant l'emplacement du carreau
		if (numero > 1 && numero < 11){ //partie basse  ]1..11[
			setPositionText(new Point(getPositionText().x, getPositionText().y+hauteurCouleur));
				
		}else if (numero > 11 && numero < 21){ //partie gauche  ]11..21[
			positionMaison.setLocation(largeur-2-tailleMaison, 2);
			translationMaison.setLocation(0,translationMaison.x);
			setPositionProprietaire(new Point(0, hauteur-getTailleProprietaire()));
				
		}else if (numero > 21 && numero < 31){ //partie haute  ]21..31[
			setPositionCash(new Point(getPositionCash().x, getPositionCash().y-hauteurCouleur));
			positionMaison.setLocation(2, hauteur-2-tailleMaison);
			setPositionProprietaire(new Point(largeur-getTailleProprietaire(), hauteur-getTailleProprietaire()-hauteurCouleur));
				
		}else{ //partie droite  ]31..40]
			setPositionText(new Point(getPositionText().x+hauteurCouleur, getPositionText().y));
			setxPositionDessinJoueur(getxPositionDessinJoueur() + hauteurCouleur);
			translationMaison.setLocation(0,translationMaison.x);
		}
	}
	
	@Override
	public void paint(Graphics g){
		
		int largeur = getLargeur();
		int hauteur = getHauteur();
		int numero = getNumero();
		
		g.setColor(getColor()); // recupere la couleur de la case
		
		// pour desiner le rectangle de couleur
		if (numero > 1 && numero < 11){ //partie basse  ]1..11[
			g.fillRect(1, 1,largeur-2 , hauteurCouleur);
			g.setColor(Color.BLACK);
			g.drawLine(0, hauteurCouleur, largeur, hauteurCouleur);
				
		}else if (numero > 11 && numero < 21){ //partie gauche  ]11..21[
			g.fillRect(largeur-1 - hauteurCouleur, 1, hauteurCouleur, hauteur-2);
			g.setColor(Color.BLACK);
			g.drawLine(largeur-hauteurCouleur-1, 0, largeur-hauteurCouleur-1, hauteur);
				
		}else if (numero > 21 && numero < 31){ //partie haute  ]21..31[
			g.fillRect(1, hauteur-1 - hauteurCouleur, largeur-2, hauteurCouleur);
			g.setColor(Color.BLACK);
			g.drawLine(0, hauteur-hauteurCouleur-1, largeur, hauteur-hauteurCouleur-1);
				
		}else{ //partie droite  ]31..40]
			g.fillRect(1, 1, hauteurCouleur, hauteur-2);
			g.setColor(Color.BLACK);
			g.drawLine(hauteurCouleur, 0, hauteurCouleur, hauteur);
		}
		
		g.setColor(Color.white);
		int nbmaison = ((ProprieteAConstruire)getCarreau()).getNbmaison(); //pour construire les maisons
		if (nbmaison == 5){
			g.fillRect(positionMaison.x, positionMaison.y, tailleHotel, tailleHotel);
		}else{
			for (int i = 0; i < ((ProprieteAConstruire)getCarreau()).getNbmaison(); i++){ 
				g.fillRect(positionMaison.x+(i*translationMaison.x), positionMaison.y+(i*translationMaison.y), tailleMaison, tailleMaison);
			}
		}
		
		super.paint(g);
		
	}
	
	private Color getColor(){ // pour avoire la couleur des proprietes
		switch (((ProprieteAConstruire)getCarreau()).getCouleur()){
			case bleuFonce :
				return Color.blue;
			case orange :
				return Color.orange;
			case mauve :
				return Color.magenta;
			case violet :
				return Color.pink;
			case bleuCiel :
				return Color.cyan;
			case jaune :
				return Color.yellow;
			case vert :
				return Color.green;
			case rouge :
				return Color.red;
				
		}
		return null;
	}
	
}
