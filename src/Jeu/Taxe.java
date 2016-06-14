package Jeu;

public class Taxe extends Carreau {
    
    private int prixTaxe;

    public Taxe(int num, String nomcarreau, int prixTaxe) {
        super(num, nomcarreau);
        this.prixTaxe = prixTaxe;
    }
    
    public int getPrixTaxe(){
        return this.prixTaxe;
    }

    @Override
    public void action(Joueur j) {
		j.payerTaxe(prixTaxe);
    }
}