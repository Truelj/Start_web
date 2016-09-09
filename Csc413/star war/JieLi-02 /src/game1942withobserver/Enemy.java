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
import javax.imageio.ImageIO;

public class Enemy {

        Image img;
        int x, y, sizeX, sizeY, speed;
        int enemyType;//There are 3 kinds of enemyPlane
        boolean show;
        
        MyPlane m1, m2;
        GameEvents gameEvents;
        //because enemy plane interests in the user's bullet
        LinkedList<Bullet> bullets;
        LinkedList<EnemyBullet> enemyBullets;
        LinkedList<Explosion> explosions;
        
        Enemy(Image img, int speed, int x, int y,int enemyType,
                MyPlane m1, MyPlane m2, GameEvents gameEvents, 
                LinkedList<Bullet> bullets, LinkedList<Explosion> explosions,
                LinkedList<EnemyBullet> enemyBullets) {
            this.img = img;
            //this.explosionImages = explosionImages;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.enemyType = enemyType;
            this.show = true;
            sizeX = img.getWidth(null);
            sizeY = img.getHeight(null);
            System.out.println("Enemytype:"+ enemyType +" is created");
            System.out.println("w:" + sizeX + " y:" + sizeY);
            this.m1 = m1;
            this.m2 = m2;
            this.gameEvents = gameEvents;
            this.bullets = bullets;
            this.enemyBullets = enemyBullets;
            this.explosions = explosions;
       }

        public void update() {
            if(enemyType == 1){
                y += speed; // basic enemy plane
            }else if(enemyType == 2){
                x += speed; // left enemy plane
            }else if(enemyType == 3){
                x -= speed; // right enemy plane
            }else if(enemyType == 4){
                y -= speed; // enemy plane from bottom
            }else if(enemyType == 5){
                y += speed; // enmey plane from up and fire bullets target to user planes
                if(y % 50 == 0){//enemy plane shoot bullet every 5 frame
                //create an instance of enemy bullet
                Image enemyBulletImage;
                try{
                    enemyBulletImage = ImageIO.read(new File("Resources/enemybullet1.png"));
                    EnemyBullet newEnemyBullet = new EnemyBullet(enemyBulletImage,1,5, x, y + 5,
                                                     this.m1, this.m2, this.explosions, this.gameEvents);
                    enemyBullets.add(newEnemyBullet);
                }catch(Exception e){
                    System.out.print("No resources are found");
                }
                }//end of inner if
            }else if(enemyType == 6){
                y += speed; // enmey plane from up and fire bullets target to user planes
                if(y % 50 == 0){//enemy plane shoot bullet every 5 frame
                //create an instance of enemy bullet
                Image enemyBulletImage;
                try{
                    enemyBulletImage = ImageIO.read(new File("Resources/enemybullet1.png"));
                    EnemyBullet newEnemyBullet = new EnemyBullet(enemyBulletImage,2,5, x, y + 5,
                                                     this.m1, this.m2, this.explosions, this.gameEvents);
                    enemyBullets.add(newEnemyBullet);
                }catch(Exception e){
                    System.out.print("No resources are found");
                }
                }//end of inner if
            }
            //if the enemy plane flys out of screee, destroy it
            if(y > 580|| y < -100 || x <-100 || x>740){
                //reset();
                show = false;
            }else{
            //if the enemy plane gets collisoin with the user plane(s), destroy it
            if(m1.collision(this.x, this.y, this.sizeX, this.sizeY)){
                //System.out.println(x + "" + y + "");
                show = false;
                // You need to remove this one and increase score etc
                //System.out.println("Enemy plane got explosion with plane 1");
                ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion1.wav");
                gameEvents.enemy_MyPlane_Event(1);
                //System.out.println("Create Explosion images");
                Explosions e = new Explosions(1,this.x,this.y);
                this.explosions.addAll(e.getExplosions());
            }
            if(m2.collision(this.x, this.y, this.sizeX, this.sizeY)){
                //System.out.println("x:"+ x + "y: " + y );
                show = false;
                //System.out.println("Enemy plane got explosion with plane 2");
                ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion1.wav");
                gameEvents.enemy_MyPlane_Event(2);
                //System.out.println("Create Explosion images");
                Explosions e = new Explosions(1,x,y - 20);
                this.explosions.addAll(e.getExplosions());
            }
            //now need to check if the enemy plane gets collision with a bullet
            //for(ListIterator<Bullet> iterator = bullets.listIterator(); iterator.hasNext();){
            for(int i = 0; i< bullets.size(); i++){
                Bullet b = bullets.get(i);
                if(b.collision(x,y,sizeX,sizeY)){
                    System.out.println("Killed by a bullet");
                    ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion1.wav");
                    show = false;
                    //iterator.remove();//destroy the bullet
                    bullets.remove(i);//remove the bullet
                    //System.out.println("Create explosion images");
                    Explosions e = new Explosions(1,b.x,b.y);
                    explosions.addAll(e.getExplosions());
                    gameEvents.enemy_MyBullet_Event();//report the event
                 }
            }//end of for loop
            }//end of else
        }

        public void draw(ImageObserver obs, Graphics2D g2) {
            //if (show) {
                //System.out.println("draw enemy");
                g2.drawImage(img, x, y, obs);
            //}
        }
 }
