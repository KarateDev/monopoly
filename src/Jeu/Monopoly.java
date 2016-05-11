package Jeu;

import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> carreaux;
	private HashMap<String,Joueur> joueurs;
        private HashSet<Integer> des;
        
    public Monopoly(){
        carreaux = new HashMap<>();
        joueurs = new HashMap<>();
		des = new HashSet<>();
    }

	public void lancerDes() {
            des.clear();
            Random rand = new Random();
            int de = rand.nextInt(6)+1;
            des.add(de);
            de = rand.nextInt(6)+1;
            des.add(de);
	}

    public HashMap<Integer, Carreau> getCarreaux() {
	    HashMap<Integer, Carreau> Carreaux;
	return carreaux;
    }

    public void setCarreaux(HashMap<Integer, Carreau> carreaux) {
	this.carreaux = carreaux;
    }

    public HashMap<String, Joueur> getJoueurs() {
	return joueurs;
    }

    public void setJoueurs(HashMap<String, Joueur> joueurs) {
	this.joueurs = joueurs;
    }
        
        public void addJoueur(Joueur j){
            this.joueurs.put(j.getNomJoueur(),j);
        }
        
        public HashSet<Integer> getDes() {
            return des;
        }

        /**
         * @return the joueurs
         */


	public void addCarreau(Carreau c) {
		carreaux.put(carreaux.size()+1,c);
	}


}
