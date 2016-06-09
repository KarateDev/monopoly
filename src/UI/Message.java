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
import Jeu.ProprieteAConstruire;
import java.util.ArrayList;

/**
 *
 * @author sorindoc
 */
public class Message {
	
	public enum Type {
		AFFICHER_DEMANDE_ACHETER_PROPRIETE,
		AFFICHER_ACHAT_PROPRIETE,

		AFFICHER_ACHAT_BATIMENT,
		AFFICHER_PROPRIETE_CONSTRUCTIBLE,
		
		AFFICHER_PAYER_LOYER,
		AFFICHER_PAYER_TAXE,
		
		AFFICHER_CARTE_CAISSE_DE_COMMUNAUTE,
		AFFICHER_CARTE_CHANCE,
		
		AFFICHER_PASSAGE_DEPART,
		AFFICHER_ARGENT_RESTANT,
		
		AFFICHER_CARREAU,
	
		AFFICHER_FIN_PARTIE,
		
		AFFICHER_JOUEUR,
		AFFICHER_JOUEUR_ELIMINE,
		
		AFFICHER_FAIT_UN_DOUBLE,
		AFFICHER_3D_DOUBLE,
		
		AFFICHER_ATTENDRE_PROCHAIN_TOUR,
		
		AFFICHER_PATRIMOINE,
		AFFICHER_LANCER_DES,
		
		AFFICHER_MENU,
		
		AFFICHER_INTERACTION_PRISON,
		AFFICHER_LIBERE_PRISON,
		AFFICHER_DERNIER_TOUR_EN_PRISON,
		
		AFFICHER_PAS_DE_TERRAIN_CONSTRUCTIBLE,
		AFFICHER_PAS_ASSEZ_DE_BATIMENTS,
		
		AFFICHER_PAS_ASSEZ_DARGENT,
		
		AFFICHER_SORTIE_PRISON_AMANDE,
		AFFICHER_SORTIE_PRISON_DOUBLE,
		AFFICHER_PAS_ASSEZ_DARGENT

	}

	public Message(Type type) {
		this.type = type;
	}
	
    
	public Type type;
	public Propriete propriete;
	public ArrayList<ProprieteAConstruire> proprieteConstructible;
	public Joueur joueur;
	public int loyer;
	public Carreau carreau;
	public Carte carte;
	
	public int de1;
	public int de2;
}
