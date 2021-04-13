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

/**
 *
 * @author User
 */
public class Oeuf {
    protected BufferedImage sprite ;
    protected double x , y;
    protected boolean saisi;
    
    
    public Oeuf(){
        try {
         this. sprite = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/testjaune20_20.png"));
         } catch ( IOException ex ) {
             Logger . getLogger ( Joueur .class. getName () ). log ( Level . SEVERE , null , ex );
         }
        this.x = 400;       //milieu de map d'axe x
        this.y = 250;
        this.saisi=false;
    }
    
    public void miseAJour (Joueur joueur) {
        if (this.saisi){
            x=joueur.x;
            y=joueur.y;
        }
    }
        
    public void rendu ( Graphics2D contexte ) {
            contexte.drawImage (this.sprite , (int) x-sprite.getWidth()/2 , (int) y-sprite.getHeight()/2 , null);
    }
    
    public double getX () {
            return x;
        }
        
    public double getY () {
        return y;
    }
        
}
