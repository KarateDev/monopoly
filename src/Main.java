import UI.Controleur;
/**
 *
 * @author mourerj
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		Controleur controleur = new Controleur();
		controleur.creerPlateau("./data.txt");
	}
	
}
