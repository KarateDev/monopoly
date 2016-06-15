package UI;

import Jeu.Carreau;
import Jeu.Taxe;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author bouchval
 */
public class CarreauTaxeIhm extends CarreauIhm{
	
	public CarreauTaxeIhm(Carreau c, int x, int y){
		super(c, x, y);
	}
	
	@Override
	public void paint(Graphics g){
		
		g.setColor(Color.black);
		g.setFont(new Font("", 0, 10)); // taille des textes
		
		g.drawString(((Taxe)getCarreau()).getPrixTaxe()+"€", getPositionCash().x, getPositionCash().y);
		
		super.paint(g);
		
	}
	
}
