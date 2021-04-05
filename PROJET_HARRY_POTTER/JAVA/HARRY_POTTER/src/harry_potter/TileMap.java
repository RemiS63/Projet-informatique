/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harry_potter;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.imageio.ImageIO;

/**
 *
 * @author Romain
 */
public class TileMap {
    
    private int x;
    private int y;
    private final int xImage = 20;
    private final int yImage = 20;
    
    private int tileSize;
    private int [][] map;
    private int mapWidth;
    private int mapHeight;
    
    
    public TileMap(String s, int tileSize){
        this.tileSize = tileSize;
        
        try {
            
            BufferedReader br = new BufferedReader(new FileReader(new File(s)));
            
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];
            
            String delimiters = " ";
            for(int ligne =0; ligne < mapHeight; ligne++) {
                String line = br.readLine();
                String [] tokens = line.split(delimiters);
                for(int col =0; col < mapWidth; col++) {
                    map [ligne][col] = Integer.parseInt(tokens[col]);
                    
                }
            }
            
        }
        catch(Exception e) {}
    }

    public void miseAJour () {
    }
    public void rendu (Graphics2D g) {
        BufferedImage imgTerre = null;
        BufferedImage imgHerbe = null;
        BufferedImage imgBaseJoueur = null;
        try{
            CelluleTerre terre = new CelluleTerre();
            imgTerre = ImageIO.read(getClass().getResource(terre.GetCheminImage()));
            
            CelluleHerbe herbe = new CelluleHerbe();
            imgHerbe = ImageIO.read(getClass().getResource(herbe.GetCheminImage()));
            
            CelluleBaseJoueur baseJoueur = new CelluleBaseJoueur();
            imgBaseJoueur = ImageIO.read(getClass().getResource(baseJoueur.GetCheminImage()));
        }
        catch(Exception e) {}

        for (int ligne = 0; ligne < mapHeight; ligne++){
            for (int col = 0; col < mapWidth; col++){
                int rc = map[ligne][col];
                
                if (rc == 0){
                    AffineTransform at = new AffineTransform();
                    at.translate(col*xImage,ligne*yImage);
                    g.drawImage(imgTerre, at, null);
                }
                if (rc == 1){
                    AffineTransform at = new AffineTransform();
                    at.translate(col*xImage,ligne*yImage);
                    g.drawImage(imgHerbe, at, null);
                }
                if (rc == 2){
                    AffineTransform at = new AffineTransform();
                    at.translate(col*xImage,ligne*yImage);
                    g.drawImage(imgBaseJoueur, at, null);
                }
            }
        }
    }
}