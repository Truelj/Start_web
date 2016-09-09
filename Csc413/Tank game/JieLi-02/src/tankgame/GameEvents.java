/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.LinkedList;
import java.util.Observable;

/**
 * GameEvents is used to collect a series of events that causes by other objects. 
 * It will report the event ot its observer, which in this case is only the objects of Tank.
 */
public class GameEvents extends Observable{
    LinkedList<Integer> keys;
    boolean wall_tank1;
    boolean wall_tank2;
    boolean shell1_tank2;//used to determine if shell1 collides with tank2
    boolean shell2_tank1;//used to determine if shell2 collides with tank1
    boolean shell1_shell2;//used to determine if shell1 collides with shell2
    boolean shell2_shell1;//used to determine if shell2 collides with shell1
    boolean pickup_tank1;//used to determine if tank1 picks up any weapons
    boolean pickup_tank2;//used to determine if tank2 picks up any weapons;
    boolean rocket_tank1;
    boolean bouncingbomb_tank1;
    boolean rocket_tank2;
    boolean bouncingbomb_tank2;
    boolean bouncingbomb1_wall1;
    boolean bouncingbomb2_wall1;
    boolean pickup_rocket;
    boolean pickup_bouncingbomb;
    boolean pickup_sheild;
    boolean pickup_toolbox;
    public GameEvents(){
        this.wall_tank1 = false;
        this.wall_tank2 = false;
        this.shell1_tank2 = false;
        this.shell2_tank1 = false;
        this.shell1_shell2 = false;
        this.shell2_shell1 = false;
        this.pickup_tank1 = false;
        this.pickup_tank2 = false;
        this.rocket_tank1 = false;
        this.bouncingbomb_tank1 = false;
        this.rocket_tank2 = false;
        this.bouncingbomb_tank2 = false;
        this.bouncingbomb1_wall1 = false;
        this.bouncingbomb2_wall1 = false;
    }
    public void keyEvent(LinkedList keys) {
          System.out.println("gameEvent recieves a key event");
          this.keys = keys;
          setChanged();
          notifyObservers(this); 
    }
    public void tank_wallEvent(int tankNumber){
        if(tankNumber == 1){//wall collides with tank 1
            wall_tank1 = true;
            setChanged();
            notifyObservers(this); 
            wall_tank1 = false;
        }else if(tankNumber == 2){//wall collides with tank 2
            wall_tank2 = true;
            setChanged();
            notifyObservers(this); 
            wall_tank2 = false;
        }
    }
    public void shell_shellEvent(int shellType){
        if(shellType == 1){//shell1 collides with shell2
            shell1_shell2 = true;
            setChanged();
            notifyObservers(this);
            shell1_shell2 = false;
        }else if(shellType == 2){//shell2 collides with shell1
            shell2_shell1 = true;
            setChanged();
            notifyObservers(this);
            shell2_shell1 = false;
        }
    }
    public void shell_tankEvent(int shellType){
        if(shellType == 1){//shell1 collides with tank2
            shell1_tank2 = true;
            setChanged();
            notifyObservers(this);
            shell1_tank2 = false;
        }else if(shellType == 2){//shell2 collides with tank1
            shell2_tank1 = true;
            setChanged();
            notifyObservers(this);
            shell2_tank1 = false;
        }
    }
    public void pickup_tankEvent(int tankType, int pickupType){
        if(tankType == 1){
            if(pickupType == 1){//rocket collides with tank1
                this.rocket_tank2 = true;
                setChanged();
                notifyObservers(this);
                this.rocket_tank2 = false; 
            }else if(pickupType == 2){//bouncingbomb collides with tank1
                this.bouncingbomb_tank1 = true;
                setChanged();
                notifyObservers(this);
                this.bouncingbomb_tank1 = false;
            }  
        }else if(tankType == 2){
            if(pickupType == 1){//rocket collides with tank2
                this.rocket_tank1 = true;
                setChanged();
                notifyObservers(this);
                this.rocket_tank1 = false; 
            }else if(pickupType == 2){//bouncingbomb collides with tank2
                this.bouncingbomb_tank1 = true;
                setChanged();
                notifyObservers(this);
                this.bouncingbomb_tank1 = false;
            }  
        }
    }
    //pickup_Event method is called when the tank collides one of 4 pickups
    public void pickup_Event(int tankType,int pickupType){
        if(tankType == 1){ 
            this.pickup_tank1 = true;
            if(pickupType == 1){
                this.pickup_rocket = true;
                setChanged();
                notifyObservers(this);
                this.pickup_rocket = false;
            }else if(pickupType == 2){
                this.pickup_bouncingbomb = true;
                setChanged();
                notifyObservers(this);
                this.pickup_bouncingbomb = false;
            }else if(pickupType == 3){
                this.pickup_sheild = true;
                setChanged();
                notifyObservers(this);
                this.pickup_sheild = false;
            }else if(pickupType == 4){
                this.pickup_toolbox = true;
                setChanged();
                notifyObservers(this);
                this.pickup_toolbox = false;
            }  
            this.pickup_tank1 = false;
        }else if(tankType == 2){
            this.pickup_tank2  = true;
            if(pickupType == 1){
                this.pickup_rocket = true;
                setChanged();
                notifyObservers(this);
                this.pickup_rocket = false;
            }else if(pickupType == 2){
                this.pickup_bouncingbomb = true;
                setChanged();
                notifyObservers(this);
                this.pickup_bouncingbomb = false;
            }else if(pickupType == 3){
                this.pickup_sheild = true;
                setChanged();
                notifyObservers(this);
                this.pickup_sheild = false;
            }else if(pickupType == 4){
                this.pickup_toolbox = true;
                setChanged();
                notifyObservers(this);
                this.pickup_toolbox = false;
            }   
            this.pickup_tank2 = false;
            
        }
    }//end of the method
    
    public void bouncingbomb_wall(int tankType){
        if(tankType == 1){
            this.bouncingbomb1_wall1 = true;
            setChanged();
            notifyObservers(this);
            this.bouncingbomb1_wall1 = false;
        }else if(tankType == 2){
            this.bouncingbomb2_wall1 = true;
            setChanged();
            notifyObservers(this);
            this.bouncingbomb2_wall1 = false;
        }
    }
}
