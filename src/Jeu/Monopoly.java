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
        
        public void addJoueur(Joueur j){
            this.joueurs.put(j.getNomJoueur(),j);
        }

}