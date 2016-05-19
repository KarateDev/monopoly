package UI;

import java.util.Scanner;
import Jeu.*;

public class IHM {

	Controleur controleur;
	public IHM (Controleur controleur) {
		this.controleur = controleur;
	}

    public Controleur getControleur() {
	return controleur;
    }

    public void setControleur(Controleur controleur) {
	this.controleur = controleur;
    }

    public String saisirNom(int i){ //i : numero du joueur 
	System.out.println("\nEntrez le nom du joueur n°"+i+" : ");
	Scanner sc = new Scanner(System.in);
	String nom = sc.nextLine();
        if (!nom.equals("fin")){
            System.out.println("Joueur ajouté.");
        }
	return nom;
    }
    
    public void afficherLancerDesDe(int i1, int i2){ //i1 et i2 : valeur des dés
        System.out.println("Vous lancer les dés et faites : "+i1+" et : "+i2);
    }
    
    public void affichePassageDepart(int i){
        System.out.println("Vous passer par la case depart et empocher : "+i+"€");
    }
    
    public void afficherCarreau(Carreau c, int i1, int i2){
        System.out.println("Vous etes sur le carreau n°"+c.getNumero());
        System.out.println("\n");
        System.out.println("Nom : "+c.getNomCarreau());
        if (c.getClass() == Gare.class || c.getClass() == ProprieteAConstruire.class || c.getClass() == Compagnie.class){
            if (((Propriete)c).getProprietaire() == null){
                System.out.println("Proprietaire : Aucun");
                System.out.println("Prix d'achat de la propriete : "+((Propriete)c).getPrix());
            }else{
                System.out.println("Proprietaire : "+((Propriete)c).getProprietaire().getNomJoueur());
                System.out.println("Loyer du terain : "+((Propriete)c).calculLoyer(i1+i2));
            }
            if (c.getClass() == ProprieteAConstruire.class){
                System.out.println("Couleur : "+((ProprieteAConstruire)c).getCouleur()+"\n");
            }
        }
        
    }
    
    public void afficherJoueur(Joueur j){
        System.out.println("\nTour du joueur : "+j.getNomJoueur());
        System.out.println("Argent disponible : "+j.getCash()+"€\n");
    }
    
    public void afficherPayerLoyer(Joueur j,Propriete p,int loyer){
         System.out.println("Vous payer un loyer de "+loyer+"€ à "+p.getProprietaire().getNomJoueur());
         System.out.println("Il vous reste : "+j.getCash()+"€\n");
    }
    
    public boolean afficherDemandeAcheterPropriete(Propriete p){
        Scanner sc = new Scanner(System.in);
        System.out.print("Voulez vous acheter cette propriete ? (oui/non) : ");
        String reponse = sc.nextLine();
        System.out.println("");
        return reponse.equals("oui");
    }
    
    public void afficherAchatPropriete(Propriete p){
        System.out.println("Vous avez acheté la propriété "+p.getNomCarreau()+" pour "+p.getPrix()+"€");
        System.out.println("Il vous reste : "+p.getProprietaire().getCash()+"€\n");
        
    }
    
    public void afficherJoueurElimine(Joueur j){
        System.out.println("Domage "+j.getNomJoueur()+" vous n'avez plus d'argent ...");
        System.out.println("Vous etes éliminé de la partie !\n");
    }
    
    public void afficherJoueur3double(Joueur j){
        System.out.println("Domage "+j.getNomJoueur()+" vous avez fait 3 doubles d'affilé ...");
        System.out.println("Vous etes envoyé en prison !\n");
    }
    
    public void attendreProchainTour(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Appuyer sur entré pour passer au tour suivant\n");
        sc.nextLine();
    }
    
    public void afficherFaitUnDouble(){
        System.out.println("Vous avez fait un double ! Vous pouvez donc rejouer !");
    }

    public boolean finSaisie() {
	boolean fin = false;
	String rep = "rien";
	while(!rep.equals("oui") && !rep.equals("non") ){
	System.out.println("Voulez vous stoper l'ajout de joueurs ? (oui/non) ");
	Scanner sc = new Scanner(System.in);
	rep = sc.nextLine();
	if(rep.equals("oui")){
	    fin = true;
	}else{
	    if(!rep.equals("non")){
		System.out.println("Vous devez repondre par oui ou non !");
	    }
	}
	}
	return fin;
    }
    
}