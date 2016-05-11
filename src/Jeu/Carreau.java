package Jeu;

public abstract class Carreau {

	private int numero;
	private String nomCarreau;
        
        public Carreau(int num,String nomcarreau){
            this.numero = num;
            this.nomCarreau = nomcarreau;
        }

	public int getNumero() {
		return this.numero;
	}
        
        public String getNomCarreau(){
            return this.nomCarreau;
        }
}