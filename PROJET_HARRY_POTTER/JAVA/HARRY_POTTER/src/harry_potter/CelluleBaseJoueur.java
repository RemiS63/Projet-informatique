/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

/**
 *
 * @author Romain
 */
public class CelluleBaseJoueur extends Cellule {
    private int vitesse;
    private String cheminImage;
    
    public CelluleBaseJoueur(){
        super(2, "/MAP_DRAGON_images/pixelgris.png");
    }
}
