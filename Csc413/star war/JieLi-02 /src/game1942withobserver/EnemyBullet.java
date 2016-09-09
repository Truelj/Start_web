package game1942withobserver;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
/**
 *
 * @author lenovo
 */
public class EnemyBullet {
    Image img;
    int type;
    int Enemy_x;
    int x, y, sizeX, sizeY, speed;
    boolean show;
    MyPlane m1, m2;    
    GameEvents gameEvents;
    LinkedList<Explosion> explosions;
    public EnemyBullet(Image img, int type,int speed, int x, int y, MyPlane m1, MyPlane m2,
            LinkedList<Explosion> explosions,GameEvents gameEvents){
        this.img = img;
        this.type = type;//diagonal bullet or downward bullet
        this.speed = speed;
        this.Enemy_x = x;//used to determine right or left diagonal 
        this.x = x;
        System.out.println("x;"+ x);
        this.y = y;
        System.out.println("y;"+ y);
        this.show = true;
        this.m1 = m1;
        this.m2 = m2;
        this.gameEvents = gameEvents;
        this.explosions = explosions;
        sizeX = img.getWidth(null);
        sizeY = img.getHeight(null);
        System.out.println("w:" + sizeX + " y:" + sizeY);
    }
    void update(){
        if(this.type == 1){// flying downward
            y += speed;
        }else if(this.type == 2){// flying in diagonal 
            
            if(this.Enemy_x > 240){
                x -= speed;
                y += speed;
            }else if( this.Enemy_x <= 240){
                 x += speed;
                 y += speed;
           }
        }//end of else if
        if (x < -100 || y > 580|| x > 740){// check if it is out of screen
            show = false;
        }
        if(m1.collision(this.x, this.y, this.sizeX, this.sizeY)){//check collision with plane1
           //System.out.println(x + "" + y + "");
           show = false;
           //Create explosion music
           ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion1.wav");
           gameEvents.enemyBulletEvent(1);//report the event
           System.out.println("Create Explosion images");
           Explosions e = new Explosions(1,this.x,this.y);
           this.explosions.addAll(e.getExplosions());
        }
        if(m2.collision(this.x, this.y, this.sizeX, this.sizeY)){//check collisoin with plane2
           //System.out.println("x:"+ x + "y: " + y );
           show = false;
           ExplosionMusic em = new ExplosionMusic("Resources/snd_explosion1.wav");
           gameEvents.enemyBulletEvent(2);//report the event
           System.out.println("Create Explosion images");
           Explosions e = new Explosions(1,x,y);
           this.explosions.addAll(e.getExplosions());
         }
    }
    
    public void draw(ImageObserver obs, Graphics2D g2) {
                g2.drawImage(img, x, y, obs);
    }
}
