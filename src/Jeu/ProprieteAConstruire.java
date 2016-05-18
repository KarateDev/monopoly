package Jeu;
import java.util.HashSet;
import java.util.ArrayList;

public class ProprieteAConstruire extends Propriete {
    
    private CouleurPropriete couleur;
    private ArrayList<Integer> loyer; //en fonction du nombre de maison
    private int prixhypoteque;
    private int nbmaison;
    
    public final int PRIXMAISON = 100;

    public ProprieteAConstruire(int numcase, String nomcarreau,CouleurPropriete couleur,int prixachat,int loyerterrainnu
            , int loyer1maison, int loyer2maison, int loyer3maison, int loyer4maison, int loyer1hotel, int prixhypotheque) {
        super(numcase, nomcarreau, prixachat);
        this.couleur = couleur;
        loyer = new ArrayList<>();
        this.loyer.add(loyerterrainnu);
        this.loyer.add(loyer1maison);
        this.loyer.add(loyer2maison);
        this.loyer.add(loyer3maison);
        this.loyer.add(loyer4maison);
        this.loyer.add(loyer1hotel); // = 5maison
        this.prixhypoteque = prixhypotheque;
        this.nbmaison = 0;
    }

    @Override
    public int calculLoyer(int valeurdes) {
        int nbmaisonmemecouleur = 0;
        HashSet<Propriete> proprietes = this.getProprietaire().getProprietes();
        for (Propriete p : proprietes){                     // pour savoir si le joueur possede tout les terrains de meme couleur
            if (p.getClass() == ProprieteAConstruire.class){
                if (((ProprieteAConstruire) p).getCouleur() == this.getCouleur()){
                    nbmaisonmemecouleur +=1;
                }
            }
        }
        
        if (nbmaisonmemecouleur == 3 || (this.getCouleur() == CouleurPropriete.bleuFonce && nbmaisonmemecouleur == 2)){
            return getLoyer(getNbmaison()) *2;
        }else{
            return  getLoyer(getNbmaison());
        }

    }
    
    public void addMaison(){
            this.nbmaison += 1;
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

}