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
 * @author User
 */
public class Oeuf {
    protected BufferedImage sprite ;
    protected double x , y;
    protected boolean saisi;
    protected Connection connection;
    protected String pseudo_joueur;
    
    
    public Oeuf(Connection connexion){
        try {
         this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/oeuf.png"));
         } catch ( IOException ex ) {
             Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
         }
        this.x = 400;       //milieu de map d'axe x
        this.y = 250;
        this.saisi=false;
        this.connection=connexion;
        this.pseudo_joueur="";
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO objets VALUES (?,?,?,?,?,?,?)");
            requete.setInt(1, 1);
            requete.setDouble(2, x);
            requete.setDouble(3, y);
            requete.setString(4, "../MAP_DRAGON_images/oeuf.png");
            requete.setString(5, "oeuf");                    
            requete.setString(6, pseudo_joueur);             
            requete.setBoolean(7, saisi);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void miseAJour () {        
        if (this.saisi ){
            try {
                //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
                PreparedStatement requete = this.connection.prepareStatement("SELECT x,y FROM joueur WHERE pseudo=?;");
                requete.setString(1, this.pseudo_joueur);
                ResultSet resultat = requete.executeQuery();
                while (resultat.next()) {
                    this.x = resultat.getDouble("x");
                    this.y = resultat.getDouble("y");
                }
                requete.close();
                //connexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else{
            try {
                //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
                PreparedStatement requete = this.connection.prepareStatement("SELECT x,y,pseudo_joueur FROM objets WHERE id=1;");
                ResultSet resultat = requete.executeQuery();
                while (resultat.next()) {
                    this.x = resultat.getDouble("x");
                    this.y = resultat.getDouble("y");
                    //boolean saisij=resultat.getBoolean("saisi");
                    this.pseudo_joueur=resultat.getString("pseudo_joueur");
                    //System.out.println(""+x+"   "+y);
                }
                requete.close();
                //connexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("UPDATE objets SET x = ?, y = ?, pseudo_joueur=? WHERE id = 1");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setString(3, this.pseudo_joueur);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
    }

    public void rendu ( Graphics2D contexte ) {
            contexte.drawImage (this.sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
    }
    
    public double getX () {
            return x;
        }
        
    public double getY () {
        return y;
    }
    
    public boolean seTrouveEnCoordonnéesIntiales(){
        if ((30<= this.x  && this.x <=110) && (45<=this.y && this.y<=95)){
            return true;
            
        } else { 
            
            if ((691<= this.x  && this.x <=771) && (45<=this.y && this.y<=95)){
                return true;
                
            } else {
               
                if ((30<= this.x  && this.x <=110) && (413<=this.y && this.y<=453)){
                    return true;
                    
                } else {
                     if ((691<= this.x  && this.x <=771) && (413<=this.y && this.y<=453)){
                         return true;
                         
                     } else {
                         return false;
                     }
                }
            }
        }
        
    }
}
      
