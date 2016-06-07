package UI;

import Jeu.*;
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


	private IHM ihm;
	private Monopoly monopoly;

		public Controleur() {
			ihm = new IHM(this);
			monopoly = new Monopoly((Observateur)ihm);
		}

	public void creerPlateau(String dataFilename) {
		buildGamePlateau(dataFilename);
	}

	public IHM getIhm() {
		return ihm;
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
			ihm.afficherJoueur(j);    //affichage des données du joueur
			
			if (j.getPositionCourante().getNumero() == getMonopoly().getPrison().getNumero() && j.getNbTourEnPrison() > 1){ //si il est en prison
				boolean libere = gestionPrison(j);
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
            ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));  
			
		    monopoly.interactionCarreau(j);	// gère les intercations du joueur avec le carreau
                    
            if (j.getCash() < 0){ //si le joueur n'a plus d'argent, il est eliminé
                ihm.afficherJoueurElimine(j);
                getMonopoly().eliminerJoueur(j);
                break;
            }
						
            if (aFaitUnDouble){
                ihm.afficherFaitUnDouble();
            }
			
			if(!interactionFinDeTour(j)){ // interaction pour les choix de fin de tour
				break;
			}
			
        }while (aFaitUnDouble);
		
        if (nbDouble == 3){ //si le joueur a fait 3 doubles, on l'envoie en prison
            ihm.afficherJoueur3double(j);
		    monopoly.envoyerEnPrison(j);
			interactionFinDeTour(j);
        }
	}
	
	public boolean interactionFinDeTour(Joueur j){ // retourn vrai pour continuer, faux pour abandonner
		String reponse = ihm.attendreProchainTour(j); // interaction de fin de tour
			if (reponse.equals("patrimoine")){ // si le joueur veut voir sont patrimoine
				ihm.afficherPatrimoine(j);
				return interactionFinDeTour(j); // relance l'interaction à la fin de l'achat
			}else if(reponse.equals("abandonner")){ // si le joueur decide d'abandonner
				ihm.afficherJoueurElimine(j);
				monopoly.eliminerJoueur(j);
				return false;
			}else if(reponse.equals("batiment")){ // si le joueur veut acheter des batiments
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

		ihm.afficherLancerDesDe(getMonopoly().getDes().get(0), getMonopoly().getDes().get(1)); //affiche les resultats des dés

		monopoly.deplacerJoueur(j, valeurdes); // on deplace le joueur

		return getMonopoly().getDes().get(0) == getMonopoly().getDes().get(1);
	}

	public void initialiserUnJoueur(String nomJoueur){
	    Joueur nouveauJoueur = new Joueur(nomJoueur, monopoly.getCarreaux().get(0));
	}
	
		public boolean initialiserUnePartie(){ //retourn vrai pour jouer at faux pour quitter le jeu
		//intialisation des joueurs
			boolean fin;
			int nbj;

			int choixMenu = ihm.afficherMenu();
			while (choixMenu != 2 || monopoly.getJoueurs().size() < 2){
				if (choixMenu == 1){
					nbj = monopoly.getJoueurs().size();
					fin = false;
					while(!fin || nbj < 2){
						String nom = ihm.saisirNom(nbj+1);
						Joueur j = new Joueur(nom,monopoly.getCarreaux().get(1));
						getMonopoly().addJoueur(j);
						nbj++;
						if(nbj >= 2){
							fin = ihm.finSaisie();
						}
					}

				}else if(choixMenu == 3){
					break;
				}
				choixMenu = ihm.afficherMenu();
			}
			return choixMenu != 3;
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
				if (j.getCash() >= p.getPrix() && ihm.afficherDemandeAcheterPropriete(p)) {
					j.achatPropriété(p);
					ihm.afficherAchatPropriete(p);
				}
			} else if (j != p.getProprietaire()) { // si le joueur n'est pas le propriétaire , il paye le loyer
				j.payerCash(p.calculLoyer(getMonopoly().getSommeDes()));
				p.getProprietaire().recevoirCash(p.calculLoyer(getMonopoly().getSommeDes()));
				ihm.afficherPayerLoyer(j, p, p.calculLoyer(getMonopoly().getSommeDes()));  //affiche que le joueur doit payer un loyer
			}

		} else if (j.getPositionCourante() instanceof Taxe) {
			Taxe caseTaxe = (Taxe) j.getPositionCourante();
			j.payerTaxe(caseTaxe.getPrixTaxe());
			ParcPublic parc = (ParcPublic) getMonopoly().getParcPublic();
			parc.encaisser(caseTaxe.getPrixTaxe());
			ihm.afficherPayerTaxe(j, caseTaxe);
			
		} else if (j.getPositionCourante() instanceof CaisseDeCommunaute) {
			Carte carte = monopoly.piocherUneCarteCaisseDeCommunaute();
			ihm.afficherCarteCaisseDeCommunaute(carte);
			carte.action(j, ihm, monopoly);
		} else if (j.getPositionCourante() instanceof Chance) {
			Carte carte = monopoly.piocherUneCarteChance();
			ihm.afficherCarteChance(carte);
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


	public boolean gestionPrison(Joueur j){
		int choix = ihm.afficherInteractionPrison(j);
		if (choix == 1){ // si il tente de faire un double
			monopoly.lancerDes();
			ihm.afficherLancerDesDe( monopoly.getDes().get(0),  monopoly.getDes().get(1));
			if (monopoly.getDes().get(0) == monopoly.getDes().get(1)){ // si il a fait un double
				j.setNbTourEnPrison(0);
				ihm.afficherFaitUnDouble();
				ihm.afficherLibereDePrison();
				return true;
			}else{
				j.setNbTourEnPrison(j.getNbTourEnPrison()-1);
				if (j.getNbTourEnPrison() == 0){ // si c'etait son dernier tour en prison
					j.payerCash(50);
					ihm.afficherDernierTourEnPrison();
					ihm.afficherArgentRestant(j);
					return true;
				}
				return false;
			}
		}else{ // si il utilise une carte pour se libere de prison
			j.setNbTourEnPrison(0);
			j.retirerCarteLibereDePrison();
			return true;
		}
	}
	
	public void achatBatiment(Joueur j, ProprieteAConstruire p){ //gere l'achat d'un batiment
		
		if (p.getNbmaison() < 4){
			monopoly.setNbMaisonDisponible(monopoly.getNbMaisonDisponible()-1); //on enleve 1 maison
		}else{
			monopoly.setNbHotelDisponible(monopoly.getNbHotelDisponible()-1); // on enleve 1 hotel
			monopoly.setNbMaisonDisponible(monopoly.getNbMaisonDisponible()+4); // on remet en jeu les 4 maisons qui sont remplacées par l'hotel
		}
		j.achatMaisonSurPropriete(p);
		ihm.afficherAchatBatiment(j, p);
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
				if (!proprieteConstructible.isEmpty()){ //si il y a des proprietes constructibles
					int reponse = ihm.afficherProprieteConstructible(proprieteConstructible,monopoly.getNbMaisonDisponible(),monopoly.getNbHotelDisponible()); //affiche les batiments constructibles et demande une reponse
					if (reponse != 0){ // si il achete une propriete
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
					}
				}else{
					ihm.afficherPasDeTerrainConstructible();
				}
			}else{
				ihm.afficherPasAsserDeBatiment();
			}
		}while ((monopoly.getNbHotelDisponible() > 0 || monopoly.getNbMaisonDisponible() > 0) && !proprieteConstructible.isEmpty() && ihm.demandeAchatBatiment()); // boucle tant que le joueur veut acheter et peut acheter
		
		
	}
	
}
