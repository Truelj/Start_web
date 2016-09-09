
package tankgame;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 * Wall is the obstacle for tank to move around
 * Different type of wall has different futures. 
 * He we create only two types of walls. One is dynamic wall,
 * and the other one is static wall.
 */
public class Wall {
    int wallType;//only two types of wall
    int x, width;
    int y, height;
    int frames;//used to set when the wall of type 2 should come back
    int original_x, original_y;//used to store the original position when wall of type 2is moved away
    boolean show;
    Image wallImg;
    Tank tank1, tank2;
    GameEvents gameEvents;
    LinkedList<Shell> shell_1;
    LinkedList<Shell> shell_2;
    LinkedList<Explosion> explosions;
    LinkedList<Pickups> pickups_1;
    LinkedList<Pickups> pickups_2;
    
    public Wall(int wallType, int x, int y, Tank tank1, Tank tank2, GameEvents gameEvents,
            LinkedList<Shell> shell_1, LinkedList<Shell> shell_2, LinkedList<Pickups> pickups_1,
            LinkedList<Pickups> pickups_2,LinkedList<Explosion> explosions){
        this.wallType = wallType;
        this.x = x; 
        this.y = y;
        this.frames = 0;
        this.show = true;
        try{
            wallImg = ImageIO.read(new File("Resources/Wall" + wallType + ".png"));
            this.width = wallImg.getWidth(null);
            this.height = wallImg.getHeight(null);
        }catch(Exception e){
            System.out.print("Wall1 -- No resources are found");
        }
        this.tank1 = tank1;
        this.tank2 = tank2;
        this.gameEvents = gameEvents;
        this.shell_1 = shell_1;
        this.shell_2 = shell_2;
        this.pickups_1 = pickups_1;
        this.pickups_2 = pickups_2;
        this.explosions = explosions;
    }
    
    public void update(){
        //each wall object should check collision with tank because each wall has a great
        //possibility to collide with tank
        if(tank1.collision(this.x, this.y, this.wallImg.getWidth(null), this.wallImg.getHeight(null))){//check collision with tank1
                //Report the event to let tank2 move backward
                gameEvents.tank_wallEvent(1);
                //Do nothing else
        }
        if(tank2.collision(this.x, this.y, this.wallImg.getWidth(null), this.wallImg.getHeight(null))){//check collision with tank2
                //Report the event to let tank1 move backward
                gameEvents.tank_wallEvent(2);
                //Do nothing else
        }
        if(wallType == 1){
            //wall of type 1 should also check collision with shells
            for(int i = 0; i < shell_1.size(); i++){//check collision with tank1's shell
                Shell s1 = shell_1.get(i);
                if(s1.collision(x, y, this.width, this.height)){
                    //System.out.println("wall of type "+ wallType + " collides with shell 1");
                    //create small explosion music
                    ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                    shell_1.remove(i);//remove the shell
                    //create small explosion
                    Explosions e = new Explosions(1,(int)s1.x,(int)s1.y);
                    explosions.addAll(e.getExplosions());
                    //report the event? No      
                }
            }
            for(int i = 0; i < shell_2.size(); i++){//check collision with tank2's shell
                Shell s2 = shell_2.get(i);
                if(s2.collision(x, y, this.width, this.height)){
                    //System.out.println("wall of type "+ wallType + " collides with shell 2");
                    //create small explosion music
                    ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                    shell_2.remove(i);//remove the shell
                    //create small explosion
                    Explosions e = new Explosions(1,(int)s2.x,(int)s2.y);
                    explosions.addAll(e.getExplosions());
                    //report the event? No   
                }
            }
            //now should check collision with bouncing bombs from tank1
            for(int i = 0; i < this.pickups_1.size(); i++){
                Pickups p = pickups_1.get(i);
                if(p.pickupType == 2){//if the pickup is bouncing bomb
                    if(p.collision(x, y, this.width, this.height)){
                    //System.out.println("wall of type "+ wallType + " collides with bomb 1");
                    //create small explosion music
                    ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                    pickups_1.remove(i);//remove the pickup from the list
                    //create small explosion
                    Explosions e = new Explosions(1,(int)p.x,(int)p.y);
                    explosions.addAll(e.getExplosions());
                    //create small explosion for wall of type 1
                    e = new Explosions(1,this.x,this.y);
                    explosions.addAll(e.getExplosions());
                    show = false;
                    //report the event? Yes. Add score to tank1 
                    gameEvents.bouncingbomb_wall(1);
                   }
                }
            }
            //now should check collision with bouncing bombs from tank2
            for(int i = 0; i < this.pickups_2.size(); i++){
                Pickups p = pickups_2.get(i);
                if(p.pickupType == 2){//if the pickup is bouncing bomb
                    if(p.collision(x, y, this.width, this.height)){
                    //System.out.println("wall of type "+ wallType + " collides with bomb 2");
                    //create small explosion music
                    ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                    pickups_2.remove(i);//remove the pickup from the list
                    //create small explosion
                    Explosions e = new Explosions(1,(int)p.x,(int)p.y);
                    explosions.addAll(e.getExplosions());
                    //create small explosion for wall of type 1
                    e = new Explosions(1,this.x,this.y);
                    explosions.addAll(e.getExplosions());
                    show = false;
                    //report the event? Yes. Add score to tank1 
                    gameEvents.bouncingbomb_wall(2);
                   }
                }
            }
        }else if(wallType == 2){
            if(this.frames == 0){
                //wall of type 2 should also check collision with shells
                for(int i = 0; i < shell_1.size(); i++){//check collision with shell of type 1
                    Shell s1 = shell_1.get(i);
                    if(s1.collision(x, y, this.width, this.height)){
                        //System.out.println("wall of type "+ wallType + " collides with shell 1");
                        //create small explosion music
                        ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                        shell_1.remove(i);//remove the shell1
                        //create small explosion for shell1
                        Explosions e = new Explosions(1,(int)s1.x,(int)s1.y);
                        explosions.addAll(e.getExplosions());
                        //report event? Maybe not
                        //create small explosion for wall of type 2
                        e = new Explosions(1,this.x,this.y);
                        explosions.addAll(e.getExplosions());
                        this.frames = 50; i = shell_1.size();//stop checking the rest shells
                        this.original_x = this.x; this.original_y = this.y;
                        this.x = 2000; this.y = 2000;     
                    }
                }//end of for
                if(this.frames != 50){//continue to check collision with shell of type 2
                    for(int i = 0; i < shell_2.size(); i++){
                        Shell s2 = shell_2.get(i);
                        if(s2.collision(x, y, this.width, this.height)){
                            //System.out.println("wall of type "+ wallType + " collides with shell 2");
                            //create small explosion music
                            ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                            shell_2.remove(i);//remove the shell
                            //create small explosion for shell
                            Explosions e = new Explosions(1,(int)s2.x,(int)s2.y);
                            explosions.addAll(e.getExplosions());
                            //create small explosion for wall of type 2
                            //report the event?
                            e = new Explosions(1,this.x,this.y);
                            explosions.addAll(e.getExplosions());
                            this.frames = 50; i = shell_2.size();//stop checking the rest shells
                            this.original_x = this.x; this.original_y = this.y;
                            this.x = 2000; this.y = 2000;       
                        }
                    }//end of for
                }//end of if(this.frames != 50)
                if(this.frames != 50){//continue to check collision with bouncing bomb from tank1
                    //now should check collision with bouncing bombs from tank1
                    for(int i = 0; i < this.pickups_1.size(); i++){
                        Pickups p = pickups_1.get(i);
                        if(p.pickupType == 2){//if the pickup is bouncing bomb
                            if(p.collision(x, y, this.width, this.height)){
                                //System.out.println("wall of type "+ wallType + " collides with bomb 1");
                                //create small explosion music
                                ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                                pickups_1.remove(i);//remove the pickup from the list
                                //create small explosion
                                Explosions e = new Explosions(1,(int)p.x,(int)p.y);
                                explosions.addAll(e.getExplosions());
                                //no need to report event
                                e = new Explosions(1,this.x,this.y);
                                explosions.addAll(e.getExplosions());
                                this.frames = 50; i = this.pickups_1.size();//stop checking the rest shells
                                this.original_x = this.x; this.original_y = this.y;
                                this.x = 2000; this.y = 2000;       
                            }
                         }
                    }//end of for loop
                }
                if(this.frames != 50){//continue to check collision with bouncing bomb from tank2
                    //now should check collision with bouncing bombs from tank1
                    for(int i = 0; i < this.pickups_2.size(); i++){
                        Pickups p = pickups_2.get(i);
                        if(p.pickupType == 2){//if the pickup is bouncing bomb
                            if(p.collision(x, y, this.width, this.height)){
                                //System.out.println("wall of type "+ wallType + " collides with bomb 2");
                                //create small explosion music
                                ExplosionMusic em = new ExplosionMusic("resources/Explosion_small.wav");
                                pickups_2.remove(i);//remove the pickup from the list
                                //create small explosion
                                Explosions e = new Explosions(1,(int)p.x,(int)p.y);
                                explosions.addAll(e.getExplosions());
                                //no need to report event
                                e = new Explosions(1,this.x,this.y);
                                explosions.addAll(e.getExplosions());
                                this.frames = 50; i = this.pickups_2.size();//stop checking the rest shells
                                this.original_x = this.x; this.original_y = this.y;
                                this.x = 2000; this.y = 2000;       
                            }
                        }    
                    }//end of for loop
                }//end of if(frame != 50)
            }else if(this.frames > 1){
                this.frames--;
            }else if(this.frames == 1){
                this.x = this.original_x; this.y = this.original_y;//reset
            }
            
        }
        
    }
    public void draw(ImageObserver obs, Graphics2D g2){
        g2.drawImage(wallImg, x, y, obs);
        //g2.drawRect(x, y, wallImg.getWidth(null), wallImg.getHeight(null));
        
    }
}
