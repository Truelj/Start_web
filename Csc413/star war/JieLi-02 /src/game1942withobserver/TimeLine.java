
package game1942withobserver;
import java.util.LinkedList;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
/*
 * TimeLine is used to create a wave of enemies according to the frame number
 * An instance of Timeline should be declared inside the init() method
 */
public class TimeLine {
    static int frame = 0;
    //whenever a wave of enemies are created, they should be added to the list.
    LinkedList<Enemy> enemies;
    LinkedList<Explosion> explosions;
    LinkedList<Bullet> bullets;
    Boss boss;//
    Image enemy1Image;
    Image enemy2Image;
    Image enemy3Image;
    Image enemy4Image;
    MyPlane m1, m2;
    GameEvents gameEvents;
    LinkedList<EnemyBullet> enemyBullets;
    
    
    TimeLine(LinkedList<Enemy> enemies,Boss boss,MyPlane m1, MyPlane m2, GameEvents gameEvents, 
            LinkedList<Bullet> bullets, LinkedList<Explosion> explosions, LinkedList<EnemyBullet> enemyBullets){
        this.enemies = enemies;
        this.boss = boss;
        try{
            this.enemy1Image = ImageIO.read(new File("Resources/enemy1_1.png"));
            this.enemy2Image = ImageIO.read(new File("Resources/enemy2_1.png"));
            this.enemy3Image = ImageIO.read(new File("Resources/enemy3_1.png"));
            this.enemy4Image = ImageIO.read(new File("Resources/enemy4_1.png"));
        }catch(Exception e){
            
        }
        this.m1 = m1;
        this.m2 = m2;
        this.gameEvents = gameEvents;
        this.bullets = bullets;
        this.explosions = explosions;
        this.enemyBullets = enemyBullets;
    }
    // update method should be called inside the drawDemo() method
    void update(){
        frame++;
        //create the frist wave of enemies
        if(frame == 60){//create a wave of enemy planes of type 1
           enemies.add(new Enemy(enemy1Image,4,0, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,100, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,200, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,300, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,400, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,500, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,600, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 200){//create a wave of enemy planes coming from left or left
           enemies.add(new Enemy(enemy2Image,4,-25,240,2, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 220){
           enemies.add(new Enemy(enemy2Image,4,-50,240,2, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 240){
           enemies.add(new Enemy(enemy2Image,4,-75,240,2, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 260){
           enemies.add(new Enemy(enemy2Image,4,-100,240,2, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 280){//create a wave of enemy planes coming from left or left
           enemies.add(new Enemy(enemy2Image,4,665,240,3, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 300){
           enemies.add(new Enemy(enemy2Image,4,690,240,3, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 320){
           enemies.add(new Enemy(enemy2Image,4,715,240,3, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 330){
           enemies.add(new Enemy(enemy2Image,4,740,240,3, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 350){//create a wave of enemy planes coming from bottom
           enemies.add(new Enemy(enemy3Image,4,300,505,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           //enemies.add(new Enemy(enemy3Image,4,250,505,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           //enemies.add(new Enemy(enemy3Image,4,450,505,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 365){
           enemies.add(new Enemy(enemy3Image,4,300,515,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           //enemies.add(new Enemy(enemy3Image,4,250,530,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           //enemies.add(new Enemy(enemy3Image,4,450,530,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 380){
           enemies.add(new Enemy(enemy3Image,4,300,525,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           //enemies.add(new Enemy(enemy3Image,4,250,530,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           //enemies.add(new Enemy(enemy3Image,4,450,530,4, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 450){//create a wave of enemy planes shooting from up          
           enemies.add(new Enemy(enemy4Image,2,200,-20,5, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy4Image,2,450,-20,5, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 600){//create a wave of enemy planes shooting diagonal bullets from up
           enemies.add(new Enemy(enemy4Image,2,50,-20,6, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy4Image,2,100,-20,6, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy4Image,2,500,-20,6, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy4Image,2,550,-20,6, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }else if(frame == 700){//create a wave of enemy planes of type 1
           enemies.add(new Enemy(enemy1Image,4,200, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
           enemies.add(new Enemy(enemy1Image,4,400, -20,1, m1, m2, gameEvents, bullets, explosions, enemyBullets));
        }
        if(frame > 650){
               boss.update();
        }
    }
}