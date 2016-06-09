package Jeu;

public class Depart extends Carreau {
    
    private int gainPourPassage;
    
    public Depart(int gainpourpassage){
            super(1,"Depart");
            this.gainPourPassage = gainpourpassage;
        }
    
    public int getGainPourPassage(){
        return this.gainPourPassage;
    }

    @Override
    public void action(Joueur j) {
	j.recevoirCash(gainPourPassage);
    }
    
    
}