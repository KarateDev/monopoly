package Jeu;

public abstract class Carreau {

	private int numero;
	private String nomCarreau;
        
        public Carreau(int num,String nomcarreau){
            this.numero = num;
            this.nomCarreau = nomcarreau;
        }

    public String getNomCarreau() {
	return nomCarreau;
    }

    private void setNomCarreau(String nomCarreau) {
	this.nomCarreau = nomCarreau;
    }

    public int getNumero() {
        return this.numero;
    }
    
    public abstract void action(Joueur j);
        
}
