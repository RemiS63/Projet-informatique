/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Romain
 */
public class BarreDeSante {
    
    private BufferedImage image;
    protected Connection connection;
    protected int nombreDeJoueur;
    
    public BarreDeSante( Connection connection){
        this.connection=connection;
        try {
            image = ImageIO.read(getClass().getResource ("../MAP_DRAGON_images/barre de santé.png"));
        } catch (IOException ex) {
            Logger.getLogger(BarreDeSante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void miseAJour() {
        nombreDeJoueur = 0;
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("SELECT pseudo FROM joueur;");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) { // pour chaque joueur
                nombreDeJoueur = nombreDeJoueur +1;
                String pseudo = resultat.getString("pseudo");
                System.out.println("pseudo = " + pseudo);
            } 
            requete.close();
            }catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("nombreDeJoueur = " + nombreDeJoueur);
        
    }
    
    public void rendu(Graphics2D contexte) {
            if (nombreDeJoueur == 1){
                contexte.drawImage(this.image, 0, 0, null);
            //    contexte.drawString(pseudo, 0, 0);
            }
            if (nombreDeJoueur == 2){
                contexte.drawImage(this.image, 0, 0, null);
            //    contexte.drawString(pseudo, 0, 0);
                contexte.drawImage(this.image, 700, 0, null);
            //    contexte.drawString(pseudo, 700, 0);
            }
            if (nombreDeJoueur == 3){
                contexte.drawImage(this.image, 0, 0, null);
            //    contexte.drawString(pseudo, 0, 0);
                contexte.drawImage(this.image, 700, 0, null);
            //    contexte.drawString(pseudo, 700, 0);
                contexte.drawImage(this.image, 0, 460, null);
            //    contexte.drawString(pseudo, 0, 460);
            }
            if (nombreDeJoueur >= 4){
                contexte.drawImage(this.image, 0, 0, null);
            //    contexte.drawString(pseudo, 0, 0);
                contexte.drawImage(this.image, 700, 0, null);
            //    contexte.drawString(pseudo, 700, 0);
                contexte.drawImage(this.image, 0, 460, null);
            //    contexte.drawString(pseudo, 0, 460);
                contexte.drawImage(this.image, 700, 460, null);
            //    contexte.drawString(pseudo, 700, 460);
            }
    }
}
