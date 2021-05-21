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
    
    public Feu(Connection connexion){
        try{
            this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/boule de feu.png"));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.xb = 0;       //la position du dragon selon l'axe x
        this.yb = 0;      //la position du dragon selon laxe y
        this.xdep= 0;     // initialiser les coord de départ et d'arrivée avec les coord dragon/joueur
        this.ydep= 0;
        this.xarr= 0;
        this.yarr= 0;
        this.affichee=false;      
        this.connection=connexion;
        this.vitesse=25;
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
        if(this.affichee==true){
            contexte.drawImage (this. sprite , (int) xb , (int) yb , null);
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
