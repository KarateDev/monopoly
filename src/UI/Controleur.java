package UI;

import Jeu.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controleur {


	private IHM ihm;
	private Monopoly monopoly;
        
        public Controleur(){
            ihm = new IHM(this);
            monopoly = new Monopoly();
        }

	public void creerPlateau(String dataFilename){
		buildGamePlateau(dataFilename);
	}
	
	public IHM getIhm(){
		return ihm;
	}

	private void buildGamePlateau(String dataFilename)
	{
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
                                        Integer.valueOf(data.get(i)[10]), Integer.valueOf(data.get(i)[11])));
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
							getMonopoly().setPrison((Prison) getCarreau(Integer.valueOf(data.get(i)[1]))); 
							break;
					}
				}
				else
					System.err.println("[buildGamePleateau()] : Invalid Data type");
			}

		}
		catch(FileNotFoundException e){
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
            aFaitUnDouble = lancerDesAvancer(j); //on lance les des et on fait avancer le joueur
                    
            if (aFaitUnDouble){
                nbDouble ++;
                if (nbDouble == 3){ // si il a fait 3 doubles d'affilé,on sort de la boucle
                    break;
                }
            }
                    
				//affiche le carreau su lequel il tombe
            ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));  
			
		    interactionCarreau(j);	// gère les intercations du joueur avec le carreau
                    
            if (j.getCash() < 0){ //si le joueur n'a plus d'argent, il est eliminé
                ihm.afficherJoueurElimine(j);
                getMonopoly().eliminerJoueur(j);
                break;
            }
            if (aFaitUnDouble){
                ihm.afficherFaitUnDouble();
            }
            if (!ihm.attendreProchainTour()){ // si le joueur decide d'abandonner
				ihm.afficherJoueurElimine(j);
				monopoly.eliminerJoueur(j);
				break;
			}
			
        }while (aFaitUnDouble);
		
        if (nbDouble == 3){ //si le joueur a fait 3 doubles, on l'envoie en prison
            ihm.afficherJoueur3double(j);
		    monopoly.envoyerEnPrison(j);
			if (!ihm.attendreProchainTour()){ // si le joueur decide d'abandonner
				ihm.afficherJoueurElimine(j);
				monopoly.eliminerJoueur(j);
			}
        }
	}

	/**
	 * 
	 * @param j
	 */
	private boolean lancerDesAvancer(Joueur j) { //renvoi vrai si il a fait un double
		getMonopoly().lancerDes();
        int valeurdes = getMonopoly().getSommeDes(); //recupere la somme des dés
		
		ihm.afficherLancerDesDe(getMonopoly().getDes().get(0), getMonopoly().getDes().get(1)); //affiche les resultats des dés
		
        deplacerJoueur(j, valeurdes); // on deplace le joueur
			
        return getMonopoly().getDes().get(0) == getMonopoly().getDes().get(1);
	}
	
	/**
	 * 
	 * @param indice
	 */
	public Carreau getCarreau(int indice) {
		return getMonopoly().getCarreaux().get(indice);
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
	Le controlleur vérifie sur quelle case se trouve le joueur et lui propose une interaction adéquate
	*/
	public void interactionCarreau(Joueur j){
	    // verifie la case sur laquelle se trouve le joueur
                    if (j.getPositionCourante().getClass() == Gare.class 
                            || j.getPositionCourante().getClass() == ProprieteAConstruire.class 
                            || j.getPositionCourante().getClass() == Compagnie.class){ //si il tombe sur une case propriete
                        Propriete p = (Propriete) j.getPositionCourante();
			// si il n'y a pas de propriétaire, le joueur peut acheter la case
                        if (p.getProprietaire() == null){
                            if (j.getCash() >= p.getPrix()){
                                boolean reponse = ihm.afficherDemandeAcheterPropriete(p); //demande la reponse
                                if (reponse == true){
                                    j.achatPropriété(p);
                                    ihm.afficherAchatPropriete(p);
                                }
                            }
                        }else{
			    // si le joueur n'est pas le propriétaire , il paye le loyer
                            if (!j.equals(p.getProprietaire())){ 
                                j.payerCash(p.calculLoyer(getMonopoly().getSommeDes()));
                                p.getProprietaire().recevoirCash(p.calculLoyer(getMonopoly().getSommeDes()));
                                ihm.afficherPayerLoyer(j, p, p.calculLoyer(getMonopoly().getSommeDes())); //affiche que le joueur doit payer un loyer
                            }
                        }
                    }else if (j.getPositionCourante() instanceof Taxe) {
                        Taxe caseTaxe = (Taxe) j.getPositionCourante();
						j.payerTaxe(caseTaxe.getPrixTaxe());
						ihm.afficherPayerTaxe(j, caseTaxe);
                   } else if (j.getPositionCourante() instanceof CaisseDeCommunaute) {
						ArrayList<String> carte = monopoly.piocherUneCarteCaisseDeCommunaute();
						ihm.afficherCarteCaisseDeCommunaute(carte);
						actionCartes(carte,j);
                    } else if (j.getPositionCourante() instanceof Chance) {
						ArrayList<String> carte = monopoly.piocherUneCarteChance();
						ihm.afficherCarteChance(carte);
						actionCartes(carte,j);
                    } else if (j.getPositionCourante() instanceof AllerEnPrison) {
						monopoly.envoyerEnPrison(j);
		    }
	}
	
	public void initialiserCartes(String dataFilename){
		try {
			ArrayList<String[]> data = readDataFile(dataFilename, ",");

			//TODO: create cases instead of displaying
			for(int i=0; i<data.size(); ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("ch") == 0){
					ArrayList<String> carte = new ArrayList<>();
					if (data.get(i)[1].equals("4") || data.get(i)[1].equals("5")){
						carte.add(data.get(i)[1]);carte.add(data.get(i)[2]);
					}else if (data.get(i)[1].equals("0") || data.get(i)[1].equals("1") || data.get(i)[1].equals("3") || data.get(i)[1].equals("6") || data.get(i)[1].equals("7")){
						carte.add(data.get(i)[1]);carte.add(data.get(i)[2]);carte.add(data.get(i)[3]);
					}else{
						carte.add(data.get(i)[1]);carte.add(data.get(i)[2]);carte.add(data.get(i)[3]);carte.add(data.get(i)[4]);
					}
					monopoly.ajouterCarteChance(carte);
				}
				else if(caseType.compareTo("cdc") == 0){
					ArrayList<String> carte = new ArrayList<>();
					if (data.get(i)[1].equals("4") || data.get(i)[1].equals("5")){
						carte.add(data.get(i)[1]);carte.add(data.get(i)[2]);
					}else if (data.get(i)[1].equals("0") || data.get(i)[1].equals("1") || data.get(i)[1].equals("3") || data.get(i)[1].equals("6") || data.get(i)[1].equals("7")){
						carte.add(data.get(i)[1]);carte.add(data.get(i)[2]);carte.add(data.get(i)[3]);
					}else{
						carte.add(data.get(i)[1]);carte.add(data.get(i)[2]);carte.add(data.get(i)[3]);carte.add(data.get(i)[4]);
					}
					monopoly.ajouterCarteCaisseDeCommunaute(carte);
				}
				
				else
					System.err.println("[initialiserCartes()] : Invalid Data type");
			}

		}
		catch(FileNotFoundException e){
			System.err.println("[initialiserCartes()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[initialiserCartes()] : Error while reading file!");
		}
	}
	
	public void deplacerJoueur(Joueur j,int deplacement){ //cette fonction gere le deplacement du joueur avec le passage par la case depart
		
		int numeroCaseActuel = j.getPositionCourante().getNumero();
		
		if (deplacement > 0){ // si le deplacement nous fait avancer
			if (numeroCaseActuel + deplacement > monopoly.getCarreaux().size()){ //si on passe par la case depart
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement - monopoly.getCarreaux().size()));
				j.recevoirCash(((Depart) getCarreau(1)).getGainPourPassage());   //gain pour etre passe par la case depart
				ihm.affichePassageDepart(((Depart) getCarreau(1)).getGainPourPassage());
				ihm.afficherArgentRestant(j);
			}else{
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement));
			}
			
		}else{ // si le deplacement nous fait reculer (pour la carte chance)
			if (numeroCaseActuel + deplacement < 1){ //si on recule plus loin que la case depart
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement + monopoly.getCarreaux().size()));
			}else{
				j.setPositionCourante(getCarreau(numeroCaseActuel + deplacement));
			}
		}
	}
	
	public void actionCartes(ArrayList<String> carte, Joueur j){ //pour faire l'action de la carte
		switch (carte.get(0)) {
			case "0": //bouger d'un certain nombre de case
				deplacerJoueur(j, Integer.valueOf(carte.get(2)));
				ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));
				interactionCarreau(j);
				break;
				
			case "1": //recevoire ou payer de l'argent à la banque
				j.recevoirCash(Integer.valueOf(carte.get(2)));
				ihm.afficherArgentRestant(j);
				break;
				
			case "2": //payer de l'argent pour chaque propriete construite
				for (Propriete p : j.getProprietes()){
					if (p.getClass() == ProprieteAConstruire.class){
						if(((ProprieteAConstruire) p).getNbmaison() < 5){
							j.payerCash(-Integer.valueOf(carte.get(2)) * ((ProprieteAConstruire) p).getNbmaison());
						}else{
							j.payerCash(-Integer.valueOf(carte.get(3)));
						}
					}
				}
				ihm.afficherArgentRestant(j);
				break;
				
			case "3": //transaction entre tout les joueurs
				if (Integer.valueOf(carte.get(2)) < 0){ //vous donnez de l'argent aux autes joueur
					for (Joueur j2 : monopoly.getJoueurs()){
						if (!j.equals(j2)){
							j.payerCash(-Integer.valueOf(carte.get(2)));
							j2.recevoirCash(-Integer.valueOf(carte.get(2)));
						}
					}
				}else{	// les autes joueur vous donnent de l'argent
					for (Joueur j2 : monopoly.getJoueurs()){
						if (!j.equals(j2)){
							j.recevoirCash(Integer.valueOf(carte.get(2)));
							j2.payerCash(Integer.valueOf(carte.get(2)));
						}
					}
				}
				ihm.afficherArgentRestant(j);
				break;
				
			case "4": //aller en prison
				monopoly.envoyerEnPrison(j);
				break;
				
			case "5": //carte libere de prison
				j.ajouterCarteLibereDePrison();
				break; 
				
			case "6": //aller sur une case
				int deplacement = 0;	//calcul du deplacement
				if (Integer.valueOf(carte.get(2)) - j.getPositionCourante().getNumero() < 0){  //le deplacement doit etre positif
					deplacement = Integer.valueOf(carte.get(2)) - j.getPositionCourante().getNumero() + monopoly.getCarreaux().size();
				}else{
					deplacement = Integer.valueOf(carte.get(2)) - j.getPositionCourante().getNumero();
				}
				
				deplacerJoueur(j, deplacement); //deplacement du joueur
				ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));
				interactionCarreau(j);
				break;
				
			case "7": //avancer jusqu'a la case specifique la plus proche
				deplacement = 0;
				int nombreDeTour = 0;	//pour faire le modulo permetant de rester dans le vecteur
				
				if (carte.get(2).equals("gare")){	// si on doit aller sur la gare la plus proche
					while (getCarreau(j.getPositionCourante().getNumero() + deplacement).getClass() != Gare.class){
						deplacement ++;
						if (j.getPositionCourante().getNumero() + deplacement > monopoly.getCarreaux().size()){
							deplacement -= monopoly.getCarreaux().size();
							nombreDeTour ++;
						}
					}
					
				}else if (carte.get(2).equals("compagnie")){	//si on doit aller sur la compagnie la plus proche
					while (getCarreau(j.getPositionCourante().getNumero() + deplacement).getClass() != Compagnie.class){
						deplacement ++;
						if (j.getPositionCourante().getNumero() + deplacement > monopoly.getCarreaux().size()){
							deplacement -= monopoly.getCarreaux().size();
							nombreDeTour ++;
						}
					}
					
				}
				
				deplacerJoueur(j, deplacement + nombreDeTour*monopoly.getCarreaux().size());	//deplacement du joueur
				ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));	//affiche le carreau
				interactionCarreau(j);
				break;
		}
	}

	public boolean gestionPrison(Joueur j){
	    return true;
	}
}
