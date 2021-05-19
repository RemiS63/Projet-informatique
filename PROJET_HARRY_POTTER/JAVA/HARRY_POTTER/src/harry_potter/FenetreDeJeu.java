/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 *
 * @author rollin
 */

public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage buffer;
    private Graphics2D contexteBuffer;
    private Jeu jeu;
    private Timer timer;
    private JLabel jLabel1;
    
public FenetreDeJeu(String pseudo) throws SQLException {    
    // initialisation de la fenetre
        this.setSize(800, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(800, 500));
        this.setContentPane(this.jLabel1);
        this.pack();
        
        // Creation du jeu
        this.jeu = new Jeu(pseudo);

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.buffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(buffer));
        this.contexteBuffer = this.buffer.createGraphics();


        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(30, this);
        this.timer.start();
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }

    // Methode appelee par le timer et qui contient la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        this.jeu.miseAJour();
        try {
            this.jeu.rendu(contexteBuffer);
        } catch (SQLException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jLabel1.repaint();
        if (this.jeu.estTermine()){
            
       
            try {
                Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
                PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur "); 
                 
                //requete.setString(1,"plo");                
                System.out.println(requete);
                requete.executeUpdate();
                requete.close();
                PreparedStatement requete1 = connexion.prepareStatement("DELETE FROM objets"); 
                requete1.executeUpdate();
                requete1.close();
            //connexion.close();
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
            System.out.println("jeu fini");
            this. timer . stop () ;
            
    
        }
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        // NOP
    }
    
    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 38) {
            this.jeu.getJoueur().setHaut(true);
        }
        if (evt.getKeyCode() == 40) {
            this.jeu.getJoueur().setBas(true);
        }
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.getJoueur().setDroite(true);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.getJoueur().setGauche(true);
        }
        if (evt.getKeyCode() == 65) {
            this.jeu.joueur1.SaisirOeuf(this.jeu.oeuf);
        }
        if (evt.getKeyCode() == 32) {
            this.jeu.lancerBombe();            
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        //System.out.println(evt.getKeyCode());        
        if (evt.getKeyCode() == 38) {
            this.jeu.getJoueur().setHaut(false);
        }
        if (evt.getKeyCode() == 40) {
            this.jeu.getJoueur().setBas(false);
        }
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.getJoueur().setDroite(false);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.getJoueur().setGauche(false);
        }
        if (evt.getKeyCode() == 65) {
            this.jeu.getJoueur().LacherOeuf(this.jeu.oeuf);
        }
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {  
        
         try {
                Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
                PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur "); 
                 
                //requete.setString(1,"plo");                
                System.out.println(requete);
                requete.executeUpdate();
                requete.close();
                PreparedStatement requete1 = connexion.prepareStatement("DELETE FROM objets"); 
                requete1.executeUpdate();
                requete1.close();
            //connexion.close();
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
            System.out.println("jeu fini");
            this. timer . stop () ;
            
        System.out.println("Au revoir");
    } 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        String pseudo = JOptionPane.showInputDialog(null, "Entrez votre pseudo");
        if (pseudo != null) {
            FenetreDeJeu fenetre = new FenetreDeJeu(pseudo);
            fenetre.setVisible(true);
        }       

    }
    
}
