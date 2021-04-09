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
            this.sprite = ImageIO.read(getClass().getResource ("../MAP_DRAGON_images/testrouge20_20.png"));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 400;       //milieu de map d'axe x
        this.y = 250;       //milieu de map d'axe y
        this.connection=connexion;
    }
    
    public void miseAJour (Joueur joueur1) {  
        double r = 200;     //radius de cercle
        double ro = 100;    //radius d'oeuf
        double djd = 0;     //distance entre joueur dragon
        double xjd = 0;     //coordonne x entre joueur dragon
        double yjd = 0;     //coordonne y entre joueur dragon
        BufferedImage spritej;
        double taille_joueur=20;
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("SELECT x, y, avatar FROM joueur;");
            ResultSet resultat = requete.executeQuery();            
            while (resultat.next()) { // pour chaque joueur
                double xj = resultat.getDouble("x");        //avoir le coordonne x de joueur
                double yj = resultat.getDouble("y");        //avoir le coordonne y de joueur
                String avatar = resultat.getString("avatar");
                double rj = Math.sqrt(Math.pow(xj-400,2) + Math.pow(yj-250,2));     //radius de joueur
                if(rj<r) { //si le joueur est dans le cercle
                    double xjdn = this.x-xj;    //coordonne x dragon - joueur
                    double yjdn = this.y-yj;    //coordonne y dragon - joueur
                    double djdn = Math.sqrt(Math.pow(xjdn,2) + Math.pow(yjdn,2)); //distance entre joueur dragon
                    if (djd == 0) { //pour le premier joueur dans le cercle
                        xjd=xjdn;
                        yjd=yjdn;   //plus proche joueur
                        djd=djdn;
                        try{
                            spritej = ImageIO.read(getClass().getResource (avatar));
                            taille_joueur=Math.max(spritej.getWidth(), joueur1.sprite.getHeight());
                        }
                        catch(IOException ex){
                            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                    }       
                    if (djdn<djd){ //si ce joueur est plus proche que les autre
                        xjd=xjdn;
                        yjd=yjdn;
                        djd=djdn;
                        try{
                            spritej = ImageIO.read(getClass().getResource (avatar));
                            taille_joueur=Math.max(spritej.getWidth(), joueur1.sprite.getHeight());
                        }
                        catch(IOException ex){
                            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                    }
                   
                }
                
            }
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        double taille_dragon=Math.max(this.sprite.getWidth(), this.sprite.getHeight());     
        if(djd==0){
            xjd=this.x-400;
            yjd=this.y-250;
            djd=Math.sqrt(Math.pow(xjd,2) + Math.pow(yjd,2));
            if(djd>3){
                this.x=x-xjd*3/djd; //dragon bouge 3 pixel
                this.y=y-yjd*3/djd;
            }            
        }                  
        else if (djd>10+(taille_dragon+taille_joueur)/2){
            this.x=x-xjd*3/djd; //dragon bouge 3 pixel
            this.y=y-yjd*3/djd;
        }                  
        if (x > 800 - sprite.getWidth() ) { // collision avec le bord droit de la scene
            x = 800 - sprite.getWidth() ;
        }
        if (x < 0) { // collision avec le bord gauche de la scene
            x = 0;
        }
        if (y > 500 - sprite.getHeight()) { // collision avec le bord droit de la scene
            y = 500 - sprite.getHeight() ;
        }
        if (y < 0) { // collision avec le bord gauche de la scene
            y = 0;
        }
    }
    
    public void rendu ( Graphics2D contexte ) {
        contexte . drawImage (this. sprite , (int) x , (int) y , null);
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


/*
if(rj<ro){  //si ce joueur est dans la zone d'oeuf
    xjd=xjdn;
    yjd=yjdn;
    djd=djdn;
    try{
        spritej = ImageIO.read(getClass().getResource (avatar));
        taille_joueur=Math.max(spritej.getWidth(), joueur1.sprite.getHeight());
    }
    catch(IOException ex){
        Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
    }      
}


*/
