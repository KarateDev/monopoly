package Jeu;

import Jeu.Cartes.Carte;
import Jeu.Cartes.CarteLiberationPrison;
import static UI.Message.*;
import UI.Observateur;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	private Carte carte = null;
	
	private int tricheId = 0;

	String[] tricheArray;
	
	public Monopoly() {
		try {//pour tricher
			tricheArray = loadDes("./src/Data/des_triche.txt", ",");
		}
		catch(FileNotFoundException e){
			System.err.println("[initialiserCartes()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[initialiserCartes()] : Error while reading file!");
		}
		
		Carreaux = new HashMap();
		joueurs = new ArrayList<>();
		des = new ArrayList<>();
		des.add(0);des.add(0);
		piocheCarteChance = new ArrayList<>();
		piocheCarteCaisseDeCommunaute = new ArrayList<>();
		this.observateur = observateur;
	}
	
	public void setObservateur(Observateur ihm){
		this.observateur = ihm;
	}
	
	public Carte getCarte(){
		return carte;
	}

	public void lancerDes() {
		this.getDes().clear();
		if (true) { //mode triche
			getDes().add(Integer.parseInt(tricheArray[tricheId++]));
			getDes().add(Integer.parseInt(tricheArray[tricheId++]));
			
		} else {
			Random rand = new Random();
			int de = rand.nextInt(6)+1;
			getDes().add(de);
			de = rand.nextInt(6)+1;
			getDes().add(de);
		}
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
		j.setPositionCourante(getPrison());//la prison se trouve être la case 11
		j.setNbTourEnPrison(3);
		observateur.notifier(AFFICHER_ALLER_EN_PRISON);
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
		return getDes().get(0)+getDes().get(1);
	}

	public void eliminerJoueur(Joueur j){
		for(Propriete p : j.getProprietes()){ //les proprietes du joueur retournent à la banque
			p.setProprietaire(null);
			if (p.getClass() == ProprieteAConstruire.class){ //on enleve les eventuelles maisons
				int nbmaison = ((ProprieteAConstruire) p).getNbmaison();
				if (nbmaison < 4){
					setNbMaisonDisponible(getNbMaisonDisponible()+nbmaison); //on rajoute les maisons au nombre de maison disponible dans la jeu
				}else{
					setNbHotelDisponible(getNbHotelDisponible()+1); // on rajoute 1 hotel
				}
				((ProprieteAConstruire) p).setNbMaison(0); // on enleve les maisons de la propriete
			}
		}
		joueurs.remove(j);
		
		if (getJoueurs().size() == 1){
			observateur.notifier(AFFICHER_FIN_PARTIE);
		}
	}

	public Carte piocherUneCarteChance() {
		Carte carte = getPiocheCarteChance().get(0); // on recupere la carte du sommet de la pile
		getPiocheCarteChance().remove(carte); // on supprime la carte du sommet de la pile
		if (!(carte instanceof CarteLiberationPrison)) { // si ce n'est pas la carte "libere de prison"
			getPiocheCarteChance().add(carte); // on la met en dessous de la pile
		}
		return carte;
	}

	public Carte piocherUneCarteCaisseDeCommunaute() {
		Carte carte = getPiocheCarteCaisseDeCommunaute().get(0); // on recupere la carte du sommet de la pile
		getPiocheCarteCaisseDeCommunaute().remove(carte); // on supprime la carte du sommet de la pile
		if (!(carte instanceof CarteLiberationPrison)) {
			getPiocheCarteCaisseDeCommunaute().add(carte); // on la met en dessous de la pile
		}
		return carte;
	}

	public void ajouterCarteChance(Carte carte) {
		getPiocheCarteChance().add(carte);
	}

	public void ajouterCarteCaisseDeCommunaute(Carte carte) {
		getPiocheCarteCaisseDeCommunaute().add(carte);
	}

	public void melangerLesCartes() {
		java.util.Collections.shuffle(getPiocheCarteChance());
		java.util.Collections.shuffle(getPiocheCarteCaisseDeCommunaute());
	}
	
	public void ajouterCarteLibereDePrison(){ // pour remetre la carte sous la pioche quand le joueur l'utilise
		CarteLiberationPrison carte = new CarteLiberationPrison("Vous etes libere de prison");
		
		if (getPiocheCarteChance().contains(CarteLiberationPrison.class)){
			ajouterCarteCaisseDeCommunaute(carte);
		}else{
			ajouterCarteChance(carte);
		}
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
				
				observateur.notifier(AFFICHER_PASSAGE_DEPART);
				
				observateur.notifier(AFFICHER_ARGENT_RESTANT);
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
		observateur.notifier(AFFICHER_ACHAT_PROPRIETE);
	}
	/*
	Le controleur vérifie sur quelle case se trouve le joueur et lui propose une interaction adéquate
	*/
	public void interactionCarreau(Joueur j) {
		// verifie la case sur laquelle se trouve le joueur
		if (j.getPositionCourante() instanceof Gare
				|| j.getPositionCourante() instanceof ProprieteAConstruire
				|| j.getPositionCourante() instanceof Compagnie) { //si il tombe sur une case propriete
			Propriete p = (Propriete) j.getPositionCourante();
			if (p.getProprietaire() == null) {
				if (j.getCash() >= p.getPrix()) {
					observateur.notifier(AFFICHER_DEMANDE_ACHETER_PROPRIETE);
				}
			} else if (j != p.getProprietaire()) { // si le joueur n'est pas le propriétaire , il paye le loyer
				j.payerCash(p.calculLoyer(this.getSommeDes()));
				p.getProprietaire().recevoirCash(p.calculLoyer(this.getSommeDes()));
				
				observateur.notifier(AFFICHER_PAYER_LOYER); //affiche que le joueur doit payer un loyer
			}

		} else if (j.getPositionCourante() instanceof Taxe) {
			Taxe caseTaxe = (Taxe) j.getPositionCourante();
			j.payerTaxe(caseTaxe.getPrixTaxe());
			ParcPublic parc = (ParcPublic) this.getParcPublic();
			parc.encaisser(caseTaxe.getPrixTaxe());
			
			observateur.notifier(AFFICHER_PAYER_TAXE);
		} else if (j.getPositionCourante() instanceof CaisseDeCommunaute) {
			carte = this.piocherUneCarteCaisseDeCommunaute();
			
			observateur.notifier(AFFICHER_CARTE_CAISSE_DE_COMMUNAUTE);
			
			carte.action(j, observateur, this);
		} else if (j.getPositionCourante() instanceof Chance) {
			carte = this.piocherUneCarteChance();
			
			observateur.notifier(AFFICHER_CARTE_CHANCE);
			
			carte.action(j, observateur, this);
		} else if (j.getPositionCourante() instanceof AllerEnPrison) {
			this.envoyerEnPrison(j);
			
		}else if (j.getPositionCourante() instanceof Prison) {
			if (j.getNbTourEnPrison() > 0){
				observateur.notifier(AFFICHER_INTERACTION_PRISON);
			}
			
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

	/**
	 * @return the piocheCarteChance
	 */
	public ArrayList<Carte> getPiocheCarteChance() {
		return piocheCarteChance;
	}

	/**
	 * @return the piocheCarteCaisseDeCommunaute
	 */
	public ArrayList<Carte> getPiocheCarteCaisseDeCommunaute() {
		return piocheCarteCaisseDeCommunaute;
	}

	private String[] loadDes(String filename, String token) throws FileNotFoundException, IOException
	{
		String[] data = null;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		while((line = reader.readLine()) != null){
			data = line.split(token);
		}
		reader.close();

		return data;
	}
}
