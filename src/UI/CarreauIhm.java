/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author bouchval
 */
public class CarreauIhm extends Canvas{
	
	private Carreau carreau; // carreau à representer
	
	private ArrayList<Joueur> joueurs; // joueurs present sur le plateau
	
	private int largeur; // dimension du carreau
	private int hauteur;
			
	private Point positionText; // point de position du nom du carreau
	private Point positionCash; // point de position du loyer ou du prix d'achat
	
	private int xPositionDessinJoueur = 1; // position en x ou le premier joueur vas etre dessiné (utilisé pour si il y a plusieur joueur sur la meme case)
	
	
	public CarreauIhm(Carreau c, int x, int y){
		this.carreau = c;
		this.joueurs = new ArrayList<>();
		largeur = x;
		hauteur = y;
		positionCash = new Point(largeur/2-15,hauteur-5);
		positionText = new Point(3, 3);
		this.setPreferredSize(new Dimension(x,y));
	}
	
	public void setCarreau(Carreau c){
		this.carreau = c;
	}
	
	public void setJoueurs(ArrayList<Joueur> joueurs){
		this.joueurs = joueurs;
	}
	
	
	
	public void paint(Graphics g){
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getLargeur()-1, getHauteur()-1);
		
		g.setFont(new Font("", 0, 10)); // taille des textes
		
		// pour afficher le nom des case
		int nbLigne = 0;
		for (String s : getNomCarreau()){
			if (s != null){
				g.drawString(s, getPositionText().x, getPositionText().y+g.getFont().getSize() + nbLigne*g.getFont().getSize());
				nbLigne ++;
			}
			
		}
		
		int numJoueur = 0;
		for (Joueur j : getJoueurs()){ // pour afficher les joueurs
			
			if (j.getPositionCourante() == getCarreau()){ // si un joueur est sur ce carreau
				g.setColor(j.getColorJoueurIhm()); // doit etre la couleur du joueur
				g.fillOval(getxPositionDessinJoueur() + numJoueur*(getLargeur()/getJoueurs().size()), getPositionText().y+10+ nbLigne*g.getFont().getSize(), 20, 20);
				numJoueur ++;
			}
			
		}
				
	}
	

	private String[] getNomCarreau() { // metode pour mettre le nom de la case sur plusieur ligne
		int charractereParLigne = getLargeur() /6;
		
		String nom[] = getCarreau().getNomCarreau().split(" ");
		String nomFinal[] = new String[nom.length+1];
		String nomIntermediaire = new String("");
		
		int i = 0;
		
		for (String s : nom){
			if (String.join(" ", nomIntermediaire, s).length() > charractereParLigne){
				nomFinal[i] = nomIntermediaire;
				nomIntermediaire = s;
				i++;
			}else{
				nomIntermediaire = nomIntermediaire.trim();
				nomIntermediaire = String.join(" ", nomIntermediaire, s);
			}
		}
		
		if (i <= nom.length){
			nomFinal[i] = nomIntermediaire;
		}
		
		return nomFinal;
		
	}

	/**
	 * @return the carreau
	 */
	public Carreau getCarreau() {
		return carreau;
	}

	/**
	 * @return the joueurs
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * @return the largeur
	 */
	public int getLargeur() {
		return largeur;
	}

	/**
	 * @return the hauteur
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * @return the numero
	 */
	public int getNumero() {
		return carreau.getNumero();
	}

	/**
	 * @return the positionText
	 */
	public Point getPositionText() {
		return positionText;
	}

	/**
	 * @param positionText the positionText to set
	 */
	public void setPositionText(Point positionText) {
		this.positionText = positionText;
	}

	/**
	 * @return the positionCash
	 */
	public Point getPositionCash() {
		return positionCash;
	}

	/**
	 * @param positionCash the positionCash to set
	 */
	public void setPositionCash(Point positionCash) {
		this.positionCash = positionCash;
	}

	/**
	 * @return the xPositionDessinJoueur
	 */
	public int getxPositionDessinJoueur() {
		return xPositionDessinJoueur;
	}

	/**
	 * @param xPositionDessinJoueur the xPositionDessinJoueur to set
	 */
	public void setxPositionDessinJoueur(int xPositionDessinJoueur) {
		this.xPositionDessinJoueur = xPositionDessinJoueur;
	}

	
}
