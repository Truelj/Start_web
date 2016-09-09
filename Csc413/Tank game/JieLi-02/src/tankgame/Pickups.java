
package tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 *
 * Pickups are weapons that user tanks pick on the map
 * The behavior of Pickups is similar to Shell's
 */
public class Pickups {
    int tankType;//Actually is used to determine which tank uses it
    boolean pickup;//The most important variable. Used to determin whether the user has picked up the pickup weapons
    int pickupType;//used to determine which type of pickups the user tank has picked up
    Image currentImage;
    double x, y, speed;
    int direction; //used to choose image from 60 subimages
    int showTime;//user only has the limited time to pick up the weapon
    int range;//Same to shell, each pickup weapon also has limited range
    boolean show;//used to destroy the current pickup
    Random gen;//used to generate the position of the next pickup on the map
    Rectangle rectangle_shell;
    
    LinkedList<Shell> shell_opponent;//a list of opponent shells
    LinkedList<Explosion> explosions;
    GameEvents gameEvents;
    Tank tank1;
    Tank tank2;
    Tank tank_self;
    Tank tank_opponent;//Extra tank object to make code more clear
    public Pickups(int tankType, boolean pickup, int pickupType, int direction, double x, double y, double speed,
            LinkedList<Shell> shell_opponent, LinkedList<Explosion> explosions,
            GameEvents gameEvents, Tank tank1,Tank tank2){
        this.tankType = tankType;
        this.pickup = pickup;
        this.pickupType = pickupType;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.show = true;
        //now need to get the current image from direction
        try{
            if(!this.pickup){//if user has not picked up this weapon yet, set the pickup
                if(this.pickupType == 1){
                    currentImage = ImageIO.read(new File("resources/Pickup_1.png"));
                }else if(this.pickupType == 2){
                    currentImage = ImageIO.read(new File("resources/Pickup_2.png"));
                }else if(this.pickupType == 3){
                    currentImage = ImageIO.read(new File("resources/Pickup_3.png"));
                }else if(this.pickupType == 4){
                    currentImage = ImageIO.read(new File("resources/Pickup_4.png"));
                }
                this.showTime = 300; this.range = -1;
                this.tank1 = tank1; this.tank2 = tank2;
                this.gen = new Random(1234567);//create a random number generator
            }else{//or the user has already picked up this weapon
                this.showTime = -1; //this.range  = 50;
                this.tank_self = tank1; this.tank_opponent = tank2;
                int imageIndex;//used to get the image file
                if(direction >= 0){
                    imageIndex = (direction%360)/6 + 1;//starts from 1 to 60
                }else{
                    imageIndex = (-(-direction%360)/6 + 60)%60 + 1;//starts from 1 to 60
                }
                if(this.pickupType == 1){//Access the image of rockeet
                    range = 100;
                    if(imageIndex < 10){
                        currentImage = ImageIO.read(new File("resources/Rocket_strip60/Rocket_0"+ imageIndex +".png"));
                    }else{
                        currentImage = ImageIO.read(new File("resources/Rocket_strip60/Rocket_"+ imageIndex +".png"));
                    }
                }else if(this.pickupType == 2){//Access the image of bouncing bomb
                    range = 50;
                    if(imageIndex < 10){
                        currentImage = ImageIO.read(new File("resources/Bouncing_strip60/Bouncing_0"+ imageIndex +".png"));
                    }else{
                        currentImage = ImageIO.read(new File("resources/Bouncing_strip60/Bouncing_"+ imageIndex +".png"));
                    }
                }else if(this.pickupType == 3){//Access the image of sheild
                    range = 300;
                    currentImage = ImageIO.read(new File("resources/Pickup_3.png"));
                }
                //only initiate the "shell_opponent" when the weapon is picked up by the user tank
                this.shell_opponent = shell_opponent;
                //only initiate the "explosions" when the weapon is picked up by the user tank
                this.explosions = explosions;
            }
        }catch(Exception e) {
            System.out.print("TankGame -- No resources are found");
       }
        
        this.gameEvents = gameEvents;
    
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
    
    public void update(){
        //if the weapon is picked up, its "showTime" is -1
        //otherwise, its "showTime" is always greater or equal to 0
        if(this.showTime > 0){//Do the following test if showTime is greateer than 0
            //decrease the showTime
            this.showTime--;
            if(!this.pickup){//Do the following test if user has not picked up the weapon
                //check collision with tank1 first
                if(this.tank1.collision((int)x, (int)y, currentImage.getWidth(null), currentImage.getHeight(null))){
                    this.tankType = 1;//tank1 picks it up
                    //report the event
                    gameEvents.pickup_Event(this.tankType,this.pickupType);
                    showTime = 0;
                }
                //check collision with tank2 secondly. Don't check them together. This is the rule~
                else if(this.tank2.collision((int)x, (int)y, currentImage.getWidth(null), currentImage.getHeight(null))){
                    this.tankType = 2;//tank2 picks it up
                    //report the event
                    gameEvents.pickup_Event(this.tankType,this.pickupType);
                    showTime = 0;
                }
            }//end of if(!this.pickup)  
        }else if(this.showTime == 0){//once "showTime" is equal to 0
            //Whether the weapon is picked up or not, call "reset" method to set another pickup on the map
            reset();
        }else{
            //leave for showTime == -1
        }
        //If the weapn is pickedUp, its range is always greater or equal to 0
        //otherwise, its range is always set to -1
        if(this.range > 0){//Do the following test to see which type of weapon the user has picked up
            this.range--;
            if(this.pickupType == 1 || this.pickupType == 2){
                //update its position
                x = speed * Math.cos(Math.toRadians((double)direction)) + x;
                y = y - speed * Math.sin(Math.toRadians((double)direction));
                //check collision with the opponent tank
                if(tank_opponent.collision((int)x, (int)y, currentImage.getWidth(null), currentImage.getHeight(null))){
                    //System.out.println("pickup of type "+ pickupType + " collides with the opponent tank");
                    //create small explosion music
                    ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                    show = false;//used to destroy the current shell
                    range = 0;
                    //create small explosion
                    Explosions e = new Explosions(1,(int)this.x,(int)this.y);
                    explosions.addAll(e.getExplosions());
                    //report the event
                    gameEvents.pickup_tankEvent(this.tankType,this.pickupType);         
                }if(show){//Continue to check collision with the opponent's shell
                    for(int i = 0; i < shell_opponent.size(); i++){
                        if(shell_opponent.get(i).collision((int)this.x, (int)this.y, currentImage.getWidth(null), currentImage.getHeight(null))){
                            //System.out.println("pickup of type "+ pickupType + " collides with the opponent shell");
                            //create small explosion music
                            ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                            show  = false;//used to destroy the current shell
                            range = 0;
                            shell_opponent.remove(i);//remove the opponent shell
                            //create small explosion
                            Explosions e = new Explosions(1,(int)this.x,(int)this.y);
                            explosions.addAll(e.getExplosions());
                            //report the event? No
                        }
                    }//end of for loop
                }//end of if(show)
            }//end of if(this.pickupType == 1 || this.pickupType == 2)
            else if(this.pickupType == 3){//so the pickup is sheild
                //its position should be around tank_self
                this.x = this.tank_self.x;
                this.y = this.tank_self.y;
            }
            
                
        }else if(range == 0){
            show = false;//used to destroy the pickup
        }else {
            //leave for range == -1
        }
        
    }
    
    public void reset(){
        //reset the position of the pickup 
        this.x = 32 + Math.abs(gen.nextInt() % (1280 - 32));
        this.y = 32 + Math.abs(gen.nextInt() % (1280 - 32));
        showTime = 300;
        
    }
    public void draw(ImageObserver obs, Graphics2D g2) {
        //System.out.println("draw pickup");
        g2.drawImage(currentImage, (int)x, (int)y, obs);
    }
}

