package UI;

import Jeu.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controleur {


	IHM ihm = new IHM(this);
	Monopoly monopoly = new Monopoly();

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
					monopoly.addCarreau(new ProprieteAConstruire(i, data.get(i)[2]));
				}
				else if(caseType.compareTo("G") == 0){
					System.out.println("Gare :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					monopoly.addCarreau(new Gare(i, data.get(i)[2]));
				}
				else if(caseType.compareTo("C") == 0){
					System.out.println("Compagnie :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					monopoly.addCarreau(new Compagnie(i, data.get(i)[2]));
				}
				else if(caseType.compareTo("AU") == 0){
					System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
					switch (data.get(i)[2]) {
						case "Départ":
							monopoly.addCarreau(new Depart());
							break;
						case "Impôt sur le revenu":
						case "Caisse de Communauté":
							monopoly.addCarreau(new CaisseDeCommunaute(i, data.get(i)[2]));
							break;
						case "Chance":
							monopoly.addCarreau(new Chance(i, data.get(i)[2]));
							break;
						case "Prison":
							monopoly.addCarreau(new Prison(i, data.get(i)[2]));
							break;
						case "Parc Gratuit":
							monopoly.addCarreau(new ParcPublic(i, data.get(i)[2]));
							break;

					}
					monopoly.addCarreau(new ProprieteAConstruire(i, data.get(i)[2]));
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
		// TODO - implement Controleur.jouerUnCoup
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param j
	 */
	private boolean lancerDesAvancer(Joueur j) {
		// TODO - implement Controleur.lancerDésAvancer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param indice
	 */
	public Carreau getCarreau(int indice) {
		// TODO - implement Controleur.getCarreau
		throw new UnsupportedOperationException();
	}

        public void initialiserUnePartie(){
	    //intialisation du plateau de jeu
	    int nbJoueurs = 0;
	    boolean fin = false;
		for (int i = 0; i != 6; ++i) {
			String nom = ihm.saisirNom();
			if (i >= 2 && nom.equals("fin")) break;
			Joueur j = new Joueur(nom);
			monopoly.addJoueur(j);
		
			
		}
            
        }
}
