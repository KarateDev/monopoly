package UI;

import java.util.Scanner;
import java.util.ArrayList;
import Jeu.*;
import Jeu.Cartes.Carte;
import static UI.Message.*;

public class IHM implements Observateur {
	Controleur controleur;
	
   
    public static void main(String[] args) {
		IHM ihm = new IHM();
		Controleur controleur = new Controleur();
		controleur.setObservateur(ihm);
		ihm.setControleur(controleur);
	}

	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}


    public String saisirNom(int i){ //i : numero du joueur 
		System.out.println("\nEntrez le nom du joueur n°"+i+" : ");
		Scanner sc = new Scanner(System.in);
		String nom = sc.nextLine();
        System.out.println("Joueur ajouté.");
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
                System.out.println("Couleur : "+((ProprieteAConstruire)c).getCouleur());
            }
        }
		System.out.println("");
        
    }
    
    public void afficherJoueur(Joueur j){
		System.out.println("\n-------------------------------------------------------------------");
        System.out.println("\nTour du joueur : "+j.getNomJoueur());
        System.out.println("Argent disponible : "+j.getCash()+"€\n");
    }
    
    public void afficherPayerLoyer(Joueur j, Propriete p ,int loyer) {
         System.out.println("Vous payer un loyer de "+loyer+"€ à "+p.getProprietaire().getNomJoueur());
         afficherArgentRestant(j);
    }
    
    public void afficherPayerTaxe(Joueur j , Taxe t){
		System.out.println(t.getNomCarreau() + " : Vous payez " + -t.getPrixTaxe() + "€ au trésor public ");
        afficherArgentRestant(j);
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
        afficherArgentRestant(p.getProprietaire());
        
    }
	
	public void afficherArgentRestant(Joueur j){
		System.out.println("Il vous reste : "+j.getCash()+"€\n");
	}
    
    public void afficherJoueurElimine(Joueur j){
        System.out.println("Domage "+j.getNomJoueur()+" vous n'avez plus d'argent ...");
        System.out.println("Vous etes éliminé de la partie !\n");
    }
    
    public void afficherJoueur3double(Joueur j){
        System.out.println("Domage "+j.getNomJoueur()+" vous avez fait 3 doubles d'affilé ...");
        System.out.println("Vous etes envoyé en prison !\n");
    }
    
    public String attendreProchainTour(Joueur j) { // return la reponse du joueur
        String reponse;
		Scanner sc = new Scanner(System.in);
        System.out.println("\nAppuyer sur entré pour passer au tour suivant");
		System.out.println("('abandonner' pour abandonner / 'patrimoine' pour voir votre patrimoine / 'batiment' pour acheter des batiments)\n");
		reponse = sc.nextLine();
		
        
		return reponse;
		
    }
    
    public void afficherFaitUnDouble(){
        System.out.println("Vous avez fait un double ! Vous pouvez donc rejouer !");
    }

    public boolean finSaisie() {
		boolean fin = false;
		String rep = "rien";
		while (!rep.equals("oui") && !rep.equals("non") ) {
			System.out.println("Voulez vous stoper l'ajout de joueurs ? (oui/non) ");
			Scanner sc = new Scanner(System.in);
			rep = sc.nextLine();
			if (rep.equals("oui")) {
				fin = true;
			} else if(!rep.equals("non")) {
					System.out.println("Vous devez repondre par oui ou non !");
			}
		}
		return fin;
    }
	
	public void afficherCarteChance(Carte carte){
		System.out.println("\nVous avez pioché une carte chance :");
		System.out.println(carte.getIntitule() + "\n");
	}
	
	public void afficherCarteCaisseDeCommunaute(Carte carte) {
		System.out.println("\nVous avez pioché une carte Caisse de communaute :");
		System.out.println(carte.getIntitule() + "\n");
	}
	
	public void afficherFinDePartie(Joueur j){
		System.out.println("\nLa partie est terminée !");
		System.out.println("Felicitation au joueur "+j.getNomJoueur()+" pour sa victoire écrasante sur ses enemis !");
		System.out.println("Il termine avec "+j.getCash()+"€ à son compteur.");
	}
	
	public int afficherMenu(){
		int choix = 0;
		do{
			System.out.println("\nmenu :");
			System.out.println("1. Inscrire les joueurs");
			System.out.println("2. Commencer le jeu");
			System.out.println("3. Quitter");
			System.out.print("Choix : ");
			Scanner sc = new Scanner(System.in);
			try{
				choix = sc.nextInt();
			}catch (Exception e){
				System.out.println("La valeure entré est invalide !");
			}
		}while(choix != 1 && choix != 2 && choix != 3);
		return choix;
	}
	
	public boolean demandeAchatBatiment(){ //retourn vrai si il veut acheter
		Scanner sc = new Scanner(System.in);
		String reponse;
		do{
			System.out.println("Voulez vous continuer à acheter des batiments ? (oui/non) : ");
			reponse = sc.nextLine();
		}while (!reponse.equals("oui") && !reponse.equals("non"));
		return reponse.equals("oui");
	} 
	
	public int afficherProprieteConstructible(ArrayList<ProprieteAConstruire> proprietes, int nbmaisondisponible, int nbhoteldisponible){
		Scanner sc = new Scanner(System.in);
		int reponse;
		ArrayList<Integer> reponsePossible = new ArrayList<>();
		do{
			System.out.println("\nNombre de maison disponible : "+nbmaisondisponible+" Nombre d'hotel disponible : "+nbhoteldisponible);
			System.out.println("Choix du batiment à acheter : ");
			System.out.println("\n0 : Quitter");
			reponsePossible.add(0);
			int i;
			for (i = 0; i < proprietes.size(); i++){
				int prix = proprietes.get(i).getPrixBatiment();
				String type;
				if (proprietes.get(i).getNbmaison() != 5){ // si il n'y a pas deja le maximum de maison
					if (proprietes.get(i).getNbmaison() == 4){
						type = "l'hotel";
					}else{
						type = "la maison";
					}
					System.out.print(i+1 +" : "+proprietes.get(i).getNomCarreau());
					System.out.print(" / Couleur : "+proprietes.get(i).getCouleur().toString());
					System.out.print(" / Nombre de maison : "+proprietes.get(i).getNbmaison());
					System.out.println(" / Cout de "+type+" : "+prix+"€");
					reponsePossible.add(i+1);
				}else{
					System.out.print("  : "+proprietes.get(i).getNomCarreau());
					System.out.print(" / Couleur : "+proprietes.get(i).getCouleur().toString());
					System.out.println(" / Nombre de maison : "+proprietes.get(i).getNbmaison());
				}
				
			}
			try{
				reponse = sc.nextInt();
			}catch (Exception e){
				sc.reset();
				System.out.println("Reponse invalide !");
				reponse = -1;
			}
		}while (reponse < 0 || reponse > proprietes.size() || !reponsePossible.contains(reponse));
		return reponse;
	}
	
	public void afficherAchatBatiment(Joueur j, ProprieteAConstruire p){
		int prix = p.getPrixBatiment();
		String type;
		if (p.getNbmaison() == 5){
			type = "un hotel";
		}else{
			type = "une maison";
		}
		System.out.println("Felicitation "+j.getNomJoueur()+", vous avez acheté "+type+" pour "+prix+"€ !");
		afficherArgentRestant(j);
	}
	
	public void afficherPasAsserArgent() {
		System.out.println("Desolé, vous n'avez pas asser d'argent pour acheter ce batiment ...");
	}
	
	public void afficherPasAsserDeBatiment() {
		System.out.println("Desolé, il n'y a plus de batiment de ce type disponible ...");
	}
	
	public void afficherPasDeTerrainConstructible() {
		System.out.println("Desolé, vous n'avez aucun terrain sur lequels vous pouvez construire ...");
	}
	
	public void afficherPatrimoine(Joueur j) {
		System.out.println("\nJoueur : "+j.getNomJoueur()+"  argent : "+j.getCash()+"€");
		System.out.println("Proprietes : ");
		for (Propriete p : j.getProprietes()){
			System.out.print("Numero de carreau : "+p.getNumero());
			System.out.print(" / Nom : "+p.getNomCarreau());
			if (p.getClass() == ProprieteAConstruire.class){
				System.out.print(" / Couleur : "+((ProprieteAConstruire)p).getCouleur().toString());
				System.out.print(" / Nombre de maison : "+((ProprieteAConstruire)p).getNbmaison());
			}
			System.out.println("");
		}
	}
	@Override
    public void notifier(Message msg) {
		Joueur j = controleur.getJoueurCourant();
		switch (msg) {
			case AFFICHER_ACHAT_PROPRIETE:
				this.afficherAchatPropriete((Propriete)j.getPositionCourante());
				break;
			case AFFICHER_DEMANDE_ACHETER_PROPRIETE:
				if (this.afficherDemandeAcheterPropriete((Propriete)j.getPositionCourante())) {
					controleur.getMonopoly().acheterProprieteGenreVraiment(j, (Propriete)j.getPositionCourante(), this);
				}
				break;
			case AFFICHER_ACHAT_BATIMENT:
				this.afficherAchatBatiment(j, (ProprieteAConstruire)j.getPositionCourante());
				break;
			case AFFICHER_PROPRIETE_CONSTRUCTIBLE:
				ArrayList<ProprieteAConstruire> proprieteConstructibles = controleur.getProprieteConstructibles(j);
				int reponse = this.afficherProprieteConstructible(proprieteConstructibles, controleur.getMonopoly().getNbMaisonDisponible(), controleur.getMonopoly().getNbHotelDisponible()); //affiche les batiments constructibles et demande une reponse
				if (reponse != 0){ // si il achete une propriete
						if (proprieteConstructibles.get(reponse-1).getPrixBatiment() <= j.getCash()){ // si il peut acheter le batiment
							if ((proprieteConstructibles.get(reponse-1).getNbmaison() == 4 && controleur.getMonopoly().getNbHotelDisponible() > 0) || (proprieteConstructibles.get(reponse-1).getNbmaison() < 4 && controleur.getMonopoly().getNbMaisonDisponible() > 0) ){ // si il reste des batiments du type qu'il veut construire
								controleur.achatBatiment(j, proprieteConstructibles.get(reponse-1));
							}else{
								this.afficherPasAsserDeBatiment();
							}
						}else{
							this.afficherPasAsserArgent();
						}
					}
				break;
			case AFFICHER_PAYER_LOYER:
				afficherPayerLoyer(j, (Propriete)j.getPositionCourante(), controleur.getMonopoly().getSommeDes());
				break;
			case AFFICHER_PAYER_TAXE:
				afficherPayerTaxe(j, (Taxe)j.getPositionCourante());
				break;
			case AFFICHER_CARTE_CAISSE_DE_COMMUNAUTE:
				afficherCarteCaisseDeCommunaute(controleur.getMonopoly().getPiocheCarteCaisseDeCommunaute().get(controleur.getMonopoly().getPiocheCarteCaisseDeCommunaute().size() - 1));
				break;
			case AFFICHER_CARTE_CHANCE:
				afficherCarteChance(controleur.getMonopoly().getPiocheCarteChance().get(controleur.getMonopoly().getPiocheCarteChance().size() - 1));
				break;
			case AFFICHER_PASSAGE_DEPART:
				affichePassageDepart(((Depart)controleur.getMonopoly().getCarreau(1)).getGainPourPassage());
				break;
			case AFFICHER_CARREAU:
				afficherCarreau(j.getPositionCourante(), controleur.getMonopoly().getDes().get(0), controleur.getMonopoly().getDes().get(1));
				break;
			case AFFICHER_ARGENT_RESTANT:
				afficherArgentRestant(j);
				break;
			case AFFICHER_FIN_PARTIE:
				afficherFinDePartie(j);
				break;
			case AFFICHER_JOUEUR:
				afficherJoueur(j);
				break;
			case AFFICHER_JOUEUR_ELIMINE:
				afficherJoueurElimine(j);
				break;
			case AFFICHER_PATRIMOINE:
				afficherPatrimoine(j);
				break;
			case AFFICHER_3D_DOUBLE:
				afficherJoueur3double(j);
				break;
			case AFFICHER_ATTENDRE_PROCHAIN_TOUR:
				System.err.println("afficherAttndreProchainTour (Changez ce string)");
				break;
			case AFFICHER_MENU:
				int choixMenu = 666;
				boolean fin;
				int nbj;

				while (choixMenu != 2 || controleur.getMonopoly().getJoueurs().size() < 2) {
					if (choixMenu == 1) {
						nbj = controleur.getMonopoly().getJoueurs().size();
						fin = false;
						while(!fin || nbj < 2){
							String nom = this.saisirNom(nbj+1);
							Joueur joueur = new Joueur(nom, CouleurPropriete.rouge, controleur.getMonopoly().getCarreaux().get(1));
						controleur.getMonopoly().addJoueur(joueur);
							nbj++;
							if(nbj >= 2){
								fin = this.finSaisie();
							}
						}

					} else if(choixMenu == 3) {
						controleur.quitterJeu();
						break;
					}
					choixMenu = this.afficherMenu();
				}
				break;
			case AFFICHER_INTERACTION_PRISON:
				int choix = this.afficherInteractionPrison(j);
				if (choix == 1){ // si il tente de faire un double
					controleur.tenteSortiePrisonDouble(j);
				}else{ // si il utilise une carte pour se libere de prison
					controleur.tenterSortiePrisonCarte(j);
				}
				break;
			case AFFICHER_LANCER_DES:
				this.afficherLancerDesDe(controleur.getMonopoly().getDes().get(0), controleur.getMonopoly().getDes().get(1));
				break;
			case AFFICHER_FAIT_UN_DOUBLE:
				this.afficherFaitUnDouble();
				break;
			case AFFICHER_LIBERE_PRISON:
				this.afficherLibereDePrison();
				break;
			case AFFICHER_DERNIER_TOUR_EN_PRISON:
				this.afficherDernierTourEnPrison();
				break;
			case AFFICHER_PAS_ASSEZ_DE_BATIMENTS:
				this.afficherPasAsserDeBatiment();
				break;
			case AFFICHER_PAS_DE_TERRAIN_CONSTRUCTIBLE:
				this.afficherPasDeTerrainConstructible();
				break;
			case AFFICHER_PAS_ASSEZ_DARGENT:
				this.afficherPasAsserArgent();
				break;
				
		}
    }

	public int afficherInteractionPrison(Joueur j) {
		int choix = 0;
		
		while (choix != 1 && choix != 2){
			System.out.println("Vous etes en prison pour encore "+j.getNbTourEnPrison()+" tours !");
			System.out.println("Que voulez-vous faire ? :");
			System.out.println("1 : Tenter de faire un double");
			System.out.println("2 : Utiliser une carte 'Sortir de prison'");
			Scanner sc = new Scanner(System.in);
			try{
				choix = sc.nextInt();
			}catch (Exception e){
				System.out.println("La reponse est invalide !");
				choix = 0;
			}
			
			if (choix == 2 && j.getNbCarteLibereDePrison() == 0){
				System.out.println("Vous n'avez pas de carte pour sortir de prison ...");
				choix = 0;
			}else if (choix == 2 && j.getNbCarteLibereDePrison() > 0){
				System.out.println("Vous utilisez une carte pour vous liberer de prison");
			}
		}
		
		return choix;
	}

	void afficherLibereDePrison() {
		System.out.println("Vous etes liberé de prison");
	}

	void afficherDernierTourEnPrison() {
		System.out.println("C'était votre dernier tour en prison et vous n'avez pas fait de double ...");
		System.out.println("Vous payer une amande de 50€ pour etre libére");
	}
}

