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

	
	
	// attention !!! dans aller en prison et prison , les nbtours peuvent aller de 0 a 3 ou de 3 a 0 
    @Override
    public void action(Joueur j) {
		int nbTours = j.getNbTourEnPrison();

		if(nbTours == 1){
				j.payerCash(AMENDE);
				j.setNbTourEnPrison(0);	    
			}else if (nbTours > 0 && j.isAFaitUnDouble()){
				j.setNbTourEnPrison(0);
			}else{
				nbTours -= 1;
				j.setNbTourEnPrison(nbTours);
			}
		}
}
