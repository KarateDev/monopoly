package Jeu;

import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> Carreaux;
	private ArrayList<Joueur> joueurs;
	private ArrayList<Integer> des;

	private ArrayList<ArrayList<String>> piocheCarteChance;
	private ArrayList<ArrayList<String>> piocheCarteCaisseDeCommunaute;
	
	private int nbMaisonDisponible = 32;
	private int nbHotelDisponible = 12;
	  
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
		
	public void addCarreau(Carreau c){
		this.Carreaux.put(c.getNumero(),c);
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

	public ArrayList<String> piocherUneCarteChance(){
		ArrayList<String> carte = new ArrayList();

		carte = piocheCarteChance.get(0); // on recupere la carte du sommet de la pile
		piocheCarteChance.remove(carte); // on supprime la carte du sommet de la pile
		if (Integer.valueOf(carte.get(0)) != 5){ // si ce n'est pas la carte "libere de prison"
			piocheCarteChance.add(carte); // on la met en dessous de la pile
		}
		return carte;
	}

	public ArrayList<String> piocherUneCarteCaisseDeCommunaute(){
		ArrayList<String> carte = new ArrayList();

		carte = piocheCarteCaisseDeCommunaute.get(0); // on recupere la carte du sommet de la pile
		piocheCarteCaisseDeCommunaute.remove(carte); // on supprime la carte du sommet de la pile
		if (Integer.valueOf(carte.get(0)) != 5){
			piocheCarteCaisseDeCommunaute.add(carte); // on la met en dessous de la pile
		}
		return carte;
	}

	public void ajouterCarteChance(ArrayList<String> carte){
		piocheCarteChance.add(carte);
	}

	public void ajouterCarteCaisseDeCommunaute(ArrayList<String> carte){
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
