package UI;

import Jeu.*;
import static UI.Message.Type.*;
import Jeu.Cartes.Carte;
import Jeu.Cartes.CarteAllerPrison;
import Jeu.Cartes.CarteDeplacementAbsolu;
import Jeu.Cartes.CarteDeplacementRelatif;
import Jeu.Cartes.CarteDeplacementSemiAbsolu;
import Jeu.Cartes.CarteLiberationPrison;
import Jeu.Cartes.CartePayerParPropriete;
import Jeu.Cartes.CarteTransactionBanque;
import Jeu.Cartes.CarteTransactionJoueurs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Controleur {


	private Observateur ihm;
	private Monopoly monopoly;

	public Controleur(Observateur ihm) {
		this.monopoly = new Monopoly((Observateur)ihm);
		this.ihm = ihm;

		this.creerPlateau("./src/Data/data.txt");
		
		this.initialiserCartes("./src/Data/dataCartes.txt");
		this.getMonopoly().melangerLesCartes();

		this.initialiserUnePartie();
		
		int numeroJoueur = 0;
		while (this.getMonopoly().getJoueurs().size() > 1){ //boucle du jeu tant qu'il reste plus d'un joueur
			Joueur j = this.getMonopoly().getJoueurs().get(numeroJoueur);
			this.jouerUnCoup(j);
			numeroJoueur += 1;
			if (numeroJoueur > this.getMonopoly().getJoueurs().size()-1){
				numeroJoueur = 0;
			}
		}

		Message msg = new Message(AFFICHER_FIN_PARTIE);
		msg.joueur = this.getMonopoly().getJoueurs().get(0);
		ihm.notifier(msg);
	}
	

	public void creerPlateau(String dataFilename) {
		buildGamePlateau(dataFilename);
	}

	private void buildGamePlateau(String dataFilename) {
		try {
			ArrayList<String[]> data = readDataFile(dataFilename, ",");

			//TODO: create cases instead of displaying
			for(int i=0; i<data.size(); ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("P") == 0){
					System.out.println("Propriété :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					getMonopoly().addCarreau(new ProprieteAConstruire(i+1, data.get(i)[2], CouleurPropriete.valueOf(data.get(i)[3]),
										Integer.valueOf(data.get(i)[4]),Integer.valueOf(data.get(i)[5]),Integer.valueOf(data.get(i)[6]),
										Integer.valueOf(data.get(i)[7]),Integer.valueOf(data.get(i)[8]), Integer.valueOf(data.get(i)[9]),
										Integer.valueOf(data.get(i)[10]), Integer.valueOf(data.get(i)[11]), Integer.valueOf(data.get(i)[12])));
				}
				else if(caseType.compareTo("G") == 0){
					System.out.println("Gare :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					getMonopoly().addCarreau(new Gare(Integer.valueOf(data.get(i)[1]), data.get(i)[2],Integer.valueOf(data.get(i)[3])));
				}
				else if(caseType.compareTo("C") == 0){
					System.out.println("Compagnie :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					getMonopoly().addCarreau(new Compagnie(Integer.valueOf(data.get(i)[1]), data.get(i)[2],Integer.valueOf(data.get(i)[3])));
				}
				else if(caseType.compareTo("AU") == 0){
					System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					switch (data.get(i)[2]) {
						case "Départ":
							getMonopoly().addCarreau(new Depart(Integer.valueOf(data.get(i)[3])));
							break;
						case "Impôt sur le revenu":
							getMonopoly().addCarreau(new Taxe(Integer.valueOf(data.get(i)[1]), data.get(i)[2], Integer.valueOf(data.get(i)[3])));
							break;
						case "Taxe de Luxe":
							getMonopoly().addCarreau(new Taxe(Integer.valueOf(data.get(i)[1]), data.get(i)[2], Integer.valueOf(data.get(i)[3])));
							break;
						case "Caisse de Communauté":
							getMonopoly().addCarreau(new CaisseDeCommunaute(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;
						case "Chance":
							getMonopoly().addCarreau(new Chance(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;

						case "Parc Gratuit":
							getMonopoly().addCarreau(new ParcPublic(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;
						case "Allez en prison":
							getMonopoly().addCarreau(new AllerEnPrison(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;
						case "Simple Visite / En Prison":
							getMonopoly().addCarreau(new Prison(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;
					}

				}

				else
					System.err.println("[buildGamePleateau()] : Invalid Data type");
			}

		}
		catch(FileNotFoundException e) {
			System.err.println("[buildGamePlateau()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[buildGamePlateau()] : Error while reading file!");
		}
	}

	private ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		while((line = reader.readLine()) != null){
			data.add(line.split(token));
		}
		reader.close();

		return data;
	}
	/**
	 * 
	 * @param j
	 */
	public void jouerUnCoup(Joueur j) {
        int nbDouble = 0;
        boolean aFaitUnDouble;
                
        do{      //boucle tant que le joueur fait des doubles
			Message msg = new Message(AFFICHER_JOUEUR);
			msg.joueur = j;
			ihm.notifier(msg); //affichage des données du joueur
			
			if (j.getPositionCourante().getNumero() == getMonopoly().getPrison().getNumero() && j.getNbTourEnPrison() > 0){ //si il est en prison
				boolean libere = j.getPositionCourante() instanceof Prison;//gestionPrison(j);
				if (!libere){ // si il n'est pas libéré
					break; // on sort de la boucle pour qu'il ne joue pas
				} // si il est libéré, il joue normalement
			}
			
            aFaitUnDouble = lancerDesAvancer(j); //on lance les des et on fait avancer le joueur
                    
            if (aFaitUnDouble){
                nbDouble ++;
                if (nbDouble == 3){ // si il a fait 3 doubles d'affilé,on sort de la boucle
                    break;
                }
            }
                    
			//affiche le carreau sur lequel il tombe
			Message msg2 = new Message(AFFICHER_CARREAU);
			msg.joueur = j;
			msg.de1 = getMonopoly().getDes().get(0);
			msg.de2 = getMonopoly().getDes().get(1);
			ihm.notifier(msg2);

		    monopoly.interactionCarreau(j);	// gère les intercations du joueur avec le carreau

			if (j.getNbTourEnPrison() == 3) { // pour que si le joueur est envoyé en prison, son tour prend fin meme si il a fait un double
				aFaitUnDouble = false;
			}

            if (j.getCash() < 0) { //si le joueur n'a plus d'argent, il est eliminé
				Message msg3 = new Message(AFFICHER_JOUEUR_ELIMINE);
				msg3.joueur = j;
				ihm.notifier(msg3);
                break;
            }

            if (aFaitUnDouble) {
				Message msg3 = new Message(AFFICHER_FAIT_UN_DOUBLE);
				ihm.notifier(msg3);
            }

			if(!interactionFinDeTour(j)) { // interaction pour les choix de fin de tour
				break;
			}

        } while (aFaitUnDouble);

        if (nbDouble == 3){ //si le joueur a fait 3 doubles, on l'envoie en prison
			Message msg3 = new Message(AFFICHER_3D_DOUBLE);
			msg3.joueur = j;
			ihm.notifier(msg3);
		    monopoly.envoyerEnPrison(j);
			interactionFinDeTour(j);
        }
	}
	
	public boolean interactionFinDeTour(Joueur j) { // retourn vrai pour continuer, faux pour abandonner
		Message msg = new Message(AFFICHER_ATTENDRE_PROCHAIN_TOUR);
		msg.joueur = j;
		ihm.notifier(msg); // interaction de fin de tour
		return true;//changez moi !
	}
	public boolean finInteractionJoueur(String reponse, Joueur j) {
		if (reponse.equals("patrimoine")){ // si le joueur veut voir sont patrimoine
			Message msg = new Message(AFFICHER_PATRIMOINE);
			msg.joueur = j;
			ihm.notifier(msg);
			return interactionFinDeTour(j); // relance l'interaction à la fin de l'achat
		}else if(reponse.equals("abandonner")) { // si le joueur decide d'abandonner
			Message msg = new Message(AFFICHER_JOUEUR_ELIMINE);
			msg.joueur = j;
			ihm.notifier(msg);
			monopoly.eliminerJoueur(j);
			return false;
		}else if(reponse.equals("batiment")) { // si le joueur veut acheter des batiments
			interactionAchatBatiment(j);
			return interactionFinDeTour(j);
		}
		return true;
	}

	/**
	 * 
	 * @param j
	 */
	private boolean lancerDesAvancer(Joueur j) { //renvoi vrai si il a fait un double
		getMonopoly().lancerDes();
		int valeurdes = getMonopoly().getSommeDes(); //recupere la somme des dés

		Message msg = new Message(AFFICHER_LANCER_DES);
		msg.de1 = getMonopoly().getDes().get(0);
		msg.de2 = getMonopoly().getDes().get(1);
		ihm.notifier(msg);
		//ihm.afficherLancerDesDe(getMonopoly().getDes().get(0), getMonopoly().getDes().get(1)); //affiche les resultats des dés

		monopoly.deplacerJoueur(j, valeurdes); // on deplace le joueur

		return getMonopoly().getDes().get(0) == getMonopoly().getDes().get(1);
	}

	public void initialiserUnJoueur(String nomJoueur, CouleurPropriete couleur){
	    Joueur nouveauJoueur = new Joueur(nomJoueur,couleur, monopoly.getCarreaux().get(0));
	}
	
	public void initialiserUnePartie() { //retourn vrai pour jouer at faux pour quitter le jeu
		//intialisation des joueurs

		//int choixMenu = ihm.afficherMenu();
		Message msg = new Message(AFFICHER_MENU);
		ihm.notifier(msg);
	}
	public void quitterJeu() {
		System.exit(0);
	}

		/**
		 * @return the monopoly
		 */
		public Monopoly getMonopoly() {
			return monopoly;
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
					Message msg = new Message(AFFICHER_DEMANDE_ACHETER_PROPRIETE);
					msg.propriete = p;
					ihm.notifier(msg);
				}
			} else if (j != p.getProprietaire()) { // si le joueur n'est pas le propriétaire , il paye le loyer
				j.payerCash(p.calculLoyer(getMonopoly().getSommeDes()));
				p.getProprietaire().recevoirCash(p.calculLoyer(getMonopoly().getSommeDes()));
				Message msg = new Message(AFFICHER_PAYER_LOYER);
				msg.joueur = j;
				msg.propriete = p;
				msg.loyer = p.calculLoyer(getMonopoly().getSommeDes());
				ihm.notifier(msg); //affiche que le joueur doit payer un loyer
			}

		} else if (j.getPositionCourante() instanceof Taxe) {
			Taxe caseTaxe = (Taxe) j.getPositionCourante();
			j.payerTaxe(caseTaxe.getPrixTaxe());
			ParcPublic parc = (ParcPublic) getMonopoly().getParcPublic();
			parc.encaisser(caseTaxe.getPrixTaxe());
			
			Message msg = new Message(AFFICHER_PAYER_TAXE);
			msg.joueur = j;
			msg.carreau = caseTaxe;
			ihm.notifier(msg);
			
		} else if (j.getPositionCourante() instanceof CaisseDeCommunaute) {
			Carte carte = monopoly.piocherUneCarteCaisseDeCommunaute();
			
			Message msg = new Message(AFFICHER_CARTE_CAISSE_DE_COMMUNAUTE);
			msg.carte = carte;
			ihm.notifier(msg);
			//ihm.afficherCarteCaisseDeCommunaute(carte);
			carte.action(j, ihm, monopoly);
		} else if (j.getPositionCourante() instanceof Chance) {
			Carte carte = monopoly.piocherUneCarteChance();
			
			Message msg = new Message(AFFICHER_CARTE_CHANCE);
			msg.carte = carte;
			ihm.notifier(msg);
			//ihm.afficherCarteChance(carte);
			carte.action(j, ihm, monopoly);
		} else if (j.getPositionCourante() instanceof AllerEnPrison) {
			monopoly.envoyerEnPrison(j);
			
		} else if (j.getPositionCourante() instanceof ParcPublic) {
			ParcPublic parc = (ParcPublic) j.getPositionCourante();
			parc.viderCaisse(j);
		}
		
	}

	public void initialiserCartes(String dataFilename) {
		try {
			ArrayList<String[]> data = readDataFile(dataFilename, ",");

			//TODO: create cases instead of displaying
			for(int i=0; i<data.size(); ++i){
				
				Carte carte;
				switch (data.get(i)[1]) {
					case "0":
						carte = new CarteDeplacementRelatif(data.get(i)[2], Integer.parseInt(data.get(i)[3]));
						break;
					case "1":
						carte = new CarteTransactionBanque(data.get(i)[2], Integer.parseInt(data.get(i)[3]));
						break;
					case "2":
						carte = new CartePayerParPropriete(data.get(i)[2], Integer.parseInt(data.get(i)[3]));
						break;
					case "3":
						carte = new CarteTransactionJoueurs(data.get(i)[2], Integer.parseInt(data.get(i)[3]));
						break;
					case "4":
						carte = new CarteAllerPrison(data.get(i)[2]);
						break;
					case "5":
						carte = new CarteLiberationPrison(data.get(i)[2]);
						break;
					case "6":
						carte = new CarteDeplacementAbsolu(data.get(i)[2], monopoly.getCarreaux().get(3));
						break;
					case "7":
						carte = new CarteDeplacementSemiAbsolu(data.get(i)[2], data.get(i)[3]);
						break;
					default:
						System.err.println("Carte de type inconnue: " + data.get(i)[1]);
						System.exit(1);
						return;
				}
					
				String caseType = data.get(i)[0];
				if(caseType.compareTo("ch") == 0) {
					monopoly.ajouterCarteChance(carte);
				} else if(caseType.compareTo("cdc") == 0) {
					monopoly.ajouterCarteCaisseDeCommunaute(carte);
				} else System.err.println("[initialiserCartes()] : Invalid Data type");
			}

		}
		catch(FileNotFoundException e){
			System.err.println("[initialiserCartes()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[initialiserCartes()] : Error while reading file!");
		}
	}


	public void gestionPrison(Joueur j) {
		Message msg = new Message(AFFICHER_INTERACTION_PRISON);
		msg.joueur = j;
		ihm.notifier(msg);
	}
	public void tenteSortiePrisonDouble(Joueur j) {
			monopoly.lancerDes();
			Message msg = new Message(AFFICHER_LANCER_DES);
			msg.de1 = monopoly.getDes().get(0);
			msg.de2 = monopoly.getDes().get(1);
			ihm.notifier(msg);
			
			if (monopoly.getDes().get(0) == monopoly.getDes().get(1)){ // si il a fait un double
				j.setNbTourEnPrison(0);
				Message msg2 = new Message(AFFICHER_FAIT_UN_DOUBLE);
				ihm.notifier(msg2);
				
				Message msg3 = new Message(AFFICHER_LIBERE_PRISON);
				ihm.notifier(msg3);
				//return true;
			}else{
				j.setNbTourEnPrison(j.getNbTourEnPrison()-1);
				if (j.getNbTourEnPrison() == 0){ // si c'etait son dernier tour en prison
					j.payerCash(50);
					Message msg4 = new Message(AFFICHER_DERNIER_TOUR_EN_PRISON);
					ihm.notifier(msg4);
					
					Message msg5 = new Message(AFFICHER_ARGENT_RESTANT);
					msg5.joueur = j;
					ihm.notifier(msg5);
					//return true;
				}
				//return false;
			}
	}
	public void tenterSortiePrisonCarte(Joueur j) {
			j.setNbTourEnPrison(0);
			j.retirerCarteLibereDePrison();
			monopoly.ajouterCarteLibereDePrison();
			//return true;
	}
	
	public void achatBatiment(Joueur j, ProprieteAConstruire p){ //gere l'achat d'un batiment
		
		if (p.getNbmaison() < 4){
			monopoly.setNbMaisonDisponible(monopoly.getNbMaisonDisponible()-1); //on enleve 1 maison
		}else{
			monopoly.setNbHotelDisponible(monopoly.getNbHotelDisponible()-1); // on enleve 1 hotel
			monopoly.setNbMaisonDisponible(monopoly.getNbMaisonDisponible()+4); // on remet en jeu les 4 maisons qui sont remplacées par l'hotel
		}
		j.achatMaisonSurPropriete(p);
		Message msg = new Message(AFFICHER_ACHAT_BATIMENT);
		ihm.notifier(msg);
		
	}
	
	public void interactionAchatBatiment(Joueur j){ //gere l'interaction entre le joueur et les batiments
		
		ArrayList<ProprieteAConstruire> proprieteConstructible = new ArrayList<>();
		do{
			if (monopoly.getNbHotelDisponible() > 0 || monopoly.getNbMaisonDisponible() > 0){
				proprieteConstructible = new ArrayList<> (); //proprietes sur lequelles on peut construire
				for (Propriete p : j.getProprietes()){
					if (p.getClass() == ProprieteAConstruire.class && j.asToutesLesProprietesDeCouleur(((ProprieteAConstruire) p).getCouleur())){
						proprieteConstructible.add((ProprieteAConstruire) p);
					}
				}
				proprieteConstructible.sort(new Comparator<ProprieteAConstruire>() { // pour trier le vecteur par couleur des proprietées
					@Override
					public int compare(ProprieteAConstruire o1, ProprieteAConstruire o2) {
						return o1.getCouleur().toString().compareTo(o2.getCouleur().toString());
					}
				});
				if (!proprieteConstructible.isEmpty()) { //si il y a des proprietes constructibles
					Message msg = new Message(AFFICHER_PROPRIETE_CONSTRUCTIBLE);
					msg.proprieteConstructible = proprieteConstructible;
					msg.joueur = j;
					ihm.notifier(msg);
					
					//int reponse = ihm.afficherProprieteConstructible(proprieteConstructible,monopoly.getNbMaisonDisponible(),monopoly.getNbHotelDisponible()); //affiche les batiments constructibles et demande une reponse
					/*if (reponse != 0){ // si il achete une propriete
						if (proprieteConstructible.get(reponse-1).getPrixBatiment() <= j.getCash()){ // si il peut acheter le batiment
							if ((proprieteConstructible.get(reponse-1).getNbmaison() == 4 && monopoly.getNbHotelDisponible() > 0) || (proprieteConstructible.get(reponse-1).getNbmaison() < 4 && monopoly.getNbMaisonDisponible() > 0) ){ // si il reste des batiments du type qu'il veut construire
								achatBatiment(j, proprieteConstructible.get(reponse-1));
							}else{
								ihm.afficherPasAsserDeBatiment();
							}
						}else{
							ihm.afficherPasAsserArgent();
						}
					}else{ // si il quite l'achat de batiment
						break;
					}*/
				} else {
					Message msg = new Message(AFFICHER_PAS_DE_TERRAIN_CONSTRUCTIBLE);
					ihm.notifier(msg);
				}
			}else{
				Message msg = new Message(AFFICHER_PAS_ASSEZ_DE_BATIMENTS);
				ihm.notifier(msg);
			}
		}while ((monopoly.getNbHotelDisponible() > 0 || monopoly.getNbMaisonDisponible() > 0) && !proprieteConstructible.isEmpty()/* && ihm.demandeAchatBatiment()*/); // boucle tant que le joueur veut acheter et peut acheter
		
		
	}
	
	public void acheterBatiment(ArrayList<ProprieteAConstruire> proprieteConstructible, int reponse, Joueur j) {
		if (proprieteConstructible.get(reponse-1).getPrixBatiment() <= j.getCash()){ // si il peut acheter le batiment
			if ((proprieteConstructible.get(reponse-1).getNbmaison() == 4 && monopoly.getNbHotelDisponible() > 0) || (proprieteConstructible.get(reponse-1).getNbmaison() < 4 && monopoly.getNbMaisonDisponible() > 0) ){ // si il reste des batiments du type qu'il veut construire
				achatBatiment(j, proprieteConstructible.get(reponse-1));
			}else{
				Message msg = new Message(AFFICHER_PAS_ASSEZ_DE_BATIMENTS);
				ihm.notifier(msg);
			}
		}else{
			Message msg1 = new Message(AFFICHER_PAS_ASSEZ_DARGENT);
			ihm.notifier(msg1);
		}
	}
	
}
