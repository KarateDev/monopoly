package Jeu;

import Jeu.Cartes.Carte;
import Jeu.Cartes.CarteLiberationPrison;
import UI.Message;
import static UI.Message.Type.*;
import UI.Observateur;
import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> Carreaux;
	private ArrayList<Joueur> joueurs;
	private ArrayList<Integer> des;

	private ArrayList<Carte> piocheCarteChance;
	private ArrayList<Carte> piocheCarteCaisseDeCommunaute;
	
	private int nbMaisonDisponible = 32;
	private int nbHotelDisponible = 12;
	
	private int indicePrison;
	private int indiceParc;
	
	private Observateur observateur;
	  
	public Monopoly(Observateur observateur) {
		Carreaux = new HashMap();
		joueurs = new ArrayList<>();
		des = new ArrayList<>(2);
		piocheCarteChance = new ArrayList<>();
		piocheCarteCaisseDeCommunaute = new ArrayList<>();
		this.observateur = observateur;
	}

	public void lancerDes() {
		this.des.clear();
		Random rand = new Random();
		int de = rand.nextInt(6)+1;
		des.add(de);
		de = rand.nextInt(6)+1;
		des.add(de);
	}
		
	public void addJoueur(Joueur j){
		this.joueurs.add(j);
	}
		
	public void addCarreau(Carreau c) {
		if (c instanceof Prison) {
			this.indicePrison = c.getNumero();
		} else if (c instanceof ParcPublic) {
			this.indiceParc = c.getNumero();
		}
		this.Carreaux.put(c.getNumero(),c);
	}
	
	public Prison getPrison() {
		return (Prison) this.getCarreaux().get(this.indicePrison);
	}
	public ParcPublic getParcPublic() {
		return (ParcPublic) this.getCarreaux().get(this.indiceParc);
	}


	public void envoyerEnPrison(Joueur j) {
		j.setPositionCourante(this.getCarreaux().get(11));//la prison se trouve être la case 11
	}
	/**
	 * @return the Carreaux
	 */
	public HashMap<Integer,Carreau> getCarreaux() {
		return Carreaux;
	}

	public ArrayList<Integer> getDes() {
		return des;
	}

	/**
	 * @return the joueurs
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public int getSommeDes() {
		return des.get(0)+des.get(1);
	}

	public void eliminerJoueur(Joueur j){
		for(Propriete p : j.getProprietes()){ //les proprietes du joueur retournent à la banque
			p.setProprietaire(null);
			if (p.getClass() == ProprieteAConstruire.class){ //on enleve les eventuelles maisons
				((ProprieteAConstruire) p).setNbMaison(0);
			}
		}
		joueurs.remove(j);
	}

	public Carte piocherUneCarteChance() {
		Carte carte = piocheCarteChance.get(0); // on recupere la carte du sommet de la pile
		piocheCarteChance.remove(carte); // on supprime la carte du sommet de la pile
		if (!(carte instanceof CarteLiberationPrison)) { // si ce n'est pas la carte "libere de prison"
			piocheCarteChance.add(carte); // on la met en dessous de la pile
		}
		return carte;
	}

	public Carte piocherUneCarteCaisseDeCommunaute(){
		Carte carte = piocheCarteCaisseDeCommunaute.get(0); // on recupere la carte du sommet de la pile
		piocheCarteCaisseDeCommunaute.remove(carte); // on supprime la carte du sommet de la pile
		if (!(carte instanceof CarteLiberationPrison)) {
			piocheCarteCaisseDeCommunaute.add(carte); // on la met en dessous de la pile
		}
		return carte;
	}

	public void ajouterCarteChance(Carte carte){
		piocheCarteChance.add(carte);
	}

	public void ajouterCarteCaisseDeCommunaute(Carte carte){
		piocheCarteCaisseDeCommunaute.add(carte);
	}

	public void melangerLesCartes() {
		java.util.Collections.shuffle(piocheCarteChance);
		java.util.Collections.shuffle(piocheCarteCaisseDeCommunaute);
	}


	/**
	 * 
	 * @param indice
	 */
	public Carreau getCarreau(int indice) {
		return getCarreaux().get(indice);
	}

	public void deplacerJoueur(Joueur j, int deplacement) { //cette fonction gere le deplacement du joueur avec le passage par la case depart

		int numeroCaseActuel = j.getPositionCourante().getNumero();

		if (deplacement > 0){ // si le deplacement nous fait avancer
			if (numeroCaseActuel + deplacement > getCarreaux().size()){ //si on passe par la case depart
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement - getCarreaux().size()));
				j.recevoirCash(((Depart) getCarreau(1)).getGainPourPassage());   //gain pour etre passe par la case depart
				
				Message msg1 = new Message(AFFICHER_PASSAGE_DEPART);
				msg1.carreau = getCarreau(1);
				observateur.notifier(msg1);
				
				Message msg2 = new Message(AFFICHER_ARGENT_RESTANT);
				msg2.argent = j.getCash();
				observateur.notifier(msg2);
			}else{
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement));
			}

		} else { // si le deplacement nous fait reculer (pour la carte chance)
			if (numeroCaseActuel + deplacement < 1){ //si on recule plus loin que la case depart
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement + getCarreaux().size()));
			}else{
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement));
			}
		}
	}

	public void acheterProprieteGenreVraiment(Joueur j, Propriete p, Observateur observateur) {
		j.achatPropriété(p);
		Message msg = new Message(AFFICHER_ACHAT_PROPRIETE);
		observateur.notifier(msg);
	}
	/*
	Le controleur vérifie sur quelle case se trouve le joueur et lui propose une interaction adéquate
	*/
	public void interactionCarreau(Joueur j, Observateur observateur) {
		// verifie la case sur laquelle se trouve le joueur
		if (j.getPositionCourante() instanceof Gare
				|| j.getPositionCourante() instanceof ProprieteAConstruire
				|| j.getPositionCourante() instanceof Compagnie) { //si il tombe sur une case propriete
			Propriete p = (Propriete) j.getPositionCourante();
			if (p.getProprietaire() == null) {
				if (j.getCash() >= p.getPrix()) {
					Message msg = new Message(AFFICHER_DEMANDE_ACHETER_PROPRIETE);
					msg.propriete = p;
					observateur.notifier(msg);
				}
			} else if (j != p.getProprietaire()) { // si le joueur n'est pas le propriétaire , il paye le loyer
				j.payerCash(p.calculLoyer(this.getSommeDes()));
				p.getProprietaire().recevoirCash(p.calculLoyer(this.getSommeDes()));
				
				Message msg = new Message(AFFICHER_PAYER_LOYER);
				msg.joueur = j;
				msg.propriete = p;
				msg.loyer = p.calculLoyer(this.getSommeDes());
				observateur.notifier(msg); //affiche que le joueur doit payer un loyer
			}

		} else if (j.getPositionCourante() instanceof Taxe) {
			Taxe caseTaxe = (Taxe) j.getPositionCourante();
			j.payerTaxe(caseTaxe.getPrixTaxe());
			ParcPublic parc = (ParcPublic) this.getParcPublic();
			parc.encaisser(caseTaxe.getPrixTaxe());
			
			Message msg = new Message(AFFICHER_PAYER_TAXE);
			msg.carreau = caseTaxe;
			msg.joueur = j;
			observateur.notifier(msg);
		} else if (j.getPositionCourante() instanceof CaisseDeCommunaute) {
			Carte carte = this.piocherUneCarteCaisseDeCommunaute();
			
			Message msg = new Message(AFFICHER_CARTE_CAISSE_DE_COMMUNAUTE);
			msg.carte = carte;
			observateur.notifier(msg);
			
			carte.action(j, observateur, this);
		} else if (j.getPositionCourante() instanceof Chance) {
			Carte carte = this.piocherUneCarteChance();
			
			Message msg = new Message(AFFICHER_CARTE_CHANCE);
			msg.carte = carte;
			observateur.notifier(msg);
			
			carte.action(j, observateur, this);
		} else if (j.getPositionCourante() instanceof AllerEnPrison) {
			this.envoyerEnPrison(j);
			
		} else if (j.getPositionCourante() instanceof ParcPublic) {
			ParcPublic parc = (ParcPublic) j.getPositionCourante();
			parc.viderCaisse(j);
		}
	}

	/**
	 * @return the nbMaisonDisponible
	 */
	public int getNbMaisonDisponible() {
		return nbMaisonDisponible;
	}

	/**
	 * @param nbMaisonDisponible the nbMaisonDisponible to set
	 */
	public void setNbMaisonDisponible(int nbMaisonDisponible) {
		this.nbMaisonDisponible = nbMaisonDisponible;
	}

	/**
	 * @return the nbHotelDisponible
	 */
	public int getNbHotelDisponible() {
		return nbHotelDisponible;
	}

	/**
	 * @param nbHotelDisponible the nbHotelDisponible to set
	 */
	public void setNbHotelDisponible(int nbHotelDisponible) {
		this.nbHotelDisponible = nbHotelDisponible;
	}
}
