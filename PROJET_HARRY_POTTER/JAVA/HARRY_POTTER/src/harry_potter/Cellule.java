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
public class Cellule {
    
    private int vitesse;
    private String cheminImage;
    
    public Cellule(){
        vitesse = 1;
        cheminImage = null;
    }
    
    public Cellule(int vitesse, String cheminImage){
        this.vitesse = vitesse;
        this.cheminImage = cheminImage;
    }
    
    public String GetCheminImage(){
        return cheminImage;
    }
}
