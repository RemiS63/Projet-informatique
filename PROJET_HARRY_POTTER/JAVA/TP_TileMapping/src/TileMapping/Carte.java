/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMapping;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Exemple de classe carte
 *
 * @author guillaume.laurent
 */
public class Carte {

    private int largeur = 12;
    private int hauteur = 9;
    private int tailleTuile = 32;
    
    private BufferedImage uneTuile;

    public Carte() {
        try {
            BufferedImage tileset = ImageIO.read(getClass().getResource("images/tileSetMinecraft32x32.png"));
            uneTuile = tileset.getSubimage(0, 0, tailleTuile, tailleTuile);
        } catch (IOException ex) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void miseAJour() {

    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(uneTuile, 0, 0, null);
    }

}
