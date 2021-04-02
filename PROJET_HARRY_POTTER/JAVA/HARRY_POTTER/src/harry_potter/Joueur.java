/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Joueur {

    protected BufferedImage sprite ;
    protected double x , y;
    private boolean gauche , droite , haut , bas ;
    
    public  Joueur(){
        
         try {
         this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/testbleu20_20.png"));
         } catch ( IOException ex ) {
             Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
         }
         
         this.x = 0;
         this.y = 0;
         this.gauche = false;
         this.droite = false;
         this.haut = false;
         this.bas = false;
    }
    
    public void miseAJour () {
        if (this.gauche ) {
            x -= 5;
        }         
        if (this.droite ) {
            x += 5;
        }
        if (this.haut ) {
            y -= 5;
        }
        if (this.bas ) {
            y += 5;
        }         
        if (x > 800 - sprite.getWidth() ) { // collision avec le bord droit de la scene
            x = 800 - sprite.getWidth() ;
        }
        if (x < 0) { // collision avec le bord gauche de la scene
            x = 0;
        }
        if (y > 500 - sprite.getWidth()) { // collision avec le bord droit de la scene
            y = 500 - sprite.getWidth() ;
        }
        if (y < 0) { // collision avec le bord gauche de la scene
            y = 0;
        }
}
        public void rendu ( Graphics2D contexte ) {
            contexte.drawImage(this.sprite , (int) x , (int) y , null);
        }
        
        public void setGauche (boolean gauche ) {
            this.gauche = gauche ;
        }
        
        public void setDroite(boolean droite ) {
            this.droite = droite ;
        }
        
         public void setHaut (boolean haut ) {
            this.haut = haut ;
        }
        
        public void setBas (boolean bas ) {
            this.bas = bas ;
        }
        
        public double getX () {
            return x;
        }
        
        public double getY () {
            return y;
        }
        
        public double getLargeur () {
            return sprite.getHeight () ;
        }
        
        public double getHauteur () {
            return sprite.getWidth () ;
        }
}

    
    

