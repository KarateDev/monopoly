package Jeu;

import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> Carreaux;
	private HashMap<String,Joueur> joueurs;
        private HashSet<Integer> des;
        
        public Monopoly(){
            Carreaux = new HashMap();
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
        
        public void addCarreau(Carreau c){
            this.Carreaux.put(c.getNumero(),c);
        }

        /**
         * @return the Carreaux
         */
        public HashMap<Integer,Carreau> getCarreaux() {
            return Carreaux;
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

}