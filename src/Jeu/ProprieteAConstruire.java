package Jeu;
import java.util.HashSet;
import java.util.ArrayList;

public class ProprieteAConstruire extends Propriete {
    
    private CouleurPropriete couleur;
    private ArrayList<Integer> loyer; //en fonction du nombre de maison
	private int prixMaison;
	private int prixHotel;
    private int nbmaison;
    
    public ProprieteAConstruire(int numcase, String nomcarreau,CouleurPropriete couleur,int prixachat,int loyerterrainnu
            , int loyer1maison, int loyer2maison, int loyer3maison, int loyer4maison, int loyer1hotel, int prixMaison, int prixHotel) {
        super(numcase, nomcarreau, prixachat);
        this.couleur = couleur;
        loyer = new ArrayList<>();
        this.loyer.add(loyerterrainnu);
        this.loyer.add(loyer1maison);
        this.loyer.add(loyer2maison);
        this.loyer.add(loyer3maison);
        this.loyer.add(loyer4maison);
        this.loyer.add(loyer1hotel); // = 5maison
        this.prixMaison = prixMaison;
		this.prixHotel = prixHotel;
        this.nbmaison = 0;
    }

    @Override
    public int calculLoyer(int valeurdes) {

        if (getProprietaire().asToutesLesProprietesDeCouleur(couleur) && getNbmaison() == 0){
            return getLoyer(getNbmaison()) *2; // retourne le loyer du terrain *2 si le proprietaire a tout les terrain et aucune maison
        }else{
            return  getLoyer(getNbmaison());
        }

    }
    
    public void addMaison(){
            this.nbmaison += 1;
    }
	
	 public void setNbMaison(int nbMaison){
            this.nbmaison += nbMaison;
    }
    
    public int getNbmaison(){
        return this.nbmaison;
    }
    
    public CouleurPropriete getCouleur(){
        return this.couleur;
    }

    /**
     * @return the loyer
     */
    private Integer getLoyer(int i) { // i: nb de maisons
        return loyer.get(i);
    }

	/**
	 * @return the prixMaison
	 */
	public int getPrixBatiment(){
		int prix;
		if ( getNbmaison() == 4){
			return prixHotel;
		}else{
			return prixMaison;
		}
	}

}