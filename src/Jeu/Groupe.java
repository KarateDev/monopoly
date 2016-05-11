package Jeu;

import java.util.HashSet;

public class Groupe {

	private CouleurPropriete couleur;
        private HashSet<ProprieteAConstruire> proprietes;
        
        public Groupe(CouleurPropriete c){
            proprietes = new HashSet<>();
            this.couleur = c;
        }
        
        public CouleurPropriete getCouleur(){
            return this.couleur;
        }
        
        public HashSet<ProprieteAConstruire> getProprieteAConstruires(){
            return this.proprietes;
        }
        
        public void addProprieteAConstruire(ProprieteAConstruire p){
            this.proprietes.add(p);
        }

}