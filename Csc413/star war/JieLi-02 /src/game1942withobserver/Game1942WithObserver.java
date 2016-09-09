/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import java.applet.*;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.ListIterator;
import java.applet.AudioClip;

/**
 *
 * @author lenovo
 */
public class Game1942WithObserver extends JApplet implements Runnable {

    private Thread thread;
    Image sea;
    Image myPlane1, myPlane2;
    Image island1, island2, island3, enemyImg;
    Image bossImg;
    Image bottom;
    
    //Create a list of bullets
    //Create a list of enemies
    LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    LinkedList<EnemyBullet> enemyBullets = new LinkedList<EnemyBullet>();
    LinkedList<Enemy> enemies = new LinkedList<Enemy>();
    LinkedList<Explosion> explosions = new LinkedList<Explosion>();
    Boss boss;
    private BufferedImage bimg;
    Graphics2D g2;
    int speed = 1, move = 0;
    Random generator = new Random(1234567);
    Island I1, I2, I3;
    MyPlane m1, m2;
    int w = 640, h = 480; // fixed size window game 
    //Enemy e1; 
    GameEvents gameEvents;
    TimeLine t;
    Controller controller;
    backGroundMusic bm;
    //Music2 music;
    
    public void init() {
        
        setBackground(Color.white);
        //music = new Music2("Resources/background.mid");
        bm = new backGroundMusic("Resources/background.mid");

        try {
        //sea = getSprite("Resources/water.png");
        sea = ImageIO.read(new File("Resources/water.png"));
        island1 = ImageIO.read(new File("Resources/island1.png"));
        island2 = ImageIO.read(new File("Resources/island2.png"));
        island3 = ImageIO.read(new File("Resources/island3.png"));
        myPlane1 = ImageIO.read(new File("Resources/myplane_1.png"));
        myPlane2 = ImageIO.read(new File("Resources/myplane_2.png"));
        enemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));
        bossImg = ImageIO.read(new File("Resources/boss.png"));
        bottom = ImageIO.read(new File("Resources/bottom.png"));

        I1 = new Island(island1, 100, 100, speed, generator);
        I2 = new Island(island2, 200, 400, speed, generator);
        I3 = new Island(island3, 300, 200, speed, generator);
        m1 = new MyPlane(myPlane1, 400, 320, 5, 1, bullets, explosions);
        m2 = new MyPlane(myPlane2, 150, 320, 5, 2, bullets, explosions);   

        gameEvents = new GameEvents();
        gameEvents.addObserver(m1);
        gameEvents.addObserver(m2);
        
        boss = new Boss(bossImg,1,200, -100,m1, m2, gameEvents,enemyBullets, bullets, explosions );
        
        t = new TimeLine(enemies,boss, m1, m2, gameEvents, bullets, explosions, enemyBullets);
        controller = new Controller(m1, m2, bottom);
        
        KeyControl key = new KeyControl(gameEvents);
        addKeyListener(key);
        setFocusable(true);
        }
        catch (Exception e) {
            System.out.print("No resources are found");
        }
    }
     public void drawBackGroundWithTileImage() {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth, 
                        i * TileHeight + (move % TileHeight), TileWidth, 
                        TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawDemo() {
        drawBackGroundWithTileImage();
        if((m1.health != 0 && m1.health != -5 && m2.health != 0 && m2.health != -5)&& boss.hits != 50){
            I1.update();
            I2.update();
            I3.update();
            t.update();
            //Update all enemies first!
            for(ListIterator<Enemy> iterator = enemies.listIterator(); iterator.hasNext();){
                //iterator.next().update();
                Enemy enemy = iterator.next();
                enemy.update();
                //check if enemy is died or out of screen. If it is, remove it.
                if(!enemy.show){
                    iterator.remove();
                }
            }
            //Then update all bullets
            for(ListIterator<Bullet> iterator = bullets.listIterator(); iterator.hasNext();){
                //iterator.next().update();
                Bullet bullet = iterator.next();
                bullet.update();
                //check if bullet is out of screen. If it is, remove it.
                if(!bullet.show){
                    iterator.remove();
                }
            }//end of for loop
            //update all enemyBullets
            for(ListIterator<EnemyBullet> iterator = enemyBullets.listIterator(); iterator.hasNext();){
                //iterator.next().update();
                EnemyBullet enemyBullet = iterator.next();
                enemyBullet.update();
                //check if bullet is out of screen. If it is, remove it.
                if(!enemyBullet.show){
                    iterator.remove();
                }
            }//end of for loop
         }//end of outer if
            
            I1.draw(this, g2);
            I2.draw(this, g2);
            I3.draw(this, g2);
            m1.draw(this, g2);
            m2.draw(this, g2);   
            //Draw all enemies
            for(ListIterator<Enemy> iterator = enemies.listIterator(); iterator.hasNext();){
                iterator.next().draw(this, g2);
            }
            //Draw all bullets
            for(ListIterator<Bullet> iterator = bullets.listIterator(); iterator.hasNext();){
                iterator.next().draw(this, g2);
            }
            //Draw all enemy bullets
            for(ListIterator<EnemyBullet> iterator = enemyBullets.listIterator(); iterator.hasNext();){
                iterator.next().draw(this, g2);
            }
            //Draw all explosions
            for(ListIterator<Explosion> iterator = explosions.listIterator(); iterator.hasNext();){
                iterator.next().draw(this, g2);
                iterator.remove();
            }
            //draw boss
            boss.draw(this, g2);
            controller.draw(this, g2);

        if(m1.health == -5 ||m1.health == 0|| m2.health == -5|| m2.health == 0){
            bm.end();
            thread.interrupt(); 
        }else if(boss.hits == 50){//win
            bm.end();
            //try{
                //Image youWinImage = ImageIO.read(new File("Resources/youWin.png"));
                //g2.drawImage(youWinImage,190,140,this);
            //}catch(Exception e){
                //System.out.print("No resources are found");
            //}
            thread.interrupt();
        }
   }


    public void paint(Graphics g) {
        if(bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width, 
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        //drawDemo();
        g.drawImage(bimg, 0, 0, this);
        drawDemo();
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        
    }
    public void playBackgroundMusic(){
        //AudioClip ac  = getAudioClip(this.getDocumentBase(), "Resources/background.mid");
        //ac.play();
        backGroundMusic m = new backGroundMusic("Resources/background.wav");
        m.play();
    }

    public void run() {
    	
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();  
          try {
                thread.sleep(35);
            } catch (InterruptedException e) {
                break;
            }
            
        }
    }

    public static void main(String argv[]) {
        final Game1942WithObserver demo = new Game1942WithObserver();
        demo.init();
        JFrame f = new JFrame("Scrolling Shooter");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }

}







