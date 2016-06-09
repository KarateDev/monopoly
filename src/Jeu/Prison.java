package Jeu;

/**
 *
 * @author mourerj
 */
public class Prison extends Carreau {
    private final int AMENDE = 50;
	
	public Prison(int num, String nomcarreau) {
		super(num, nomcarreau);
	}

    @Override
    public void action(Joueur j) {
	int nbTours = j.getNbTourEnPrison();
	
	if(nbTours == 2){
	    j.payerCash(AMENDE);
	    j.setNbTourEnPrison(3);	    
	}else if (nbTours < 3 && j.isAFaitUnDouble()){
	    j.setNbTourEnPrison(3);
	}else{
	    nbTours += 1;
	    j.setNbTourEnPrison(nbTours);
	}
    }
}
