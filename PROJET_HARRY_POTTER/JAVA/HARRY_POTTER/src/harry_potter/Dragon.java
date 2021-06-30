/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.awt.Color;
import java.awt.Font;
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
    protected Font fonte;
    protected int health;
    protected Feu feu;
    
    
    public Dragon(Connection connexion){
        try{
            this.sprite = ImageIO.read(getClass().getResource ("../MAP_DRAGON_images/Vouivre1.png"));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 400;       //milieu de map d'axe x
        this.y = 250;       //milieu de map d'axe y
        this.health = 100;
        this.connection=connexion;
        this.fonte = new Font("TimesRoman ",Font.BOLD,18);
        this.feu = new Feu(0,0,0,0,0,0,connection,false);
    }
    
    public void miseAJour() {
        int aLoeuf = 0;     // entier permettant de savoir si un joueur a l'oeuf
        double r = 200;     //rayon du cercle de détection
        double ro = 100;    //rayon d'oeuf
        double djd = 0;     //vecteur entre le joueur et le dragon
        double xjd = 0;     //vecteur selon x entre le joueur et le dragon
        double yjd = 0;     //vecteur selon y entre le joueur et le dragon
        double dxjd = 0;    //distance selon x entre les images du joueur et du dragon
        double dyjd = 0;    //distance selon y entre les images du joueur et du dragon
        double xjoueur = 0; //coordonnée x du joueur cible
        double yjoueur = 0; //coordonnée y du joueur cible
        BufferedImage spritej = null;   //image du joueur
        try {                   
            PreparedStatement requete = this.connection.prepareStatement("SELECT x, y, possedeLoeuf, avatar FROM joueur;");
            ResultSet resultat = requete.executeQuery();    // on parcourt la table contenant les differents joueurs connectés
            while (resultat.next() && aLoeuf == 0) {            // pour chaque joueur
                double xj = resultat.getDouble("x");            //on récupère les coordonnées sur x du joueur
                double yj = resultat.getDouble("y");            //on récupère les coordonnées sur y du joueur
                String avatar = resultat.getString("avatar");   //on récupère l'image du joueur
                double rj = Math.sqrt(Math.pow(xj - 400, 2) + Math.pow(yj - 250, 2)); //distance entre le joueur et le centre de la map
                aLoeuf = resultat.getInt("possedeLoeuf");       //on regarde si ce joueur a l'oeuf
                if (aLoeuf == 1) {                              //si il a l'oeuf alors il devient la cible du dragon
                    try {
                        spritej = ImageIO.read(getClass().getResource(avatar));
                    } catch (IOException ex) {
                        Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    double xjdn = this.x - xj;    //vecteur selon x entre le joueur et le dragon
                    double yjdn = this.y - yj;    //vecteur selon y entre le joueur et le dragon
                    double dxjdn = Math.abs(xjdn) - sprite.getWidth() / 2 - spritej.getWidth() / 2;     //distance x joueur dragon
                    double dyjdn = Math.abs(yjdn) - sprite.getHeight() / 2 - spritej.getHeight() / 2;   //distance y joueur dragon
                    double djdn = Math.sqrt(Math.pow(xjdn, 2) + Math.pow(yjdn, 2)); //distance joueur dragon                    
                    xjd = xjdn;     // on modifie les parametres pour que ce joueur devienne la cible du dragon
                    yjd = yjdn;
                    djd = djdn;
                    dxjd = dxjdn;
                    dyjd = dyjdn;
                    xjoueur = xj;
                    yjoueur = yj;

                } else if (rj < r) { //si le joueur est dans le cercle
                    try {
                        spritej = ImageIO.read(getClass().getResource(avatar));
                    } catch (IOException ex) {
                        Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    double xjdn = this.x - xj;    //vecteur selon x entre le joueur et le dragon
                    double yjdn = this.y - yj;    //vecteur selon y entre le joueur et le dragon
                    double dxjdn = Math.abs(xjdn) - sprite.getWidth() / 2 - spritej.getWidth() / 2;     //distance x joueur dragon
                    double dyjdn = Math.abs(yjdn) - sprite.getHeight() / 2 - spritej.getHeight() / 2;   //distance y joueur dragon
                    double djdn = Math.sqrt(Math.pow(xjdn, 2) + Math.pow(yjdn, 2));     //distance joueur dragon
                    if (djd == 0) {     //si c'est le premier joueur dans le cercle
                        xjd = xjdn;     //on modifie les parametres pour que ce joueur devienne la cible du dragon
                        yjd = yjdn;
                        djd = djdn;
                        dxjd = dxjdn;
                        dyjd = dyjdn;
                        xjoueur = xj;
                        yjoueur = yj;
                    }

                    if (djdn < djd) {   //si ce joueur est plus proche du dragon que les autres alors il devient la nouvelle cible
                        xjd = xjdn;     //on modifie les parametres pour que ce joueur devienne la cible du dragon
                        yjd = yjdn;
                        djd = djdn;
                        dxjd = dxjdn;
                        dyjd = dyjdn;
                        xjoueur = xj;
                        yjoueur = yj;
                    }

                }
            }
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (djd == 0) {                 //si il n'y a pas de joueur ciblé alors le dragon se dirige vers le centre
            xjd = this.x - 400;
            yjd = this.y - 250;
            djd = Math.sqrt(Math.pow(xjd, 2) + Math.pow(yjd, 2));
            if (djd > 3) {
                this.x = x - xjd * 3 / djd; //le dragon se deplace de 3 pixels
                this.y = y - yjd * 3 / djd;
            }
        } else {
            if (this.feu.affichee == false) {
                this.attaquerJoueur(xjoueur, yjoueur, this.x, this.y);
                //System.out.println("feu");
            }

        }
        if (dxjd > 0) {     //si le dragon peut se déplacer selon x (pas de colisions)
            //this.attaquerJoueur(xjoueur, yjoueur);
            this.x = x - xjd * 3 / djd; //dragon bouge 3 pixel    

        }
        if (dyjd > 0) {     //si le dragon peut se déplacer selon y (pas de colisions)
            //this.attaquerJoueur(xjoueur, yjoueur);
            this.y = y - yjd * 3 / djd;

        }
        if (x > 800) { // collision avec le bord droit de la scene
            x = 800;
            //System.out.println("dragon trop droite");
        }
        if (x < sprite.getWidth() / 2) { // collision avec le bord gauche de la scene
            x = (int) sprite.getWidth() / 2;
            //System.out.println("dragon trop gauche");
        }
        if (y > 500) { // collision avec le bord bas de la scene
            y = 500;
            //System.out.println("dragon trop bas");
        }
        if (y < sprite.getHeight() / 2) { // collision avec le bord haut de la scene
            y = (int) sprite.getHeight() / 2;
            //System.out.println("dragon trop haut");
        }
        this.feu.miseAJour();

    }
    
    public void rendu ( Graphics2D contexte ) {
        contexte.setFont(fonte);
        contexte.setColor(Color.BLACK);
        contexte.drawImage (this. sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
        contexte.drawString("PV Dragon = " + health, 325, 25);
        this.feu.rendu(contexte);
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
    public void attaquerJoueur(double xjoueur, double yjoueur, double xdragon, double ydragon) {
        double xb = xdragon;
        double yb = ydragon;
        double xdep = xdragon;
        double ydep = ydragon;
        double xarr = xjoueur;
        double yarr = yjoueur;

        if (xarr < xdragon) {
            if (yarr < ydragon) {
                xarr -= 30;
                yarr -= 30;

            }
            if (yarr > ydragon) {
                //this.feu.xarr -= 30;
                //this.feu.yarr = ;

            } else {
                xarr -= 30;
            }

        }
        if (xarr > xdragon) {
            if (yarr < ydragon) {
                xarr += 20;
                yarr -= 20;

            }
            if (yarr > ydragon) {
                xarr += 20;
                yarr += 20;

            } else {
                xarr += 20;
            }

        } else {
            if (yarr < ydragon) {
                yarr -= 20;
            }
            if (yarr > ydragon) {
                yarr += 20;
            } else {
                xarr = xjoueur;
                yarr = yjoueur;
            }

        }
        this.feu=new Feu(xb,yb,xdep,ydep,xarr,yarr,this.connection,true);
    }

}