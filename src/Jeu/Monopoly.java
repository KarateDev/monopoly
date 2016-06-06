package Jeu;

import Jeu.Cartes.Carte;
import Jeu.Cartes.CarteLiberationPrison;
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
	  
	public Monopoly() {
		Carreaux = new HashMap();
		joueurs = new ArrayList<>();
		des = new ArrayList<>(2);
		piocheCarteChance = new ArrayList<>();
		piocheCarteCaisseDeCommunaute = new ArrayList<>();
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
