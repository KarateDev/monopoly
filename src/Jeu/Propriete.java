package Jeu;

public abstract class Propriete extends Carreau {

	private Joueur proprietaire;
        private int prixachat;
        
        public Propriete(int numcase,String nomcarreau,int prixachat){
            super(numcase,nomcarreau);
            setProprietaire(null);
            setPrixachat(prixachat);
        }
        
        private void setPrixachat(int prixachat){
            this.prixachat = prixachat;
        }

	/**
	 * 
	 * @param j
	 */
	public void setProprietaire(Joueur j) {
		this.proprietaire = j;
	}

	public abstract int calculLoyer(int valeurdes);

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
		return this.prixachat;
	}


}