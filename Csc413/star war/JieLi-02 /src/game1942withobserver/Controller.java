/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
/**
 *The Controller class is used to draw the bottom image, planes' health and score
 */
public class Controller {
    //Controller needs to know both plane's health and score
    MyPlane m1;
    MyPlane m2;
    Image bottom;//Controller needs to draw the bottom image
    
    public Controller(MyPlane m1, MyPlane m2, Image bottom){
        this.m1 = m1;
        this.m2 = m2;
        this.bottom = bottom;
    }
     public void draw(ImageObserver obs, Graphics2D g2) {
         //g2.drawImage(bottom, 0, 380, obs);//draw the bottom
         g2.setColor(Color.gray);
         g2.drawRect(0, 380, bottom.getWidth(null), bottom.getHeight(null));
         g2.fillRect(0, 380, bottom.getWidth(null), bottom.getHeight(null));
         //draw "Helath 1" and "Health 2"
         g2.setColor(Color.red);
         g2.drawString("Health 1", 20, 410);
         g2.drawString("Health 2", 20, 440);
         //draw "Score: "
         g2.drawString("Score: ", 300, 420);
         g2.drawString(m1.score + m2.score + "", 350, 420);
         //draw health bar
         g2.setColor(Color.yellow);
         g2.drawRect(90,395,100,20);
         g2.fillRect(90,395,m1.health,20);
         g2.drawRect(90,425,100,20);
         g2.fillRect(90,425,m2.health,20);
        }
    
}
