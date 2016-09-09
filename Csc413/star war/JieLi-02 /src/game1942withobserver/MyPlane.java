/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class MyPlane implements Observer {
        Image img;
        int x, y, speed, width, height;
        Rectangle bbox;
        boolean show;
        int can_shoot;
        int planeNumber;
        int health;
        int score;
        LinkedList<Explosion> explosions;
        LinkedList<Bullet> bullets;
       
        MyPlane(Image img, int x, int y, int speed, int planeNumber, LinkedList<Bullet> bullets, LinkedList<Explosion> explosions) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            width = img.getWidth(null);
            height = img.getHeight(null);
            show = true;
            can_shoot = 1;
            this.planeNumber = planeNumber;
            this.health = 100; // Set the health as 100
            this.bullets = bullets;
            this.explosions = explosions;
            
        }
        public boolean collision(int x, int y, int w, int h) {
            bbox = new Rectangle(this.x + 2, this.y + 12, this.width - 6, this.height - 22);
            Rectangle otherBBox = new Rectangle (x,y,w,h);
            if(this.bbox.intersects(otherBBox)) { 
                return true;
            }
            return false;
        }
      
        public void update(Observable obj, Object arg) {
            if(show){
            GameEvents gameEvents = (GameEvents) arg;
            LinkedList<Integer> keys = gameEvents.keys;//get the list
            //Iterator<Integer> iterator = keySet.iterator();//get keySet's iterator
            if(keys != null){
            for(int i = 0; i< keys.size(); i++){
                if(planeNumber == 2){//key events for plane 1
                    //switch (keyE.getKeyCode()){
                    switch(keys.get(i).intValue()){
                        case KeyEvent.VK_A:
                            if(x > 0){x -= speed;}
                            break;
                        case KeyEvent.VK_D:
                            if(x < 640 - width){x += speed;}
                            break;
                        case KeyEvent.VK_W:
                            if(y > 0){y -= speed;}
                            break;
                        case KeyEvent.VK_S:
                            if(y < 380 - height){y += speed;}
                            break;
                        case KeyEvent.VK_SPACE:
                            System.out.println("Plane 1 Fire");
                            //Create an instance of bullet, add it to the list
                            Image bulletImage;
                            try{
                                if(can_shoot == 1){//shoot
                                    bulletImage = ImageIO.read(new File("Resources/bullet.png"));
                                    Bullet newBullet = new Bullet(bulletImage, 8, x+15, y);
                                    bullets.add(newBullet);
                                }
                                can_shoot++;
                                if(can_shoot == 3){
                                    can_shoot = 1;
                                }
                            }catch(Exception e){
                                System.out.print("No resources are found");
                            }
                            break;
                        default:
                    }//end of switch
                }else if(planeNumber == 1){ //key events for plane 2
                    switch (keys.get(i).intValue()) {    
                    case KeyEvent.VK_LEFT:
                        if(x > 0){x -= speed;}
	        	break; 
                    case KeyEvent.VK_RIGHT:
                        if(x < 640 - width){x += speed;}
	        	break;
                    case KeyEvent.VK_UP:
                        if(y > 0){y -= speed;}
	        	break; 
                    case KeyEvent.VK_DOWN:
                        if(y < 380 - height){y += speed;}
	        	break;
                    case KeyEvent.VK_ENTER:
                            System.out.println("Plane 2 Fire");
                            //Create an instance of bullet, add it to the list
                            Image bulletImage;
                            try{
                                if(can_shoot == 1){//shoot
                                    bulletImage = ImageIO.read(new File("Resources/bullet.png"));
                                    Bullet newBullet = new Bullet(bulletImage, 8, x+15, y);
                                    bullets.add(newBullet);
                                }
                                can_shoot++;
                                if(can_shoot == 3){
                                    can_shoot = 1;
                                }
                            }catch(Exception e){
                                System.out.print("No resources are found");
                            }
                    default:
                    
                    }//end of switch block
                }//end of else ifblock
            }//end of for loop
            }//end of outer if
            //set it as "if" because key event and enemy event may happen at the sametime
            if(gameEvents.enemyEvent) {//if Enemy collides with MyPlane
                if(planeNumber == 1 && gameEvents.enemy_with_plane1){
                    System.out.println("enemy plane collides with plane 1! Reduce Health");
                    health -= 10;
                }else if(planeNumber == 2 && gameEvents.enemy_with_plane2){
                    System.out.println("enemy plane collides with plane 2! Reduce Health");
                    health -= 10;
                }
                //System.out.println("Explosion! Reduce Health");     
            }
            else if(gameEvents.enemyBulletEvent){//if enemyBullet hits MyPlane
                if(planeNumber == 1 && gameEvents.bullet_with_plane1){
                    System.out.println("plane 1 collides with enemy bullet! Reduce Health");
                    health -= 5;
                }else if(planeNumber == 2 && gameEvents.bullet_with_plane2){
                    System.out.println("plane 2 collides with enemy bullet! Reduce Health");
                    health -= 5;
                }
                
            }
            else if(gameEvents.enemy_with_myBullet){//if my Bullet hits enemy plane
                System.out.println("enemy plane collides with my bullet!increase score");
                score += 5;// each user plane adds 5 points
                
            }
            else if(gameEvents.bossEvent){//if boss collides with MyPlane
                if(planeNumber == 1 && gameEvents.boss_with_plane1){
                    System.out.println("plane 1 collides with boss! Reduce Health");
                    health = 0;
                }else if(planeNumber == 2 && gameEvents.boss_with_plane2){
                    System.out.println("plane 1 collides with boss! Reduce Health");
                    health = 0;
                } 
            }
            else if(gameEvents.boss_with_myBullet){//if my Bullet hits boss
                System.out.println("Boss collides with my Bullet!increase score");
                score += 1;// each user plane adds 1 points           
            }
            else if(gameEvents.bossDie){// if Boss is dead
                System.out.println("Boss is dead!");
                score += 200; // each user plane adds 200 points             
            }  
            if(health <= 0 ){
                //enemy plane is dead
                show = false;
                //create explosion music 
                ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion2.wav");
                //create explosion
                Explosions e = new Explosions(2,this.x,this.y);
                this.explosions.addAll(e.getExplosions());
                //health--;
            }
            }//end of outer if(show)
        }//end of update
        
        public void draw(ImageObserver obs, Graphics2D g2) {
            if(show){
                g2.drawImage(img, x, y, obs);
            }
        }
        
    }