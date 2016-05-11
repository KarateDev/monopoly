package Jeu;

import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> Carreaux;
	private HashMap<String,Joueur> joueurs;
        
        public Monopoly(){
            Carreaux = new HashMap();
            joueurs = new HashMap<>();
        }

	public int lancerDe() {
		Random rand = new Random();
                return rand.nextInt(6)+1;
	}

    public HashMap<Integer, Carreau> getCarreaux() {
	return Carreaux;
    }

    public void setCarreaux(HashMap<Integer, Carreau> Carreaux) {
	this.Carreaux = Carreaux;
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

}