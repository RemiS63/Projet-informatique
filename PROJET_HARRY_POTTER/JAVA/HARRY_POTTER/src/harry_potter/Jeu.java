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
    private Joueur joueur1;
    private Dragon dragon;
    protected Oeuf oeuf;
    protected Connection connection;
     
    public Jeu() throws SQLException {   
        this.connection = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs1_tp1_harrypotter?serverTimezone=UTC", "harry", "XtCQDfMaoqzTyVam");
        //changer le chemin pour le .txt pour Github
        this.carte = new TileMap("C:\\Users\\user\\Documents\\GitHub\\Projet-informatique\\PROJET_HARRY_POTTER\\JAVA\\HARRY_POTTER\\src\\res\\testmap.txt", 32);
        this.joueur1 = new Joueur("Susinor",connection);
        this.dragon = new Dragon(connection);
        this.oeuf = new Oeuf();
    }

    public void miseAJour() {
        this.carte.miseAJour();
        this.joueur1.miseAJour();
        this.dragon.miseAJour(joueur1);
        this.oeuf.miseAJour(joueur1);
    }

    public void rendu(Graphics2D contexte) throws SQLException {        
        this.carte.rendu(contexte);
        this.afficherJoueurs(contexte);
        this.dragon.rendu(contexte);
        this.oeuf.rendu(contexte);
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
                BufferedImage sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/testbleu20_20.png"));
                contexte.drawImage(sprite, (int) x , (int) y, null);
            } catch ( IOException ex ) {
                Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
            }                
        }
        requete.close();
        //this.connection.close();
    }
    
}
