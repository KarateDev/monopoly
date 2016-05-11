package Jeu;
import java.util.HashSet;

public class ProprieteAConstruire extends Propriete {
    
    private CouleurPropriete couleur;
    private int loyerterrainnu;
    private int loyer1maison;
    private int loyer2maison;
    private int loyer3maison;
    private int loyer4maison;
    private int loyer1hotel;
    private int prixhypoteque;
    private int nbmaison;

    public ProprieteAConstruire(int numcase, String nomcarreau,CouleurPropriete couleur,int prixachat,int loyerterrainnu
            , int loyer1maison, int loyer2maison, int loyer3maison, int loyer4maison, int loyer1hotel, int prixhypotheque) {
        super(numcase, nomcarreau, prixachat);
        this.couleur = couleur;
        this.loyerterrainnu = loyerterrainnu;
        this.loyer1maison = loyer1maison;
        this.loyer2maison = loyer2maison;
        this.loyer3maison = loyer3maison;
        this.loyer4maison = loyer4maison;
        this.loyer1hotel = loyer1hotel; // = 5maison
        this.prixhypoteque = prixhypotheque;
        this.nbmaison = 0;
    }

    @Override
    public int calculLoyer(int valeurdes) { //sans les maisons pour le moment
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
            return loyerterrainnu *2;
        }else{
            return loyerterrainnu;
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

}