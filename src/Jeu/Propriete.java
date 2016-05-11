package Jeu;

public abstract class Propriete extends Carreau {

	Joueur proprietaire;

	/**
	 * 
	 * @param j
	 */
	public void setProprietaire(Joueur j) {
		this.proprietaire = j;
	}

	public abstract int calculLoyer();

	public Joueur getProprietaire() {
		return this.proprietaire;
	}

	/**
	 * 
	 * @param j
	 */
	public void action(Joueur j) {
		// TODO - implement Propriete.action
		throw new UnsupportedOperationException();
	}

	public int getPrix() {
		// TODO - implement Propriete.getPrix
		throw new UnsupportedOperationException();
	}

	public Propriete getPropriété() {
		// TODO - implement Propriete.getPropriété
		throw new UnsupportedOperationException();
	}

	public void addProprietaire() {
		// TODO - implement Propriete.addProprietaire
		throw new UnsupportedOperationException();
	}

}