package UI;

import java.util.Scanner;

public class IHM {

	Controleur controleur;

    public Controleur getControleur() {
	return controleur;
    }

    public void setControleur(Controleur controleur) {
	this.controleur = controleur;
    }

	public String saisirNom(){
	    System.out.println("Entrez le nom du joueur :");
	    Scanner sc = new Scanner(System.in);
	    String nom = sc.nextLine();
	    return nom;
	}
}