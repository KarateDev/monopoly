/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;
import Jeu.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author bouchval
 */
public class AchatBatimentIhm extends JPanel{
	
	private Controleur controleur;
	
	private JTable tablePropriete;
	
	private JLabel labelNom;
	private JLabel labelNom2;
	private JLabel labelArgent;
	
	private JButton boutonQuitter;
	private JButton boutonAcheter;
	
	public AchatBatimentIhm(Controleur controleur, Joueur joueur, ArrayList<Propriete> proprietes){

		this.controleur = controleur;
		this.setLayout(new BorderLayout());
		initComponent();
		ajouterListner();
		initialisationDonnees(joueur, proprietes);
	}
	
	private void initComponent(){
		
		JPanel panelHaut = new JPanel(new GridLayout(2,1));
		panelHaut.add(new JLabel("Joueur actuel : "));
			JPanel panelInfoJoueur = new JPanel();
				panelInfoJoueur.setBorder(new LineBorder(Color.black));
				labelNom = new JLabel("Nom :");
				panelInfoJoueur.add(labelNom);

				labelNom2 = new JLabel();
				panelInfoJoueur.add(labelNom2);

				labelArgent = new JLabel();
				panelInfoJoueur.add(labelArgent);

				panelHaut.add(panelInfoJoueur);
				
			this.add(panelHaut,BorderLayout.NORTH);
			
		JPanel panelCentre = new JPanel(new BorderLayout());
			JLabel labelListePropriete = new JLabel("Propriétes du joueur :");
					labelListePropriete.setHorizontalAlignment(JTextField.CENTER);
					panelCentre.add(labelListePropriete,BorderLayout.NORTH);
                
					JPanel panelTableau = new JPanel(new BorderLayout());
					tablePropriete = new JTable();
					panelTableau.add(tablePropriete.getTableHeader(),BorderLayout.NORTH);
					panelTableau.add(new JScrollPane(tablePropriete),BorderLayout.CENTER);
					tablePropriete.setEnabled(true);
					tablePropriete.setColumnSelectionAllowed(false);
					tablePropriete.getTableHeader().setReorderingAllowed(false);
					panelCentre.add(panelTableau,BorderLayout.CENTER);
					
					JPanel panelBas = new JPanel(new GridLayout(1, 4));
						panelBas.add(new JPanel());

						boutonQuitter = new JButton("Quitter");
						panelBas.add(boutonQuitter);

						boutonAcheter = new JButton("Acheter");
						panelBas.add(boutonAcheter);

						panelBas.add(new JPanel());
						
						panelCentre.add(panelBas, BorderLayout.SOUTH);
		
			this.add(panelCentre, BorderLayout.CENTER);
			
	}

	private void ajouterListner() {
		
		boutonQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// --------------------------------------------------------------------- action controleur a faire (notifier un message quitterAchatBatiment)
			}
		});
		
		boutonAcheter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int numeroLigne = tablePropriete.getSelectedRow();
				
				String nomPropriete = tablePropriete.getValueAt(numeroLigne, 0).toString();
				
				// ------------------------------------------------------------------------- action controleur a faire (acheter une maison sur la propriete de nom nomPropriete)
				
			}
		});
		
	}

	public void initialisationDonnees(Joueur joueur, ArrayList<Propriete> proprietes) {
		
		labelNom2.setText(joueur.getNomJoueur());
		labelNom2.setForeground(joueur.getColorJoueurIhm());
		
		labelArgent.setText("Argent : "+joueur.getCash()+"€");
		
		DefaultTableModel model = new DefaultTableModel(){ 
                @Override
                public boolean isCellEditable(int row, int column){ //pour rendre les cellules non modifiable
                return false;
            }};
        String [] colonnes = {"Nom","Loyer","Batiment","Couleur","Prix batiment"};
        model.setColumnIdentifiers(colonnes);
            
        for (Propriete p : proprietes){
		String [] donnees = {p.getNomCarreau(),
			String.valueOf(p.calculLoyer(0)),
			((ProprieteAConstruire) p).getCouleur().toString(),
			((ProprieteAConstruire) p).getNbmaison()+" maisons",
			((ProprieteAConstruire) p).getPrixBatiment()+"€"};

			
            model.addRow(donnees);
        }
        tablePropriete.setModel(model);
	}
	
	public void afficherAchatBatiment(Joueur joueur, int prix){
		JOptionPane.showConfirmDialog(null, 
			"Achat", 
			"Vous avez achater un batiment pour "+prix+"€",
			JOptionPane.DEFAULT_OPTION, 
			JOptionPane.INFORMATION_MESSAGE);
		labelArgent.setText("Argent : "+joueur.getCash()+"€");
	}
	
	
	
}
