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
import java.sql.ResultSet;
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
    protected int possedeLoeuf;
    private bombe bombe;
    protected int health;
    protected int ordreDeJoueur;
    
    public  Joueur(String pseudo,Connection connexion){        
        ordreDeJoueur = 0;
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connexion.prepareStatement("SELECT pseudo FROM joueur;");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) { // pour chaque joueur
                ordreDeJoueur = ordreDeJoueur +1;   
            }
            requete.close();
            //connexion.close();
            }catch (SQLException ex) {
            ex.printStackTrace();
        }
         try {
         this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/harry potter1.png"));
         } catch ( IOException ex ) {
             Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
         }
         if(ordreDeJoueur==0){
            this.x = 30;
            this.y = 45;
        }
        if(ordreDeJoueur==1){
            this.x = 780;
            this.y = 45;
        }
        if(ordreDeJoueur==2){
            this.x = 30;
            this.y = 440;
        }
        if(ordreDeJoueur==3){
            this.x = 780;
            this.y = 440;
        }
         this.gauche = false;
         this.droite = false;
         this.haut = false;
         this.bas = false;
         this.vitesse = 5;
         this.pseudo=pseudo;
         this.possedeLoeuf=0;
         this.health=100;
         this.connection=connexion;
         
         try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueur VALUES (?,?,?,?,?,?,?,?)");
            requete.setString(1, pseudo);
            requete.setDouble(2, x);
            requete.setDouble(3, y);
            requete.setString(4, "../MAP_DRAGON_images/harry potter1.png");
            requete.setDouble(5, vitesse);
            requete.setDouble(6, this.health); 
            requete.setDouble(7, 2);
            requete.setInt(8, possedeLoeuf);
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
        if (x > 790 - sprite.getWidth() ) { // collision avec le bord droit de la scene
            x = 790 - sprite.getWidth() ;
        }
        if (x < 30) { // collision avec le bord gauche de la scene
            x = 30;
        }
        if (y > 480 - sprite.getHeight()) { // collision avec le bord droit de la scene
            y = 480 - sprite.getHeight() ;
        }
        if (y < 45) { // collision avec le bord gauche de la scene
            y = 45;
        } 
        if (this.health<=0){//le joueur n'a plus de point de vie
            this.x=30;
            this.y=45;
        }
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("UPDATE joueur SET x = ?, y = ?, possedeLoeuf = ?, pv = ? WHERE pseudo = ?");
            requete.setDouble(1, x);
            requete.setDouble(2, y);
            requete.setDouble(3, possedeLoeuf);
            requete.setDouble(4, health);
            requete.setString(5, this.pseudo);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void subirDegats(int degats){
        this.health -=degats;
    }
    public void SaisirOeuf(Oeuf oeuf){
        this.possedeLoeuf=1;
        double xo = oeuf.x;
        double yo = oeuf.y;        
        double r = 10;
        double djo = Math.sqrt(Math.pow(x-xo,2) + Math.pow(y-yo,2));
        if(djo<r) { 
            oeuf.saisi=true;
            oeuf.pseudo_joueur=this.pseudo;
            vitesse = 2;
        } 
        
    }
    public void LacherOeuf(Oeuf oeuf){
        oeuf.saisi=false;
        vitesse = 5;
        this.possedeLoeuf=0;
    }
    
        public void rendu ( Graphics2D contexte ) {
            contexte.drawImage (this.sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
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
        public int getPossedeLoeuf() {
            return possedeLoeuf;
        }
}

    
    

