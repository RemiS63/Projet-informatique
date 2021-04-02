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
 * @author rollin
 */
public class MAP_DRAGON {
    
    private int largeur = 20;
    private int hauteur = 20;
    private int tailleTuile = 20;    
    private BufferedImage uneTuile;
    
    public MAP_DRAGON() {
               
        try { 
            BufferedImage tileset = ImageIO.read(getClass().getResource("../MAP_DRAGON_images/testorange20_20.png"));
            uneTuile = tileset;//.getSubimage(0, 0, tailleTuile, tailleTuile);
        } catch (IOException ex) {
            Logger.getLogger(MAP_DRAGON.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    public void miseAJour () {
    }
    public void rendu (Graphics2D contexte) {
        for (int x = 0; x < 40; x++){
            for (int y = 0; y < 30; y++){
        contexte.drawImage( uneTuile , x*20, y*20, null);
    }}}
}
