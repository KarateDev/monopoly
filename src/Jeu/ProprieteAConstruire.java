package Jeu;
import java.util.HashSet;

public class ProprieteAConstruire extends Propriete {
    
    private Groupe groupe;
    private int loyerterrainnu;
    private int nbmaison;

    public ProprieteAConstruire(int numcase, String nomcarreau,int prixachat,int loyerterrainnu) {
        super(numcase, nomcarreau, prixachat);
        setloyerterrainnu(loyerterrainnu);
        this.nbmaison = 0;
    }

    @Override
    public int calculLoyer(int valeurdes) { //sans les maisons pour le moment
        HashSet<ProprieteAConstruire> paconstruires = this.getGroupe().getProprieteAConstruires();
        Boolean toutmemegroupe = true;
        for(ProprieteAConstruire p : paconstruires){
            if (!getProprietaire().getProprietes().contains(p)){
                toutmemegroupe = false;
            }
        }
        if (toutmemegroupe){
            return getoyeloyerterrainnu() * 2;
        }else{
            return getoyeloyerterrainnu();
        }
    }
    
    public void addMaison(){
        this.nbmaison += 1;
    }
    
    public int getNbmaison(){
        return this.nbmaison;
    }

    /**
     * @return the loyer
     */
    public int getoyeloyerterrainnu() {
        return loyerterrainnu;
    }

    /**
     * @param loyer the loyer to set
     */
    private void setloyerterrainnu(int loyer) {
        this.loyerterrainnu = loyer;
    }

    /**
     * @return the groupe
     */
    public Groupe getGroupe() {
        return groupe;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    
}