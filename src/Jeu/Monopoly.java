package Jeu;

import java.util.*;

public class Monopoly {

	private ArrayList carreaux;
	private HashMap<String,Joueur> joueurs;
        private HashSet<Integer> des;
        
    public Monopoly(){
        carreaux = new ArrayList();
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
        
        public void addJoueur(Joueur j){
            this.joueurs.put(j.getNomJoueur(),j);
        }
        
        public HashSet<Integer> getDes() {
            return des;
        }

        /**
         * @return the joueurs
         */
        public HashMap<String,Joueur> getJoueurs() {
            return joueurs;
        }

	public void addCarreau(Carreau c) {
		carreaux.add(c);
	}
>>>>>>> Stashed changes

}
