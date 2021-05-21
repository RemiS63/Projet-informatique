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
/**
 *
 * @author aelmoudn
 */
public class Feu {
    protected double xb, yb;
    protected double xdep, ydep;
    protected double xarr, yarr;
    protected BufferedImage sprite;
    protected Connection connection;
    protected double vitesse;
    protected boolean affichee;
    
    public Feu(double xb,double yb,double xdep,double ydep,double xarr,double yarr,Connection connexion,boolean affiche){
        try{
            this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/boule de feu.png"));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.xb = Math.round(xb);       //la position du dragon selon l'axe x
        this.yb = Math.round(yb);      //la position du dragon selon laxe y
        this.xdep= Math.round(xdep);     // initialiser les coord de départ et d'arrivée avec les coord dragon/joueur
        this.ydep= Math.round(ydep);
        this.xarr= Math.round(xarr);
        this.yarr= Math.round(yarr);
        this.affichee=affiche;      
        this.connection=connexion;
        this.vitesse=20;
        //this.degats=10;
        try {
            //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
            PreparedStatement requete = connexion.prepareStatement("INSERT INTO arme VALUES (0,?,?,?,?,?,?,?,?,?,?)");
            requete.setString(1, "feu dragon");
            requete.setDouble(2, 10);
            requete.setString(3, "../MAP_DRAGON_images/boule de feu.png");
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
        double xjd=this.xarr-this.xdep; //coordonne x entre le point de depart et l'arrivee
        double yjd=this.yarr-this.ydep; //coordonne y entre le poinyt de depart et l'arrivee
        double d=Math.sqrt(Math.pow(xjd,2) + Math.pow(yjd,2));
        if(d!=0){
            this.xb=xb+xjd*this.vitesse/d;
            this.yb=yb+yjd*this.vitesse/d;
            if (Math.signum(xjd)==Math.signum(xb-xarr) && Math.signum(yjd)==Math.signum(yb-yarr)){
                this.affichee=false;
            }
        }
    }
   
    public void rendu ( Graphics2D contexte ) {
        /*if(this.affichee==true){
            contexte.drawImage (this. sprite , (int) xb , (int) yb , null);
        }
        */
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
