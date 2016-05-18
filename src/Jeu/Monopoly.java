package Jeu;

import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> Carreaux;
	private ArrayList<Joueur> joueurs;
        private ArrayList<Integer> des;
        
        public Monopoly(){
            Carreaux = new HashMap();
            joueurs = new ArrayList<>();
            des = new ArrayList<>();
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
            this.joueurs.add(j);
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
        
        public ArrayList<Integer> getDes() {
            return des;
        }

        /**
         * @return the joueurs
         */
        public ArrayList<Joueur> getJoueurs() {
            return joueurs;
        }

}