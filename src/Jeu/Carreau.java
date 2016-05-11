package Jeu;

public abstract class Carreau {

	private int numero;
	private String nomCarreau;
        
        public Carreau(int num){
            this.numero = num;
        }

	public int getNumero() {
		return this.numero;
	}

}