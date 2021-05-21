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

public class bombe {
    
    protected double xb, yb;
    protected double xdep, ydep;
    protected double xarr, yarr;
    protected BufferedImage sprite;
    protected Connection connection;
    protected double vitesse;
    protected boolean affichee;
    protected double degats;
    
    
    public bombe(Connection connexion,double xa,double ya,double xdepart,double ydepart,double xarrive,double yarrive, String image){
        try{
            this.sprite = ImageIO.read(getClass().getResource(image));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.xb = Math.round(xa);       //la position du dragon selon l'axe x
        this.yb = Math.round(ya);      //la position du dragon selon laxe y
        this.xdep= Math.round(xdepart);     // initialiser les coord de départ et d'arrivée avec les coord dragon/joueur
        this.ydep= Math.round(ydepart);
        this.xarr= Math.round(xarrive);
        this.yarr= Math.round(yarrive);
        this.affichee=true;      
        this.connection=connexion;
        this.vitesse=20;
        this.degats=10;
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO arme VALUES (0,?,?,?,?,?,?,?,?,?,?)");
            requete.setString(1, "sort joueur");
            requete.setDouble(2, this.degats);
            requete.setString(3, image);
            requete.setDouble(4, xb);
            requete.setDouble(5, yb);
            requete.setDouble(6, xdep); 
            requete.setDouble(7, ydep);
            requete.setDouble(8, xarr);
            requete.setDouble(9, yarr);
            requete.setBoolean(10, affichee);
            requete.executeUpdate();
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void miseAJour () {
        //System.out.println("vdgvcgvc");
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("UPDATE arme SET x=FLOOR(x+(xarrive-xdepart)*1000/((xarrive-xdepart)*(xarrive-xdepart)+(yarrive-ydepart)*(yarrive-ydepart))), y=FLOOR(y+(yarrive-ydepart)*1000/((xarrive-xdepart)*(xarrive-xdepart)+(yarrive-ydepart)*(yarrive-ydepart))) WHERE ((xarrive-xdepart)*(xarrive-xdepart)+(yarrive-ydepart)*(yarrive-ydepart))!=0");
            requete.executeUpdate();
            requete.close();
            PreparedStatement requete1 = this.connection.prepareStatement("UPDATE arme SET affiche=0 WHERE (SIGN(xarrive-xdepart)=SIGN(FLOOR(x)-xarrive) && SIGN(yarrive-ydepart)=SIGN(FLOOR(y)-yarrive))");
            requete1.executeUpdate();
            requete1.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }/*
        double xjd=this.xarr-this.xdep; //coordonne x entre le point de depart et l'arrivee
        double yjd=this.yarr-this.ydep; //coordonne y entre le poinyt de depart et l'arrivee
        double d=Math.sqrt(Math.pow(xjd,2) + Math.pow(yjd,2));
        if(d!=0){
            this.xb=xb+xjd*this.vitesse/d;
            this.yb=yb+yjd*this.vitesse/d;
            if (Math.signum(xjd)==Math.signum(xb-xarr) && Math.signum(yjd)==Math.signum(yb-yarr)){
                this.affichee=false;
            }
        } */       
    }
    public void rendu ( Graphics2D contexte ) {
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = this.connection.prepareStatement("SELECT x, y, avatar FROM arme WHERE affiche=1;");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                double x = resultat.getDouble("x");
                double y = resultat.getDouble("y");
                String avatar = resultat.getString("avatar");
                try {
                    BufferedImage sprite = ImageIO.read(getClass().getResource(avatar));
                    contexte.drawImage (sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
                } catch ( IOException ex ) {
                    Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
                }                
            }
            requete.close();
            //connexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
    public double getX () {
        return xb;
    }
    public double getY () {
        return yb;
    }
    public double getLargeur () {
        return sprite . getHeight () ;
    }
    public double getHauteur () {
        return sprite . getWidth () ;
    }

    
    
    
    
    
    
    
    
}
