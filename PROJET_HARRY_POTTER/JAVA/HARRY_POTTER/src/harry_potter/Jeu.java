/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.awt.Graphics2D;

/**
 *
 * @author rollin
 */
public class Jeu {
    
    private MAP_DRAGON carte;
    private Joueur joueur1;
    private Dragon dragon;
     
    public Jeu() {   
        this.carte = new MAP_DRAGON();
        this.joueur1 = new Joueur();
        this.dragon = new Dragon();
    }

    public void miseAJour() {
        this.carte.miseAJour();
        this.joueur1.miseAJour();
        this.dragon.miseAJour(joueur1);
    }

    public void rendu(Graphics2D contexte) {        
        this.carte.rendu(contexte);
        this.joueur1.rendu(contexte);
        this.dragon.rendu(contexte);
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
}
