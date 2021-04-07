/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

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
    
public FenetreDeJeu() throws SQLException {    
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
        this.jeu = new Jeu();

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.buffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(buffer));
        this.contexteBuffer = this.buffer.createGraphics();


        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(30, this);
        this.timer.start();
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
            this.jeu.getJoueur().SaisirOeuf(this.jeu.oeuf);
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
        //fenetre.jeu.connection.close();
    }
    
}
