/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.sql.ResultSet;
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

/**
 *
 * @author rollin
 */
public class Jeu {
    
    private TileMap carte;
    protected Joueur joueur1;
    private Dragon dragon;
    private bombe bombe;
    protected Oeuf oeuf;
    protected Connection connection;
    protected BarreDeSante sante;
    
    public Jeu(String pseudo) throws SQLException {   
        this.connection = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
        this.carte = new TileMap((getClass().getResource("../res/testmap.txt")).toString().substring(5), 32);
        //System.out.println((getClass().getResource("../res/testmap.txt")).toString().substring(5));
        this.joueur1 = new Joueur(pseudo,connection);
        this.dragon = new Dragon(connection);
        this.bombe=new bombe(connection);
        this.oeuf = new Oeuf(connection);
        this.sante = new BarreDeSante(connection);
    }

    public void miseAJour() {
        this.carte.miseAJour();
        this.joueur1.miseAJour();
        this.dragon.miseAJour();
        this.oeuf.miseAJour();
        this.sante.miseAJour();
        this.bombe.miseAJour();
        //System.out.println(""+this.joueur1.x+""+this.joueur1.y);
    }

    public void rendu(Graphics2D contexte) throws SQLException {        
        this.carte.rendu(contexte);        
        this.afficherJoueurs(contexte);
        this.dragon.rendu(contexte);
        this.oeuf.rendu(contexte);
        this.sante.rendu(contexte);
        this.bombe.rendu(contexte);
    }
    public void lancerBombe(){
        int portee = 100;
        //double d=Math.sqrt(Math.pow(this.joueur1.x-this.dragon.x,2) + Math.pow(this.joueur1.y-this.dragon.y,2));
        //if (d<200){
            this.bombe.xb=this.joueur1.x;
            this.bombe.yb=this.joueur1.y;
            this.bombe.xdep=this.joueur1.x;
            this.bombe.ydep=this.joueur1.y;            
            this.bombe.affichee=true;
            if (this.joueur1.gauche ) {
                if (this.joueur1.haut ) {
                    this.bombe.xarr=this.joueur1.x-portee;
                    this.bombe.yarr=this.joueur1.y-portee;
                }
                if (this.joueur1.bas ) {
                    this.bombe.xarr=this.joueur1.x-portee;
                    this.bombe.yarr=this.joueur1.y+portee;
                } 
                else{
                    this.bombe.xarr=this.joueur1.x-portee;
                    this.bombe.yarr=this.joueur1.y;
                }
            }else if (this.joueur1.droite ) {
                if (this.joueur1.haut ) {
                    this.bombe.xarr=this.joueur1.x+portee;
                    this.bombe.yarr=this.joueur1.y-portee;
                }
                if (this.joueur1.bas ) {
                    this.bombe.xarr=this.joueur1.x+portee;
                    this.bombe.yarr=this.joueur1.y+portee;                    
                }
                else{
                    this.bombe.xarr=this.joueur1.x+portee;
                    this.bombe.yarr=this.joueur1.y;
                }    
            }else if (this.joueur1.haut ) {
                this.bombe.xarr=this.joueur1.x;
                this.bombe.yarr=this.joueur1.y-portee;
            }else if (this.joueur1.bas ) {
                this.bombe.xarr=this.joueur1.x;
                this.bombe.yarr=this.joueur1.y+portee;
            }else{
                this.bombe.xarr=this.joueur1.x+portee;
                this.bombe.yarr=this.joueur1.y;
            }                     
        //}        
    }
    public Joueur getJoueur(){
        return(this.joueur1);
    }
    public void setGauche(boolean gauche){
        this.joueur1.setGauche(gauche);
    }
    public void setDroite(boolean droite){
        this.joueur1.setDroite(droite);
    }
    public void afficherJoueurs(Graphics2D contexte) throws SQLException{
        //Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
        PreparedStatement requete = this.connection.prepareStatement("SELECT x, y, avatar FROM joueur;");
        ResultSet resultat = requete.executeQuery();
        while (resultat.next()) {
            double x = resultat.getDouble("x");
            double y = resultat.getDouble("y");
            String avatar = resultat.getString("avatar");
            try {
                BufferedImage sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/harry potter1.png"));
                contexte.drawImage (sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
            } catch ( IOException ex ) {
                Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
            }                
        }
        requete.close();
        //this.connection.close();
    }
    
}