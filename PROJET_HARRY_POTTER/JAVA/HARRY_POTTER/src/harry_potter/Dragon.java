/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Dragon {
    
    protected double x, y;
    protected BufferedImage sprite;
    
    
    public Dragon(){
        try{
            this.sprite = ImageIO.read(getClass().getResource ("../MAP_DRAGON_images/testrouge20_20.png"));
        }
        catch(IOException ex){
            Logger.getLogger(Dragon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 400;
        this.y = 250;
        //demarrer();
    }
    
    public void miseAJour (Joueur joueur1) {
        double xj = joueur1.getX();
        double yj = joueur1.getY();        
        double r = 200;
        double rj = Math.sqrt(Math.pow(xj-400,2) + Math.pow(yj-250,2));
        System.out.println(""+xj+" "+yj+"  "+rj);
        if(rj<r) { 
            double xjd = this.x-xj;
            double yjd = this.y-yj;
            double djd = Math.sqrt((Math.pow(xjd,2) + Math.pow(yjd,2))); 
            this.x=x-xjd*3/djd;
            this.y=y-yjd*3/djd;
            /*while(this.x!= && this.y!=this.joueur.y){
                if(this.getX()<this.joueur.getX()){
                    if(this.getY()<this.joueur.getY()){
                        this.y = this.y + 5;
                    }
                    if(this.getY()>this.joueur.getY()){
                        this.y = this.y - 5;
                    }
                    this.x = this.x + 5;
                }
                
                if(this.getX()>this.joueur.getX()){
                    if(this.getY()<this.joueur.getY()){
                        this.y = this.y + 5;
                    }
                    if(this.getY()>this.joueur.getY()){
                        this.y = this.y - 5;
                    }
                    this.x = this.x - 5;
                }
            }*/
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
        contexte . drawImage (this. sprite , (int) x , (int) y , null);
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

}
