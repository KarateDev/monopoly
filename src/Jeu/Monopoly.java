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
        
        public void addCarreau(Carreau c){
            this.Carreaux.put(c.getNumero(),c);
        }

        /**
         * @return the Carreaux
         */
        public HashMap<Integer,Carreau> getCarreaux() {
            return Carreaux;
        }

        /**
         * @return the joueurs
         */
        public HashMap<String,Joueur> getJoueurs() {
            return joueurs;
        }

}