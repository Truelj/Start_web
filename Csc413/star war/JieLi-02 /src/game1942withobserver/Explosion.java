/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Explosion{
        Image img;
        int x, y;
        
        Explosion(Image img, int x, int y){
            this.img = img;
            this.x = x;
            this.y = y;
            //System.out.println("w:" + sizeX + " y:" + sizeY);
        }
         public void draw(ImageObserver obs, Graphics2D g2) {
                System.out.println("draw explosion");
                g2.drawImage(img, x, y, obs);
        }
}