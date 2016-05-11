package Jeu;

import java.util.*;

public class Joueur {

	private Carreau positionCourante;
	private HashSet<Propriete> proprietes;
	private String nomJoueur;
	private int cash = 1500;
        
        public Joueur(String nomjoueur){
            proprietes = new HashSet<>();
            this.nomJoueur = nomjoueur;
        }

	/**
	 * @param l
	 */
	public void payerLoyer(int l) {
            if (getCash()-l<0){
                throw new UnsupportedOperationException();
            }
            setCash(getCash()-l);
	}

	/**
	 * 
	 * @param l
	 */
	public void recevoirLoyer(int l) {
            setCash(getCash()+l);
	}

	public int getnbgare() {
		HashSet proprietes = getProprietes();
                int nbgare = 0;
                for(Propriete p : this.proprietes){
                    if(p.getClass()== Gare.class){
                        nbgare += 1;
                    }
                }
                return nbgare;
	}

	public HashSet<Propriete> getProprietes() {
		return this.proprietes;
	}

	/**
	 * 
	 * @param propriété
	 */
	public boolean achatPropriété(Propriete propriete) {
		if(getCash() >= propriete.getPrix()){
                    propriete.setProprietaire(this);
                    addPropriete(propriete);
                    setCash(getCash()-propriete.getPrix());
                    return true;
                }else{
                    return false;
                }
	}

	public int getCash() {
		return this.cash;
	}

	public void addPropriete(Propriete propriete) {
		proprietes.add(propriete);
	}

	public Carreau getPositionCourante() {
		return this.positionCourante;
	}

	/**
	 * 
	 * @param pos
	 */
	public void setPositionCourante(Carreau pos) {
		this.positionCourante = pos;
	}

        /**
         * @return the nomJoueur
         */
        public String getNomJoueur() {
            return nomJoueur;
        }

        /**
         * @param cash the cash to set
         */
        public void setCash(int cash) {
            this.cash = cash;
        }

}