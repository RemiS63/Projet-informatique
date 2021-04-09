/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Joueur {

    protected BufferedImage sprite ;
    protected double x , y;
    protected boolean gauche , droite , haut , bas ;
    protected double vitesse;
    protected String pseudo;
    protected Connection connection;
    
    public  Joueur(String pseudo,Connection connexion){
        
         try {
         this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/testbleu20_20.png"));
         } catch ( IOException ex ) {
             Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
         }
         
         this.x = 20;
         this.y = 40;
         this.gauche = false;
         this.droite = false;
         this.haut = false;
         this.bas = false;
         this.vitesse = 5;
         this.pseudo=pseudo;
         this.connection=connexion;
         try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueur VALUES (?,?,?,?,?,?,?)");
            requete.setString(1, pseudo);
            requete.setDouble(2, x);
            requete.setDouble(3, y);
            requete.setString(4, "../MAP_DRAGON_images/testbleu20_20.png");
            requete.setDouble(5, vitesse);
            requete.setDouble(6, 100); 
            requete.setDouble(7, 2);
            System.out.println(requete);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void miseAJour () {
        double vitessediag=vitesse/Math.sqrt(2);
        if (this.gauche ) {
            if (this.haut ) {
                y -= vitessediag;
                x -= vitessediag;
            }
            if (this.bas ) {
                y += vitessediag;
                x -= vitessediag;
            } 
            else{
                x -= vitesse;
            }
        }else if (this.droite ) {
            if (this.haut ) {
                y -= vitessediag;
                x += vitessediag;
            }
            if (this.bas ) {
                y += vitessediag;
                x += vitessediag;
            }
            else{
                x += vitesse;
            }            
        }else if (this.haut ) {
            y -= vitesse;
        }
        else if (this.bas ) {
            y += vitesse;
        }         
        if (x > 780 - sprite.getWidth() ) { // collision avec le bord droit de la scene
            x = 780 - sprite.getWidth() ;
        }
        if (x < 20) { // collision avec le bord gauche de la scene
            x = 20;
        }
        if (y > 460 - sprite.getWidth()) { // collision avec le bord droit de la scene
            y = 460 - sprite.getWidth() ;
        }
        if (y < 40) { // collision avec le bord gauche de la scene
            y = 40;
        } 
        try {

            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");

            PreparedStatement requete = this.connection.prepareStatement("UPDATE joueur SET x = ?, y = ? WHERE pseudo = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setString(3, this.pseudo);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void SaisirOeuf(Oeuf oeuf){
        double xo = oeuf.x;
        double yo = oeuf.y;        
        double r = 10;
        double djo = Math.sqrt(Math.pow(x-xo,2) + Math.pow(y-yo,2));
        if(djo<r) { 
            oeuf.saisi=true;
            vitesse = 2;
        } 
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");

            PreparedStatement requete = this.connection.prepareStatement("UPDATE joueur SET Vitesse = ? WHERE pseudo = ?");
            requete.setDouble(1, vitesse);
            requete.setString(2, this.pseudo);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void LacherOeuf(Oeuf oeuf){
        oeuf.saisi=false;
        vitesse = 5; 
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("UPDATE joueur SET Vitesse = ? WHERE pseudo = ?");
            requete.setDouble(1, vitesse);
            requete.setString(2, this.pseudo);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
        public void rendu ( Graphics2D contexte ) {
            contexte.drawImage(this.sprite , (int) x , (int) y , null);
        }
        
        public void setGauche (boolean gauche ) {
            this.gauche = gauche ;
        }
        
        public void setDroite(boolean droite ) {
            this.droite = droite ;
        }
        
         public void setHaut (boolean haut ) {
            this.haut = haut ;
        }
        
        public void setBas (boolean bas ) {
            this.bas = bas ;
        }
        
        public double getX () {
            return x;
        }
        
        public double getY () {
            return y;
        }
        
        public double getLargeur () {
            return sprite.getHeight () ;
        }
        
        public double getHauteur () {
            return sprite.getWidth () ;
        }
}

    
    

