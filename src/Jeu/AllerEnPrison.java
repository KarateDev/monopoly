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

	// attention !!! dans aller en prison et prison , les nbtours peuvent aller de 0 a 3 ou de 3 a 0 
    @Override
    public void action(Joueur j) {
	j.setNbTourEnPrison(3);
    }
	
}
