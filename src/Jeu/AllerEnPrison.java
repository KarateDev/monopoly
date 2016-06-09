/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

/**
 *
 * @author mourerj
 */
public class AllerEnPrison extends Carreau {

	public AllerEnPrison(int num, String nom) {
		super(num, nom);
	}

    @Override
    public void action(Joueur j) {
	j.setNbTourEnPrison(0);
    }
	
}
