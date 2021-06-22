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
    protected String pseudo;
    protected int id;
    
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
            while (resultat.next()){ // pour chaque joueur
                nombreDeJoueur = nombreDeJoueur + 1;
            } 
            requete.close();
            }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void rendu(Graphics2D contexte) {
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connection.prepareStatement("SELECT pseudo, id, pv FROM joueur;");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) { // pour chaque joueur
                id = resultat.getInt("id");
                pseudo = resultat.getString("pseudo");
                int pv =resultat.getInt("pv");
                if(id == 0){
                    contexte.drawImage(this.image, 0, 0, null);
                    contexte.drawString(pseudo + "  " + Integer.toString(pv) + "pv", 10, 20);
                }
                if(id == 1){
                    contexte.drawImage(this.image, 700, 0, null);
                    contexte.drawString(pseudo + "  " + Integer.toString(pv) + "pv", 710, 25);
                }
                if(id == 2){
                    contexte.drawImage(this.image, 0, 466, null);
                    contexte.drawString(pseudo + "  " + Integer.toString(pv) + "pv", 15, 490);
                }
                if(id == 3){
                    contexte.drawImage(this.image, 700, 466, null);
                    contexte.drawString(pseudo + "  " + Integer.toString(pv) + "pv", 710, 490);
                }
            }
            requete.close();
            //connexion.close();
            }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
