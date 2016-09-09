package tankgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 *This class is used to create Tank object
 *Tank object is controlled by two users
 */
public class Tank implements Observer{
    Image[] imgs;
    Image currentImage;
    double x, y, speed;
    int direction;
    int userNumber;
    int health, score;
    Rectangle rectangle_t;
    private boolean forward = false;
    private boolean backward = false;
    boolean pickup;
    int newPickupType;
    int oldPickupType;
    int ammunition;//the number of pickups it can fire
    boolean show = true;
    
    LinkedList<Shell> shell_1;
    LinkedList<Shell> shell_2;
    LinkedList<Explosion> explosions;
    LinkedList<Pickups> pickups;//used to store the pickups sent by the current tank
    
    GameEvents gameEvents;
    LinkedList<Tank> tanks;
    public Tank(Image[] imgs, int userNumber,int x, int y, int speed,
            LinkedList<Shell> shell_1, LinkedList<Shell> shell_2,
            LinkedList<Explosion> explosions, GameEvents gameEvents,
            LinkedList<Tank> tanks, LinkedList<Pickups> pickups){
        this.imgs = imgs;
        currentImage = imgs[0];//set the initial image as imgs[0]
        this.userNumber = userNumber;//set the user number
        this.health = 50;this.score = 0;
        this.pickup = false;
        this.oldPickupType = 0;this.newPickupType = 0;
        //set the initial position
        this.x = (double)x;
        this.y = (double)y;
        this.speed = (double)speed;//set the initial speed
        direction = 0;//set the initial direction
        
        this.shell_1 = shell_1;
        this.shell_2 = shell_2;
        this.explosions = explosions;
        this.gameEvents = gameEvents;
        this.tanks = tanks;
        this.pickups = pickups;
    }
    //method collision is called by other object to check collision with it
    public boolean collision(int x, int y, int w, int h) {
            //create a rectangle for tank
            rectangle_t = new Rectangle((int)this.x +6, (int)this.y +6, currentImage.getWidth(null)-10, currentImage.getHeight(null)-10);
            //create a rectangle for the other object
            Rectangle rectangle_other = new Rectangle (x,y,w,h);
            if(this.rectangle_t.intersects(rectangle_other)) { 
                return true;
            }
            return false;
    }
    @Override
    public void update(Observable obj, Object arg){
        //first, tank needs to update its position and the subimage
        GameEvents gameEvents = (GameEvents) arg;//get game events
        LinkedList<Integer> keys = gameEvents.keys;//get the list "keys"
        if(keys != null){//check if "keys" has no keys
            for(int i = 0; i< keys.size(); i++){
                if(userNumber == 1){
                    //update its behavior according to keys
                    switch(keys.get(i).intValue()){
                        case KeyEvent.VK_A:
                            //rotate the tank anticlockwise
                            direction += 6; 
                            if(direction >= 0){
                                currentImage = imgs[(direction%360)/6];
                            }else{
                                currentImage = imgs[(-(-direction%360)/6 + 60)%60];
                            }
                            break;
                        case KeyEvent.VK_D:
                            //rotate the tank clockwise
                            direction -= 6;
                            if(direction >= 0){
                                currentImage = imgs[(direction%360)/6];
                            }else{
                                currentImage = imgs[(-(-direction%360)/6 + 60)%60];
                            }
                           
                            break;
                        case KeyEvent.VK_W:
                            //move forward
                            //System.out.println("move forward: x: "+ x+" y: "+ y);
                            //System.out.println("direction:"+ direction);
                            //System.out.println("speed: "+ speed);
                            //System.out.println("speed * Math.cos((double)direction/180.0):" + Math.cos(Math.toRadians((double)direction)));
                            //System.out.println("speed * Math.sin((double)direction/180.0):" + Math.sin(Math.toRadians((double)direction)));
                            x = speed * Math.cos(Math.toRadians((double)direction)) + x;
                            y = y - speed * Math.sin(Math.toRadians((double)direction));
                            forward = true; backward = false;
                            break;
                        case KeyEvent.VK_S:
                            //move backward
                            x = -speed * Math.cos(Math.toRadians((double)direction)) + x;
                            y = speed * Math.sin(Math.toRadians((double)direction)) + y;
                            backward = true; forward = false;
                            break;
                        case KeyEvent.VK_SPACE:
                            //fire a shell
                            double shell_x  = this.x + currentImage.getWidth(null)/2.0;
                            double shell_y = this.y + currentImage.getHeight(null)/2.0;
                            Shell s1 = new Shell(1, direction, shell_x, shell_y, 16, shell_2, explosions, gameEvents, tanks.get(1));
                            shell_1.add(s1);//add shell to the list
                            break;
                        case KeyEvent.VK_CONTROL:
                            if(!this.pickup){
                                //do nothing, since it does not have any pickup weapon
                            }else{
                                if(this.newPickupType < 4){
                                    if(this.ammunition > 0){
                                        //create an instance of Pickup
                                        Pickups p = new Pickups(1,true,newPickupType,direction,x,y,8.0,shell_2,explosions,gameEvents,tanks.get(0),tanks.get(1) );
                                        //add it to the "pickups" list
                                        pickups.add(p);
                                        this.ammunition--;
                                    }
                                }
                            }
                        default:                          
                    }//end of switch block
                }//end of if
                else if(userNumber == 2){
                    //update its behavior according to keys
                    switch(keys.get(i).intValue()){
                        case KeyEvent.VK_LEFT:
                            //rotate the tank anticlockwise
                            direction += 6; 
                            if(direction >= 0){
                                currentImage = imgs[(direction%360)/6];
                            }else{
                                currentImage = imgs[(-(-direction%360)/6 + 60)%60];
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            //rotate the tank clockwise
                            direction -= 6.0;
                            if(direction >= 0){
                                currentImage = imgs[(direction%360)/6];
                            }else{
                                currentImage = imgs[(-(-direction%360)/6 + 60)%60];
                            }
                            break;
                        case KeyEvent.VK_UP:
                            //move forward
                            x = speed * Math.cos(Math.toRadians((double)direction)) + x;
                            y = y - speed * Math.sin(Math.toRadians((double)direction));
                            forward = true; backward = false;
                            break;
                        case KeyEvent.VK_DOWN:
                            //move backward
                            x = -speed * Math.cos(Math.toRadians((double)direction)) + x;
                            y = speed * Math.sin(Math.toRadians((double)direction)) + y;
                            backward = true; forward = false;
                            break;
                        case KeyEvent.VK_ENTER:
                            //fire shell
                            double shell_x  = this.x + currentImage.getWidth(null)/2.0;
                            double shell_y = this.y + currentImage.getHeight(null)/2.0;
                            Shell s2 = new Shell(2, direction, shell_x, shell_y, 16, shell_1, explosions, gameEvents, tanks.get(0));
                            shell_2.add(s2);
                            break;
                        case KeyEvent.VK_DELETE:
                            if(!this.pickup){
                                //do nothing, since it does not have any pickup weapon
                            }else{
                                if(this.newPickupType < 4){
                                    if(this.ammunition > 0){
                                        //create an instance of Pickup
                                        Pickups p = new Pickups(2,true,newPickupType,direction,x,y,8.0,shell_1,explosions,gameEvents,tanks.get(1),tanks.get(0) );
                                        //add it to the "pickups" list
                                        pickups.add(p);
                                        this.ammunition--;
                                    }
                                }
                            }
                            
                        default:
                            
                    }//end of switch block
                }//end of else if
            }//end of for
            
        }//end of outer if
        
        //Now we need to check for collisions
        if(userNumber == 1){
            //check for collision with walls
            if(gameEvents.wall_tank1){
                if(forward){
                   //should move backward 
                    x = -speed * Math.cos(Math.toRadians((double)direction)) + x;
                    y = speed * Math.sin(Math.toRadians((double)direction)) + y;
                    //forward = false; backward = true;
                }else{
                    //should move forward
                    x = speed * Math.cos(Math.toRadians((double)direction)) + x;
                    y = y - speed * Math.sin(Math.toRadians((double)direction));
                    //backward = false; forward = true;
                }
            }
            else if(gameEvents.shell2_tank1){//check if shell2 collides with tank1
                this.health -= 5;
            }
            else if(gameEvents.pickup_tank1){//check to see if tank1 picks up any weapons
                this.pickup = true;//okay, it picks up some weapon
                this.oldPickupType = this.newPickupType;
                this.ammunition = 10;
                if(gameEvents.pickup_rocket){
                      this.newPickupType = 1;             
                }else if(gameEvents.pickup_bouncingbomb){
                      this.newPickupType = 2;          
                }else if(gameEvents.pickup_sheild){
                      this.newPickupType = 3;
                      this.ammunition = 1;
                }else if(gameEvents.pickup_toolbox){
                      this.newPickupType = 4;
                      if(health < 25){
                          health += 25;
                      }else{
                          health = 50;
                      }
                }
            }
            //check to see if the opponent pickup weapons collides with tank1
            else if(gameEvents.rocket_tank1 || gameEvents.bouncingbomb_tank1){
                this.health -= 5;
            }
            //check to see if the pickup weapons collides with the opponent tank
            else if(gameEvents.rocket_tank2 || gameEvents.bouncingbomb_tank2){
                this.score += 5;
            }
            //check to see if the bouncing bomb collides with wall of type 1
            else if(gameEvents.bouncingbomb1_wall1){
                this.score += 1;
            }

        }else if(userNumber == 2){
            //check for collision with walls
            if(gameEvents.wall_tank2){
                if(forward){
                   //should move backward 
                    x = -speed * Math.cos(Math.toRadians((double)direction)) + x;
                    y = speed * Math.sin(Math.toRadians((double)direction)) + y;
                    //forward = false; backward = true;
                }else{
                    //should move forward
                    x = speed * Math.cos(Math.toRadians((double)direction)) + x;
                    y = y - speed * Math.sin(Math.toRadians((double)direction));
                    //backward = false; forward = true;
                }
            }
            else if(gameEvents.shell1_tank2){//check if shell1 collides with tank2
                this.health -= 5;
            }
            else if(gameEvents.pickup_tank2){//check to see if tank1 picks up any new weapons
                this.pickup = true;//okay, it picks up some new weapon
                this.oldPickupType = this.newPickupType;
                this.ammunition = 10;
                if(gameEvents.pickup_rocket){
                      this.newPickupType = 1;             
                }else if(gameEvents.pickup_bouncingbomb){
                      this.newPickupType = 2;          
                }else if(gameEvents.pickup_sheild){
                      this.newPickupType = 3;
                      this.ammunition = 1;
                }else if(gameEvents.pickup_toolbox){
                      this.newPickupType = 4;
                      if(health < 25){
                          health += 25;
                      }else{
                          health = 50;
                      }
                }
            }
            //check to see if the opponent pickup weapons collides with tank2
            else if(gameEvents.rocket_tank2 || gameEvents.bouncingbomb_tank2){
                this.health -= 5;
            }
            //check to see if the pickup weapons collides with the opponent tank
            else if(gameEvents.rocket_tank1 || gameEvents.bouncingbomb_tank1){
                this.score += 5;
            }
            //check to see if the bouncing bomb collides with wall of type 1
            else if(gameEvents.bouncingbomb2_wall1){
                this.score += 1;
            }
            
        }//end of else if
        if(show){
        if(this.health <= 0){
            this.show = false;
            //create large explosion music
            ExplosionMusic em = new ExplosionMusic("resources/Explosion_large.wav");
            //create large explosions
            Explosions e = new Explosions(2,(int)this.x,(int)this.y);
            explosions.addAll(e.getExplosions());
        }
        }
        
    }
    public void draw(ImageObserver obs, Graphics2D g2) {
            g2.drawImage(currentImage, (int)x, (int)y, obs);
            //g2.drawRect((int)x + 2, (int)y + 2,currentImage.getWidth(null) - 3, currentImage.getHeight(null) - 3);
           // g2.drawRect((int)x + 6, (int)y + 6,currentImage.getWidth(null) - 10, currentImage.getHeight(null) - 10);
            g2.setColor(Color.red);
            g2.drawRect((int)this.x + 6, (int)this.y, 50, 6);
            g2.fillRect((int)this.x + 6, (int)this.y, health, 6);
            
        }
   
}
