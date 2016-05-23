package Jeu;

import java.util.*;

public class Monopoly {

	private HashMap<Integer,Carreau> Carreaux;
	private ArrayList<Joueur> joueurs;
        private ArrayList<Integer> des;
        
    public Monopoly() {
        Carreaux = new HashMap();
        joueurs = new ArrayList<>();
        des = new ArrayList<>(2);
    }

    public void lancerDes() {
	this.des.clear();
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

		
    public void envoyerEnPrison(Joueur j) {
	j.setPositionCourante(this.getCarreaux().get(11));//la prison se trouve être la case 11
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

    public int getSommeDes() {
	return des.get(0)+des.get(1);
    }

    public void eliminerJoueur(Joueur j){
	joueurs.remove(j);
    }

}