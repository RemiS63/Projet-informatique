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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Dragon {
    
    protected double x, y;
    protected BufferedImage sprite;
    protected Connection connection;
    
    
    public Dragon(Connection connexion){
        try{
            this.sprite = ImageIO.read(getClass().getResource ("../MAP_DRAGON_images/Vouivre1.png"));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 400;       //milieu de map d'axe x
        this.y = 250;       //milieu de map d'axe y
        this.connection=connexion;
    }
    
    public void miseAJour () {  
        int aLoeuf =0;
        double r = 200;     //radius de cercle
        double ro = 100;    //radius d'oeuf
        double djd = 0;     //distance entre joueur dragon
        double xjd = 0;     //coordonne x entre joueur dragon
        double yjd = 0;     //coordonne y entre joueur dragon
        double dxjd = 0;    //distance x joueur dragon entre image
        double dyjd = 0;    //distance y joueur dragon entre image
        BufferedImage spritej = null;
        double taille_joueur=40;
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("SELECT x, y, possedeLoeuf, avatar FROM joueur;");
            ResultSet resultat = requete.executeQuery();            
            while (resultat.next() && aLoeuf==0) { // pour chaque joueur
                double xj = resultat.getDouble("x");        //avoir le coordonne x de joueur
                double yj = resultat.getDouble("y");        //avoir le coordonne y de joueur
                String avatar = resultat.getString("avatar");
                double rj = Math.sqrt(Math.pow(xj-400,2) + Math.pow(yj-250,2));     //radius de joueur
                aLoeuf = resultat.getInt("possedeLoeuf");
                if (aLoeuf==1) {
                    try{
                        spritej = ImageIO.read(getClass().getResource (avatar));
                    }
                    catch(IOException ex){
                        Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    double xjdn = this.x-xj;    //coordonne x dragon - joueur
                    double yjdn = this.y-yj;    //coordonne y dragon - joueur
                    double dxjdn = Math.abs(xjdn)-sprite.getWidth()/2-spritej.getWidth()/2;
                    double dyjdn = Math.abs(yjdn)-sprite.getHeight()/2-spritej.getHeight()/2;
                    double djdn = Math.sqrt(Math.pow(xjdn,2) + Math.pow(yjdn,2)); //distance entre joueur dragon                    
                    xjd=xjdn;
                    yjd=yjdn;   //plus proche joueur
                    djd=djdn; 
                    dxjd=dxjdn;
                    dyjd=dyjdn;
                    
                }
                else if(rj<r) { //si le joueur est dans le cercle
                    try{
                        spritej = ImageIO.read(getClass().getResource (avatar));
                    }
                    catch(IOException ex){
                        Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
                    double xjdn = this.x-xj;    //coordonne x dragon - joueur
                    double yjdn = this.y-yj;    //coordonne y dragon - joueur
                    double dxjdn = Math.abs(xjdn)-sprite.getWidth()/2-spritej.getWidth()/2;
                    double dyjdn = Math.abs(yjdn)-sprite.getHeight()/2-spritej.getHeight()/2;
                    double djdn = Math.sqrt(Math.pow(xjdn,2) + Math.pow(yjdn,2)); //distance entre joueur dragon
                    if (djd == 0) { //pour le premier joueur dans le cercle
                        xjd=xjdn;
                        yjd=yjdn;   //plus proche joueur
                        djd=djdn; 
                        dxjd=dxjdn;
                        dyjd=dyjdn;
                    }   
                    
                    
                   if (djdn<djd){ //si ce joueur est plus proche que les autre
                       xjd=xjdn;
                        yjd=yjdn;
                        djd=djdn;  
                       dxjd=dxjdn;
                      dyjd=dyjdn;
                   }
                   if(aLoeuf==1){
                       
                       
                   }
//                   if(rj<ro){
//                       xjd=xjdn;
//                        yjd=yjdn;       
//                        djd=djdn;
//                       dxjd=dxjdn;
//                        dyjd=dyjdn;
//                   }
                }
            }
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }             
        if(djd==0){
            xjd=this.x-400;
            yjd=this.y-250;
            djd=Math.sqrt(Math.pow(xjd,2) + Math.pow(yjd,2));
            if(djd>3){
                this.x=x-xjd*3/djd; //dragon bouge 3 pixel
                this.y=y-yjd*3/djd;
            }            
        }
        if (dxjd>0){
            this.x=x-xjd*3/djd; //dragon bouge 3 pixel            
        } 
        if (dyjd>0){
            this.y=y-yjd*3/djd;
        }
        if (x > 800) { // collision avec le bord droit de la scene
            x = 800 ;
            System.out.println("dragon trop droite");
        }
        if (x < sprite.getWidth()/2) { // collision avec le bord gauche de la scene
            x = (int) sprite.getWidth()/2;
            System.out.println("dragon trop gauche");
        }
        if (y > 500 ) { // collision avec le bord bas de la scene
            y = 500 ;
            System.out.println("dragon trop bas");
        }
        if (y < sprite.getHeight()/2) { // collision avec le bord haut de la scene
            y = (int) sprite.getHeight()/2;
            System.out.println("dragon trop haut");
        }
    }
    
    public void rendu ( Graphics2D contexte ) {
        contexte . drawImage (this. sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
    }
    
    public void demarrer() {
        this.x = 400;
        this.y = 250;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getLargeur(){
        return sprite.getHeight();
    }
    
    public double getHauteur(){
        return sprite.getWidth();
    }

}