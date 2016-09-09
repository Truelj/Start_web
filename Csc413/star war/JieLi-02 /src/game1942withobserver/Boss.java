/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
 *
 * @author lenovo
 */
public class Boss {
        Image img;
        int x, y, sizeX, sizeY, speed;
        int hits;
        int frames;//The number of frames painted since the boss object is created.
        boolean show;
        
        MyPlane m1, m2;
        GameEvents gameEvents;
        //because enemy plane interests in the user's bullet
        LinkedList<Bullet> bullets;
        LinkedList<EnemyBullet> enemyBullets;
        LinkedList<Explosion> explosions;
        LinkedList<Enemy> enemies;
        public Boss(Image img, int speed, int x, int y,
                MyPlane m1, MyPlane m2, GameEvents gameEvents, LinkedList<EnemyBullet> enemyBullets,
                LinkedList<Bullet> bullets, LinkedList<Explosion> explosions ) {
            this.img = img;
            this.show = false;//once the boss object is created, don't show it.
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.hits = 0;//the maximum amount of hits is 50
            this.frames = 0;
            sizeX = img.getWidth(null);
            sizeY = img.getHeight(null);
            System.out.println("w:" + sizeX + " y:" + sizeY);
            this.m1 = m1;
            this.m2 = m2;
            this.gameEvents = gameEvents;
            this.bullets = bullets;
            this.enemyBullets = enemyBullets;
            this.explosions = explosions;
            
       }
       public void update(){
       //first needs to update its position
           frames++;
           if(hits <= 50){
               show = true;
           }else{
               show = false;
           }
           
           if(show){
               if(frames < 100){ y+= speed;}//keep moving in the first 100 frames
               else{ //stop moving after 100 frames
                   if(frames == 100){
                       Image enemyBulletImage;
                       //create 10 instances of enemyBullet of type 1, add it to the enemyBullets list
                       try{
                           int i = 9;
                           enemyBulletImage = ImageIO.read(new File("Resources/enemybullet1.png"));
                           do{
                              EnemyBullet newEnemyBullet = new EnemyBullet(enemyBulletImage,1,5, x + 20*i, y + 10,
                                                     this.m1, this.m2, this.explosions, this.gameEvents);
                              enemyBullets.add(newEnemyBullet);
                              i--;
                           }while(i > 0);
                       }catch(Exception e){
                            System.out.print("No resources are found");
                            e.getStackTrace();
                       }//end of catch
                   }else if(frames == 200){
                       Image enemyBulletImage;
                       //create 10 instances of enemyBullet of type 2, add it to the list
                       try{
                           int i = 5;
                           enemyBulletImage = ImageIO.read(new File("Resources/enemybullet2.png"));
                           //System.out.print("boss x:"+ x + "boss y:"+ y);
                           do{
                              EnemyBullet newEnemyBullet = new EnemyBullet(enemyBulletImage,2,5, x - 20*i, y + 10,
                                                     this.m1, this.m2, this.explosions, this.gameEvents);
                              enemyBullets.add(newEnemyBullet);
                              i--;
                           }while(i > 0);
                           i = 5;
                           do{
                              EnemyBullet newEnemyBullet = new EnemyBullet(enemyBulletImage,2,5, x + 20*i, y + 10,
                                                     this.m1, this.m2, this.explosions, this.gameEvents);
                              enemyBullets.add(newEnemyBullet);
                              i--;
                           }while(i > 0);
                       }catch(Exception e){
                           System.out.print("No resources are found");
                           e.getStackTrace();
                       }//end of catch
                   }//end of last else if
               }//end of else
               if(m1.collision(this.x, this.y, this.sizeX, this.sizeY)){//check collision with plane 1
                   System.out.println("Boss got hit plane 1");
                   gameEvents.boss_MyPlane_Event(1);//report the event
               }
               if(m2.collision(this.x, this.y, this.sizeX, this.sizeY)){//check collision with plane 2
                   System.out.println("Boss got hit plane 2");
                   gameEvents.boss_MyPlane_Event(2);//report the event
               }
               //now need to check if the user bullet hits boss
               //for(ListIterator<Bullet> iterator = bullets.listIterator(); iterator.hasNext();){
               for(int i = 0; i< bullets.size(); i++){
                   //Bullet b = iterator.next();
                   Bullet b = bullets.get(i);//get the bullet
                   if(b.collision(x,y,sizeX,sizeY)){
                       hits++;//increase hits
                       //create explosion music
                       ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion1.wav");
                       //iterator.remove();//destroy the bullet
                       bullets.remove(i);//remove the bullet
                       Explosions e = new Explosions(1,b.x,b.y);//create an explosion for bullet
                       explosions.addAll(e.getExplosions());
                       gameEvents.boss_MyBullet_Event();//report the event
                   }
               }//end of for loop
               if(hits == 50){
                   show = false;
                   //System.out.println("boss explodes");
                   //System.out.println("play exlosion music 2");
                   ExplosionMusic em2 = new ExplosionMusic("Resources/snd_explosion2.wav");
                   //System.out.println("Create 5 explosion images");
                   Explosions e1 = new Explosions(2,x,y + 50);
                   Explosions e2 = new Explosions(2,x + 50,y + 100);
                   Explosions e3 = new Explosions(2,x + 100,y + 150);
                   Explosions e4 = new Explosions(2,x + 150,y + 100);
                   Explosions e5 = new Explosions(2,x + 200,y + 50);
                   explosions.addAll(e1.getExplosions());
                   explosions.addAll(e2.getExplosions());
                   explosions.addAll(e3.getExplosions());
                   explosions.addAll(e4.getExplosions());
                   explosions.addAll(e5.getExplosions());
                   gameEvents.boss_is_dead();//report the event
               }
           }//end of outer if(show){}
       }//end of update  
       public void draw(ImageObserver obs, Graphics2D g2) {
            if (show) {
                //System.out.println("draw enemy");
              g2.drawImage(img, x, y, obs);
              g2.setColor(Color.red);
              g2.drawRect(10,5,200,15);
              g2.fillRect(10,5,200-hits*4, 15);
            }
    }
}