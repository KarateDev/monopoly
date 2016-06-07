/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Jeu.Carreau;
import Jeu.Cartes.Carte;
import Jeu.Joueur;
import Jeu.Propriete;

/**
 *
 * @author sorindoc
 */
public class Message {
	
	public enum Type {
		AFFICHER_DEMANDE_ACHETER_PROPRIETE,
		AFFICHER_ACHAT_PROPRIETE,
		
		AFFICHER_PAYER_LOYER,
		AFFICHER_PAYER_TAXE,
		
		AFFICHER_CARTE_CAISSE_DE_COMMUNAUTE,
		AFFICHER_CARTE_CHANCE,
		
		AFFICHER_PASSAGE_DEPART,
		AFFICHER_ARGENT_RESTANT,
		
		AFFICHER_CARREAU
	}

	public Message(Type type) {
		this.type = type;
	}
	
    
	public Type type;
	public Propriete propriete;
	public Joueur joueur;
	public int loyer;
	public Carreau carreau;
	public Carte carte;
	public int argent;
	
	public int de1;
	public int de2;
}
