/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.LinkedList;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
/**
 *This class is used to create a series of explosions
 * @author lenovo
 */
public class Explosions {
    LinkedList<Explosion> explosions = new LinkedList<Explosion>();
    Image[] smallExplosion = new Image[6];
    Image[] largeExplosion = new Image[7];
    int x, y; // where to create the explosion
    Explosions( int type, int x, int y){
        this.x = x;
        this.y = y;
        try{
            if(type == 1){
               for(int i = 1; i <= smallExplosion.length; i++){
                   smallExplosion[i-1] = ImageIO.read(new File("Resources/Explosion_small_"+ i + ".png"));
                   explosions.add(new Explosion(smallExplosion[i-1], this.x, this.y));    
               } 
            }else if(type == 2){
                for(int i = 1; i <= largeExplosion.length; i++){
                    largeExplosion[i-1] = ImageIO.read(new File("Resources/Explosion_large_"+ i + ".png"));
                    explosions.add(new Explosion(largeExplosion[i-1], this.x, this.y));  
                }
            }
        }catch(Exception e){
            System.out.print("No resources are found");
        }
        
    }
    LinkedList<Explosion> getExplosions(){
        return this.explosions;
    }
    
}