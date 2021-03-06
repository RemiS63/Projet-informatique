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
    protected BarreDeSanteDragon santeDragon;
    protected BufferedImage image;
    
    public Jeu(String pseudo) throws SQLException {   
        this.connection = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
        this.carte = new TileMap((getClass().getResource("../res/testmap.txt")).toString().substring(5), 32);
        //System.out.println((getClass().getResource("../res/testmap.txt")).toString().substring(5));
        this.joueur1 = new Joueur(pseudo,connection);
        this.dragon = new Dragon(connection);
        this.bombe=new bombe(connection,0,0,0,0,0,0,"../MAP_DRAGON_images/sortilège.png");
        this.oeuf = new Oeuf(connection);       
        this.sante = new BarreDeSante(connection);
        this.santeDragon = new BarreDeSanteDragon(connection);
    }
    public Joueur getJoueur1() {
        return joueur1;
    }

    public void miseAJour() {
        this.carte.miseAJour();
        this.joueur1.miseAJour();
        this.dragon.miseAJour();
        this.oeuf.miseAJour();
        this.sante.miseAJour();
        this.bombe.miseAJour();
        this.santeDragon.miseAJour();
        this.faireDesDegats();
        //System.out.println(""+this.dragon.health);
        //System.out.println(""+this.joueur1.x+""+this.joueur1.y);
    }

    public void rendu(Graphics2D contexte) throws SQLException {        
        this.carte.rendu(contexte);        
        this.afficherJoueurs(contexte);
        this.santeDragon.rendu(contexte);
        this.dragon.rendu(contexte);
        this.oeuf.rendu(contexte);
        this.sante.rendu(contexte);
        this.bombe.rendu(contexte);
    }
        public void lancerBombe(){
        int portee = 100;           //portée des sorts du joueurs
        double xb=this.joueur1.x;   //les coordonnées de départ de la bombe sont les coordonnées actuels du joueur
        double yb=this.joueur1.y;
        double xdep=xb;
        double ydep=yb;   
        double xarr = xb;
        double yarr = yb;
        String image = "../MAP_DRAGON_images/sortilège.png";
        this.bombe.affichee=true;
        if (this.joueur1.gauche ) {         //en fonction de la direction de déplacement du joueur, on modifie la direction
            if (this.joueur1.haut ) {       // dans laquelle on lance la bombe. On modifie également l'image de ce sort.
                xarr=this.joueur1.x-portee;
                yarr=this.joueur1.y-portee;
                image = "../MAP_DRAGON_images/sortilège vers le haut.png";
            }
            else if (this.joueur1.bas ) {
                xarr=this.joueur1.x-portee;
                yarr=this.joueur1.y+portee;
                image = "../MAP_DRAGON_images/sortilège vers le bas.png";
            } 
            else{
                xarr=this.joueur1.x-portee;
                yarr=this.joueur1.y;
                image = "../MAP_DRAGON_images/sortilège vers la gauche.png";
            }
        }
        else if (this.joueur1.droite ) {
            if (this.joueur1.haut ) {
                xarr=this.joueur1.x+portee;
                yarr=this.joueur1.y-portee;
                image = "../MAP_DRAGON_images/sortilège vers le haut.png";
            }
            else if (this.joueur1.bas ) {
                xarr=this.joueur1.x+portee;
                yarr=this.joueur1.y+portee; 
                image = "../MAP_DRAGON_images/sortilège vers le bas.png";
            }
            else{
                xarr=this.joueur1.x+portee;
                yarr=this.joueur1.y;
                image = "../MAP_DRAGON_images/sortilège.png";
            }    
        }
        else if (this.joueur1.haut ) {
            xarr=this.joueur1.x;
            yarr=this.joueur1.y-portee;
            image = "../MAP_DRAGON_images/sortilège vers le haut.png";
        }
        else if (this.joueur1.bas ) {
            xarr=this.joueur1.x;
            yarr=this.joueur1.y+portee;
            image = "../MAP_DRAGON_images/sortilège vers le bas.png";
        }
        else{                                                                       //si le joueur ne se deplace pas
            if (this.joueur1.ordreDeJoueur==0 || this.joueur1.ordreDeJoueur==2){    
                xarr=this.joueur1.x+portee;     //si la position de départ du joueur est à gauche, il tire à droite          
                yarr=this.joueur1.y;
            }
            else{                               //si la position de départ du joueur est à droite, il tire à gauche 
                xarr=this.joueur1.x-portee;
                yarr=this.joueur1.y;
            }            
        }                 
        this.bombe=new bombe(connection,xb,yb,xdep,ydep,xarr,yarr, image);  //on crée une bombe avec les coordonée de determinée
               
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
    public void faireDesDegats(){
        try {
            PreparedStatement requete = this.connection.prepareStatement("SELECT x, y, degats FROM arme WHERE affiche=1;");
            ResultSet resultat = requete.executeQuery();    //on parcourt la table des sorts
            while (resultat.next()) {
                double xb = resultat.getDouble("x");        //on récupère la position et les dégats du sort
                double yb = resultat.getDouble("y"); 
                double degats = resultat.getDouble("degats"); 
                if (degats==10){    //si le sort fait 10 degats c'est un sort lancé par le joueur qui inflige des dégats au dragon
                    double xd = this.dragon.x;
                    double yd = this.dragon.y;
                    double rj = Math.sqrt(Math.pow(xd-xb,2) + Math.pow(yd-yb,2));    //on calcule la distance entre le sort le dragon       
                    if (rj<50){     //si cette distance est suffisament faible on inflige des dégats au dragon
                        this.dragon.health-=degats;
                    }    
                }
                else if (degats==15){   //si le sort fait 15 degats c'est un sort lancé par le dragon qui inflige des dégats au joueur
                    double xd = this.joueur1.x;
                    double yd = this.joueur1.y;
                    double rj = Math.sqrt(Math.pow(xd-xb,2) + Math.pow(yd-yb,2));    //on calcule la distance entre le sort le dragon             
                    if (rj<20){     //si cette distance est suffisament faible on inflige des dégats au joueur
                        this.joueur1.health-=degats;
                    }    
                }                    
            }
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean estTermine(){
        if (this.oeuf.seTrouveEnCoordonnéesIntiales()){
            return true;
        }
        else {
            return false;
           
        }
    }
    
    
}