package Jeu;

public class ParcPublic extends Carreau{

    private int caisse ;
    
    public ParcPublic(int num, String nomcarreau) {
        super(num, nomcarreau);
    }
    
    public void encaisser(int virement){
	caisse = caisse + virement;
    }
    
    public void viderCaisse(Joueur j){
	j.recevoirCash(caisse);
	caisse = 0;
    }
}