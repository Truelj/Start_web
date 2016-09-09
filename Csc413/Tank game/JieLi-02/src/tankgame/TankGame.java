/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author lenovo
 */
public class TankGame extends JApplet implements Runnable {
    Image background;
    Image[] tank1Images = new Image[60];
    Image[] tank2Images = new Image[60];
    Image[] shellImages = new Image[60];
    Tank tank1, tank2;
    Graphics2D g2;
    Thread thread;
    private BufferedImage bimg;
    LinkedList<Wall> wall_1 = new LinkedList<Wall>();
    LinkedList<Wall> wall_2 = new LinkedList<Wall>();
    LinkedList<Shell> shell_1 = new LinkedList<Shell>();//a list of shells for tank1
    LinkedList<Shell> shell_2 = new LinkedList<Shell>();//a list of shells for tank2
    LinkedList<Explosion> explosions = new LinkedList<Explosion>();
    LinkedList<Tank> tanks = new LinkedList<Tank>();
    LinkedList<Pickups> pickups = new LinkedList<Pickups>();
    LinkedList<Pickups> pickups_1 = new LinkedList<Pickups>();
    LinkedList<Pickups> pickups_2 = new LinkedList<Pickups>();
    
    BackgroundMusic bm;
    Controller controller;
    
    public void init(){
        setBackground(Color.white);
        bm = new BackgroundMusic("resources/Music.mid");
        try{
            background = ImageIO.read(new File("resources/Background.png"));
            //Read tank images from local file
            for(int i = 1; i <= tank1Images.length; i++){
                if(i<10){
                    tank1Images[i-1] = ImageIO.read(new File("resources/Tank_red_basic_strip60/Tank_red_basic_0"+ i +".png"));
                    tank2Images[i-1] = ImageIO.read(new File("resources/Tank_blue_basic_strip60/Tank_blue_basic_0"+ i +".png"));
                }else{
                    tank1Images[i-1] = ImageIO.read(new File("resources/Tank_red_basic_strip60/Tank_red_basic_"+ i +".png"));
                    tank2Images[i-1] = ImageIO.read(new File("resources/Tank_blue_basic_strip60/Tank_blue_basic_"+ i +".png"));
                }    
            }
            GameEvents gameEvents  = new GameEvents();
            //create two user tanks
            tank1 = new Tank(tank1Images,1,160,190,4,shell_1, shell_2, explosions,gameEvents, tanks, pickups_1);
            tanks.add(tank1);
            //tank2 = new Tank(tank2Images,2,1120,1040,4,shell_1, shell_2, explosions,gameEvents, tanks, pickups_2);
            tank2 = new Tank(tank2Images,2,160,300,4,shell_1, shell_2, explosions,gameEvents, tanks, pickups_2);
            tanks.add(tank2);
            
            controller = new Controller(tank1, tank2);
            
            gameEvents.addObserver(tank1);
            gameEvents.addObserver(tank2);
            
            pickups.add(new Pickups(0,false,2,0,100.0,100.0,8,null,null,gameEvents,tank1,tank2));
            pickups.add(new Pickups(0,false,1,0,300.0,300.0,8,null,null,gameEvents,tank1,tank2));
            pickups.add(new Pickups(0,false,2,0,500.0,500.0,8,null,null,gameEvents,tank1,tank2));
            pickups.add(new Pickups(0,false,1,0,900.0,900.0,8,null,null,gameEvents,tank1,tank2));
            
            MultipleKeyControl keyControl = new MultipleKeyControl(gameEvents);
            addKeyListener(keyControl);
            setFocusable(true);
            
            //create walls
            SourceReader s = new SourceReader("wall.txt");
            String string = s.readLine();
            int stringLength;
            int line = 1;
            while(string != null){//test if the current line is null
                stringLength = string.length();//get the length of the current line
                int i = 0;//start position is 0
                do{
                   char c = string.charAt(i);//get the next character
                   if( c == '1'){
                       wall_1.add(new Wall(1, i * 32, (line - 1) * 32, tank1, tank2, gameEvents, shell_1, shell_2, pickups_1,pickups_2,explosions));
                   }else if(c == '2'){
                       wall_2.add(new Wall(2, i * 32, (line - 1) * 32, tank1, tank2, gameEvents, shell_1, shell_2, pickups_1,pickups_2,explosions));
                   }
                   i++;
                }while(i < stringLength);
                System.out.println("finish reading " + line + " line");
                
                string = s.readLine();//read the next line
                line++;//increasing line number
            }
        }catch(Exception e) {
            System.out.print("TankGame -- No resources are found");
        }
        
    }
    public void drawBackGroundWithTileImage() {
        int TileWidth = background.getWidth(this);
        int TileHeight = background.getHeight(this);

        int NumberX = (int) (1280 / TileWidth);
        int NumberY = (int) (1280 / TileHeight);
        System.out.println(TileWidth);//320
        System.out.println(TileHeight);//240
        
        for (int i = 0; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(background, j * TileWidth, 
                        i * TileHeight, TileWidth, 
                        TileHeight, this);
            }
        }
    }
    /*
     * drawDemo() is used to update and draw all objects
     */
    public void drawDemo() {
        drawBackGroundWithTileImage();//draw the background
        //update wall of type 1
        for(ListIterator<Wall> iterator = wall_1.listIterator(); iterator.hasNext();){
            Wall w = iterator.next();
            w.update();
            if(!w.show){
                iterator.remove();
            }
        }
        //update wall of type 2
        for(ListIterator<Wall> iterator = wall_2.listIterator(); iterator.hasNext();){
            iterator.next().update();
        }
        //update shell of type 1
        for(int i = 0; i < shell_1.size(); i++){
            Shell s = shell_1.get(i);
            s.update();
            if(!s.show){
                shell_1.remove(i);
            }
        }
        //update shell of type 2
        for(int i = 0; i < shell_2.size(); i++){
            Shell s = shell_2.get(i);
            s.update();
            if(!s.show){
                shell_2.remove(i);
            }
        }
        //update pickups appearing on the map
        for(int i = 0; i < pickups.size(); i++){
            pickups.get(i).update();
        }
        //update pickups_1
        for(int i = 0; i < pickups_1.size(); i++){
            Pickups p = pickups_1.get(i);
            p.update();
            if(!p.show){
                pickups_1.remove(i);
            }
        }
        //update pickups_2
        for(int i = 0; i < pickups_2.size(); i++){
            Pickups p = pickups_2.get(i);
            p.update();
            if(!p.show){
                pickups_2.remove(i);
            }
        }
        
        //draw wall of type 1
        for(ListIterator<Wall> iterator = wall_1.listIterator(); iterator.hasNext();){
            iterator.next().draw(this, g2);
        }
        //draw wall of type 2
        for(ListIterator<Wall> iterator = wall_2.listIterator(); iterator.hasNext();){
            iterator.next().draw(this, g2);
        }
        //draw shell of type 1
        for(int i = 0; i < shell_1.size(); i++){
            shell_1.get(i).draw(this, g2);
        }
        //draw shell of type 2
        for(int i = 0; i < shell_2.size(); i++){
            shell_2.get(i).draw(this, g2);
        }
        //draw all pickups -- pickups appearing on the map, pickups_1, pickups_2
        for(ListIterator<Pickups> iterator = pickups.listIterator(); iterator.hasNext();){
            iterator.next().draw(this, g2);
        }
        for(int i = 0; i < pickups_1.size(); i++){
            pickups_1.get(i).draw(this, g2);
        }
        for(int i = 0; i < pickups_2.size(); i++){
            pickups_2.get(i).draw(this, g2);
        }
        //draw all explosions
        for(ListIterator<Explosion> iterator = explosions.listIterator(); iterator.hasNext();){
                iterator.next().draw(this, g2);
                iterator.remove();
        }
        
        if(tank1.show){
            tank1.draw(this, g2);
        }else{
            bm.end();
            thread.interrupt();
        }
        if(tank2.show){
            tank2.draw(this, g2);
        }else{
            bm.end();
            thread.interrupt();
        }
    }
     public void paint(Graphics g) {
        BufferedImage realImg = (BufferedImage) createImage(1280, 1280);
        g2 = realImg.createGraphics();//get the drawing surface created by realImg  
        drawDemo();//off screen drawing -> draw images into realImg
        //Now we need to draw three views: left view, right view and mini map view.
        
        Dimension windowSize = getSize();
        int windowWidth = windowSize.width, windowHeight = windowSize.height;
        int viewWidth = windowWidth / 2 -1, viewHeight =windowHeight;
        bimg = (BufferedImage) createImage(windowWidth, windowHeight);
        //System.out.println("window.width:"+ windowSize.width);//634
        //System.out.println("window.height:"+ windowSize.height);//451
        Graphics2D g2d = bimg.createGraphics();
        //now need to draw the right view
        int x1 = (int)tank1.x, y1 = (int)tank1.y;
        if(x1 <= 158){
            if(y1 <= 225){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, 0, 0, viewWidth, viewHeight, this);
            }else if(y1 >= 1055){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, 0, 1280 - viewHeight, viewWidth, 1280, this);
            }else if(y1 > 225 && y1 < 1055){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, 0, y1 - viewHeight/2, viewWidth, y1 + viewHeight/2, this);
            }
        }else if(x1 >= 1122){
            if(y1 <= 225){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, 1280 - viewWidth, 0, 1280, viewHeight, this);
            }else if(y1 >= 1055){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, 1280 - viewWidth, 1280 - viewHeight, 1280, 1280, this);
            }else if(y1 > 225 && y1 < 1055){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, 1280 - viewWidth, y1 - viewHeight/2, 1280, y1 + viewHeight/2, this);
            }
        }else if(x1 > 158 && x1 < 1122){
            if(y1 <= 225){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, x1 - viewWidth/2, 0, x1 + viewWidth/2, viewHeight, this);
            }else if(y1 >= 1055){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, x1 - viewWidth/2, 1280 - viewHeight, x1 + viewWidth/2, 1280, this);
            }else if(y1 > 225 && y1 < 1055){
                g2d.drawImage(realImg, 0, 0, viewWidth-1, viewHeight, x1 - viewWidth/2, y1 - viewHeight/2, x1 + viewWidth/2, y1 + viewHeight/2, this);
            }
        }
        //now need to draw the right view
        int x2 = (int)tank2.x, y2 = (int)tank2.y;
        if(x2 <= 158){
            if(y2 <= 225){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, 0, 0, viewWidth, viewHeight, this);
            }else if(y2 >= 1055){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, 0, 1280 - viewHeight, viewWidth, 1280, this);
            }else if(y2 > 225 && y2 < 1055){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, 0, y2 - viewHeight/2, viewWidth, y2 + viewHeight/2, this);
            }
        }else if(x2 >= 1122){
            if(y2 <= 225){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, 1280 - viewWidth, 0, 1280, viewHeight, this);
            }else if(y2 >= 1055){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, 1280 - viewWidth, 1280 - viewHeight, 1280, 1280, this);
            }else if(y2 > 225 && y2 < 1055){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, 1280 - viewWidth, y2 - viewHeight/2, 1280, y2 + viewHeight/2, this);
            }
        }else if(x2 > 158 && x2 < 1122){
            if(y2 <= 225){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, x2 - viewWidth/2, 0, x2 + viewWidth/2, viewHeight, this);
            }else if(y2 >= 1055){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, x2 - viewWidth/2, 1280 - viewHeight, x2 + viewWidth/2, 1280, this);
            }else if(y2 > 225 && y2 < 1055){
                g2d.drawImage(realImg, viewWidth+1, 0, windowWidth, windowHeight, x2 - viewWidth/2, y2 - viewHeight/2, x2 + viewWidth/2, y2 + viewHeight/2, this);
            }
        }
        g2d.drawImage(realImg.getScaledInstance(160, 160, Image.SCALE_SMOOTH), windowSize.width / 2 - 80, windowSize.height - 160, this);//draw mini map
        controller.draw(this, g2d);
        g.drawImage(bimg, 0, 0, this);//finally, use g to draw bimg onto screen
        
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        
    }
     public void run() {
    	
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();  
          try {
                thread.sleep(40);
            } catch (InterruptedException e) {
                break;
            }
            
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        final TankGame demo = new TankGame();
        demo.init();
        JFrame f = new JFrame("Tank game");
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
