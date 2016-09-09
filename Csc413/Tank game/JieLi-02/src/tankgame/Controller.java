/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *Controll class is used to draw score and ammunition of each tank
 */
public class Controller {
    //Controller needs to know both plane's health and score
    Tank tank1;
    Tank tank2;
    Image pickup_1;//Controller needs to draw the pickup image
    Image pickup_2;
    int ammunition_1;
    int ammunition_2;
    
    public Controller(Tank tank1, Tank tank2){
        this.tank1 = tank1;
        this.tank2 = tank2;
        
    }
    
    public void draw(ImageObserver obs, Graphics2D g2){
        try{
            if(tank1.newPickupType == 1){
                pickup_1 = ImageIO.read(new File("resources/Pickup_1.png"));
            }else if(tank1.newPickupType == 2){
                pickup_1 = ImageIO.read(new File("resources/Pickup_2.png"));
            }else if(tank1.newPickupType == 3){
                pickup_1 = ImageIO.read(new File("resources/Pickup_3.png"));
            }
            
            if(tank2.newPickupType == 1){
                pickup_2 = ImageIO.read(new File("resources/Pickup_1.png"));
            }else if(tank2.newPickupType == 2){
                pickup_2 = ImageIO.read(new File("resources/Pickup_2.png"));
            }else if(tank2.newPickupType == 3){
                pickup_2 = ImageIO.read(new File("resources/Pickup_3.png"));
            }
        }catch(Exception e) {
            System.out.print("TankGame -- No resources are found");
        }
        
        g2.setColor(Color.yellow);
        
        g2.drawString("Score 1: " + tank1.score, 10, 30);
        g2.drawImage(pickup_1, 90, 10, obs);
        g2.drawString("Score 2: " + tank2.score, 10, 70);
        g2.drawImage(pickup_2, 90, 50, obs);
        
        this.ammunition_1 = tank1.ammunition;
        this.ammunition_2 = tank2.ammunition;
        
        g2.drawString(": "+ this.ammunition_1,130, 30);
        g2.drawString(": "+ this.ammunition_2,130, 70);
    }
}
