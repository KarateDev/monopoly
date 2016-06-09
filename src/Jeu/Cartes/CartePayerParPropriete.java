/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.Cartes;

import Jeu.Joueur;
import Jeu.Monopoly;
import Jeu.ParcPublic;
import Jeu.Propriete;
import Jeu.ProprieteAConstruire;
import static UI.Message.*;
import UI.Observateur;

/**
 *
 * @author mourerj
 */
public class CartePayerParPropriete extends Carte {
	private int montant;

	public CartePayerParPropriete(String intitule, int montant) {
		super(intitule);
		this.montant = montant;
	}
	
	@Override
public void action(Joueur j, Observateur observateur, Monopoly monopoly) {
		ParcPublic parc = (ParcPublic) monopoly.getParcPublic();
		for (Propriete p : j.getProprietes()){
			if (p.getClass() == ProprieteAConstruire.class){
				if(((ProprieteAConstruire) p).getNbmaison() < 5){
					j.payerCash(-this.montant * ((ProprieteAConstruire) p).getNbmaison());
					parc.encaisser(this.montant * ((ProprieteAConstruire) p).getNbmaison());
				}else{
					j.payerCash(-Integer.valueOf(this.montant));
					parc.encaisser(Integer.valueOf(this.montant));
				}
			}
		}
		observateur.notifier(AFFICHER_ARGENT_RESTANT);
	}
}
