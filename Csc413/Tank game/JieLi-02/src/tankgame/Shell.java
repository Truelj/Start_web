/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author lenovo
 */
public class Shell {
    //Image[] img;
    Image currentImage;
    double x, y, speed;
    int direction; //used to choose image
    int range;//limited range
    boolean show;//used to destroy the shell
    Rectangle rectangle_shell;
    int type;//Very Important! Type is decided by the tank; used for check collision
    
    LinkedList<Shell> shell_opponent;//a list of opponent shells
    LinkedList<Explosion> explosions;
    GameEvents gameEvents;//used to report the event
    Tank tank_opponent;
    
    public Shell(int type, int direction, double x, double y, double speed,
            LinkedList<Shell> shell_opponent, LinkedList<Explosion> explosions,
            GameEvents gameEvents, Tank tank_opponent){
        this.type = type;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.range = 30;//set the initial range that shells is useful to be 30 frames
        this.show = true;
        //now need to get the current image from direction
        int imageIndex;//used to access the image file
        if(direction >= 0){
            imageIndex = (direction%360)/6 + 1;//starts from 1 to 60
        }else{
            imageIndex = (-(-direction%360)/6 + 60)%60 + 1;//starts from 1 to 60
        }
        try{
            if(imageIndex < 10){
                currentImage = ImageIO.read(new File("resources/Shell_basic_strip60/Shell_basic_0"+ imageIndex +".png"));
            }else{
                currentImage = ImageIO.read(new File("resources/Shell_basic_strip60/Shell_basic_"+ imageIndex +".png"));
            }
       }catch(Exception e) {
            System.out.print("TankGame -- No resources are found");
       }//end of accessing image file
        
        this.shell_opponent = shell_opponent;//get the list of opponent's shells
        this.explosions = explosions;
        this.gameEvents = gameEvents;
        this.tank_opponent = tank_opponent;//get an instance of the opponent tank
    }
    
    //method collision is called by other object to check collision with it
    public boolean collision(int x, int y, int w, int h) {
            //create a rectangle for tank
            rectangle_shell = new Rectangle((int)this.x, (int)this.y, currentImage.getWidth(null), currentImage.getHeight(null));
            //create a rectangle for the other object
            Rectangle rectangle_other = new Rectangle (x,y,w,h);
            if(this.rectangle_shell.intersects(rectangle_other)) { 
                return true;
            }
            return false;
    }
    
    //now we need to update the shell
    public void update(){
        if(range > 0){//do the following test if range > 0
            //update position
            x = speed * Math.cos(Math.toRadians((double)direction)) + x;
            y = y - speed * Math.sin(Math.toRadians((double)direction));
            //check collision with the opponent tank
            if(tank_opponent.collision((int)x, (int)y, currentImage.getWidth(null), currentImage.getHeight(null))){
                    System.out.println("shell of type "+ type + " collides with the opponent tank");
                    //create small explosion music
                    ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                    show = false;//used to destroy the current shell
                    //create small explosion
                    Explosions e = new Explosions(1,(int)this.x,(int)this.y);
                    explosions.addAll(e.getExplosions());
                    //report the event
                    gameEvents.shell_tankEvent(type);         
            }
            if(show){//Continue to check collision with the opponent's shell
                for(int i = 0; i < shell_opponent.size(); i++){
                    if(shell_opponent.get(i).collision((int)this.x, (int)this.y, currentImage.getWidth(null), currentImage.getHeight(null))){
                        System.out.println("shell of type "+ type + " collides with the opponent shell");
                        //create small explosion music
                        show  = false;//used to destroy the current shell
                        ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                        shell_opponent.remove(i);//remove the opponent shell
                        //create small explosion
                        Explosions e = new Explosions(1,(int)this.x,(int)this.y);
                        explosions.addAll(e.getExplosions());
                        //report the event
                        //gameEvents.shell_shellEvent(type);Maybe not

                    }
                }//end of for loop
            }//end of outer if(show)
            
            range--;
            
        }else{//if range <= 0
            show = false;//later it will be removed from the list
        }
    }
    
    public void draw(ImageObserver obs, Graphics2D g2) {
        if(show){
            System.out.println("draw shell");
            g2.drawImage(currentImage, (int)x, (int)y, obs);
        }
    }
    
    
}
