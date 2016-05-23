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
						case "Prison":
							getMonopoly().addCarreau(new Prison(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;
						case "Parc Gratuit":
							getMonopoly().addCarreau(new ParcPublic(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
							break;
						case "Allez en prison":
						case "Simple Visite / En Prison":
							getMonopoly().addCarreau(new AllerEnPrison(Integer.valueOf(data.get(i)[1]), data.get(i)[2]));
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
                
                do{                                         //boucle tant que le joueur fait des doubles
                    aFaitUnDouble = lancerDesAvancer(j);
                    
                    if (aFaitUnDouble){
                        nbDouble ++;
                        if (nbDouble == 3){
			    ihm.afficherJoueur(j);    //affichage des données du joueur
			    ihm.afficherLancerDesDe(getMonopoly().getDes().get(0), getMonopoly().getDes().get(1)); //affiche les resultats des dés
                            break;
                        }
                    }
                    
                    ihm.afficherJoueur(j);    //affichage des données du joueur
                    ihm.afficherLancerDesDe(getMonopoly().getDes().get(0), getMonopoly().getDes().get(1)); //affiche les resultats des dés
                    if (j.getPositionCourante().getNumero() - getMonopoly().getSommeDes() < 1){ //si le joueur passe par la case depart
                        ihm.affichePassageDepart(((Depart) getCarreau(1)).getGainPourPassage());
                    }
                    ihm.afficherCarreau(j.getPositionCourante(), getMonopoly().getDes().get(0), getMonopoly().getDes().get(1));  //affiche le carreau su lequel il tombe


                 

		    // gère les intercations du joueur avec le carreau
		    interactionCarreau(j);
                    

                    if (j.getCash() < 0){ //si le joueur n'a plus d'argent, il est eliminé
                        ihm.afficherJoueurElimine(j);
                        getMonopoly().eliminerJoueur(j);
                        break;
                    }
                    if (aFaitUnDouble){
                        ihm.afficherFaitUnDouble();
                    }
                    ihm.attendreProchainTour();
                }while (aFaitUnDouble);
                if (nbDouble == 3){ //on envoie le joueur en prison  a completer
                    ihm.afficherJoueur3double(j);
		    j.setPositionCourante(getCarreau(11));
                }
	}

	/**
	 * 
	 * @param j
	 */
	private boolean lancerDesAvancer(Joueur j) { //renvoi vrai si il a fait un double
		getMonopoly().lancerDes();
                int valeurdes = getMonopoly().getSommeDes(); //recupere la somme des dés
                int positionsuivante = j.getPositionCourante().getNumero() +valeurdes; //calcul la position suivante
                if (positionsuivante > getMonopoly().getCarreaux().size()){  //si on passe par la case depart
                    j.recevoirCash(((Depart) getCarreau(1)).getGainPourPassage());
                    positionsuivante -=  getMonopoly().getCarreaux().size();
                }
                j.setPositionCourante(getCarreau(positionsuivante));    
                return getMonopoly().getDes().get(0) == getMonopoly().getDes().get(1);
	}

	/**
	 * 
	 * @param indice
	 */
	public Carreau getCarreau(int indice) {
		return getMonopoly().getCarreaux().get(indice);
	}

        public void initialiserUnePartie(){
	    //intialisation du plateau de jeu
	    int nbJoueurs = 0;
	    boolean fin = false;
		int nbj = 1;
		while(!fin ){
		    String nom = ihm.saisirNom(nbj); 
		    if(nbj >= 2){
			fin = ihm.finSaisie();
		    }
		    Joueur j = new Joueur(nom,monopoly.getCarreaux().get(1));
		    getMonopoly().addJoueur(j);
		    nbj++;
		}
		
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
                        //a completer (si il tombe sur une case caisse de communaute)
                    } else if (j.getPositionCourante() instanceof Chance) {
                        //a completer (si il tombe sur une case chance)
                    } else if (j.getPositionCourante() instanceof AllerEnPrison) {
						monopoly.envoyerEnPrison(j);
		    }
	}

}
