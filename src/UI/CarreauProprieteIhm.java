package UI;

import Jeu.Carreau;
import Jeu.Propriete;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bouchval
 */
public class CarreauProprieteIhm extends CarreauIhm{
	
	private int tailleProprietaire = 10;
	private Point positionProprietaire = new Point(getLargeur()-tailleProprietaire, getHauteur()-tailleProprietaire);

	
	public CarreauProprieteIhm(Carreau c, int x, int y){
		super(c, x, y);
	}
	
	@Override
	public void paint(Graphics g){
		
		g.setColor(Color.black);
		g.setFont(new Font("", 0, 10)); // taille des textes
		
		if (((Propriete)getCarreau()).getProprietaire() != null){ // si il y a un proprietaire on affiche le loyer et une pastille qui represente le proprietaire sinon le prix d'achat
			g.drawString(((Propriete)getCarreau()).calculLoyer(0)+"€", getPositionCash().x, getPositionCash().y);
			g.setColor(((Propriete)getCarreau()).getProprietaire().getColorJoueurIhm()); // couleur du proprietaire
			g.fillRect(getPositionProprietaire().x, getPositionProprietaire().y, getTailleProprietaire(), getTailleProprietaire()); //doit etre la couleur du proprietaire
		}else{
			g.drawString(((Propriete)getCarreau()).getPrix()+"€", getPositionCash().x, getPositionCash().y);
		}
			
		super.paint(g);
		
	}

	/**
	 * @return the tailleProprietaire
	 */
	public int getTailleProprietaire() {
		return tailleProprietaire;
	}

	/**
	 * @return the positionProprietaire
	 */
	public Point getPositionProprietaire() {
		return positionProprietaire;
	}

	/**
	 * @param positionProprietaire the positionProprietaire to set
	 */
	public void setPositionProprietaire(Point positionProprietaire) {
		this.positionProprietaire = positionProprietaire;
	}
	
	
	
}
