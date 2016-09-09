/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Bullet{
        Image img;
        int x, y, sizeX, sizeY, speed;
        boolean show;
        Rectangle bulletBox;
        //MyPlane m;//Because it needs to remember which plane fires it...no
        //Bullet doesn't need to know which plane fires it, since there is 
        //no competition between 2 planes.
        
        Bullet(Image img, int speed, int x, int y){
            this.img = img;
            this.speed = speed;
            this.x = x;
            this.y = y;
            this.show = true;
            sizeX = img.getWidth(null);
            sizeY = img.getHeight(null);
            System.out.println("w:" + sizeX + " y:" + sizeY);
        }
        public boolean collision(int x, int y, int w, int h) {
            //When bullet touches the enemy plane 
            bulletBox = new Rectangle(this.x - 10, this.y + 10, this.sizeX, this.sizeY);
            Rectangle EnemyBox = new Rectangle (x,y,w,h);
            if(this.bulletBox.intersects(EnemyBox)) { 
                //show = false;
                return true;
            }
            return false;
        }
        public void update() {
            y -= 20;
            //if the bullet get out of screen, destroy it or don't draw
            if(y < 0){
                show = false;
            }

        }
         public void draw(ImageObserver obs, Graphics2D g2) {
                System.out.println("draw bullet");
                g2.drawImage(img, x, y, obs);
        }
}