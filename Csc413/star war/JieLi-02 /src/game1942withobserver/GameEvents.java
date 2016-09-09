package game1942withobserver;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.LinkedList;
import java.util.Collections;

/**
 * This class is used to accept events and inform MyPlane class about the events
 */
public class GameEvents extends Observable {
       boolean enemyEvent;//true if Enemy collides with MyPlane
       boolean enemyBulletEvent;//true if EnemyBullet collides with MyPlane
       boolean enemy_with_plane1;//true if Enemy collides with MyPlane(m1)
       boolean enemy_with_plane2;//true if Enemy collides with MyPlane(m2)
       boolean bullet_with_plane1;//true if EnemyBullet collides with MyPlane(m1)
       boolean bullet_with_plane2;//true if EnemyBullet collides with MyPlane(m2)
       boolean enemy_with_myBullet;//true if Enemy collides with Bullet
       boolean bossEvent;//true if Boss collides with MyPlane
       boolean boss_with_myBullet;//true if Boss collides with Bullet
       boolean boss_with_plane1;//true if Boss collides with MyPlane(m1)
       boolean boss_with_plane2;//true if Boss collides with MyPlane(m2)
       boolean bossDie;//true if Boss is killed
       LinkedList<Integer> keys;//keys contains all the keys that are pressed
   public GameEvents(){
       this.enemyEvent = false;
       this.enemyBulletEvent = false;
       this.enemy_with_plane1 = false;
       this.enemy_with_plane2 = false;
       this.bullet_with_plane1 = false;
       this.bullet_with_plane2 = false;
       this.enemy_with_myBullet = false;
       this.bossEvent = false;
       this.boss_with_myBullet = false;
       this.boss_with_plane1 = false;
       this.bullet_with_plane2 = false;
       this.bossDie = false;
       
   }
   public void keyEvent(LinkedList keys) {
          System.out.println("gameEvent recieves a key event");
         // this.keys = Collections.synchronizedList(keys);
          this.keys = keys;
          setChanged();
         // trigger notification
          notifyObservers(this); 
   }

   public void enemy_MyPlane_Event(int planeNumber) {
       System.out.println("receive an enemy event");
          enemyEvent = true;
          if(planeNumber == 1){
              this.enemy_with_plane1 = true;
          }
          if(planeNumber == 2){
              this.enemy_with_plane2 = true;
          }
          //event = msg;
          setChanged();
         // trigger notification
         notifyObservers(this);  
         this.enemyEvent = false;
         this.enemy_with_plane1 = false;
         this.enemy_with_plane2 = false;
   }
   public void enemy_MyBullet_Event(){
       this.enemy_with_myBullet = true;
       setChanged();
       notifyObservers(this);
       this.enemy_with_myBullet = false;
   }
   public void enemyBulletEvent(int planeNumber){
       this.enemyBulletEvent = true;
       if(planeNumber == 1){
           this.bullet_with_plane1 = true;
       }else if(planeNumber == 2){
           this.bullet_with_plane2 = true;
       }
       setChanged();
       notifyObservers(this);
       this.enemyBulletEvent = false;
       this.bullet_with_plane1 = false;
       this.bullet_with_plane2 = false;
   }
   
   public void boss_MyPlane_Event(int planeNumber){
       System.out.println("receive a boss event");
          bossEvent = true;
          if(planeNumber == 1){
              this.boss_with_plane1 = true;
          }
          if(planeNumber == 2){
              this.boss_with_plane2 = true;
          }
          setChanged();
         // trigger notification
         notifyObservers(this);  
         this.bossEvent = false;
         this.boss_with_plane1 = false;
         this.boss_with_plane2 = false;
   }
   public void boss_MyBullet_Event(){
       this.boss_with_myBullet = true;
       setChanged();
       notifyObservers(this);
       this.boss_with_myBullet = false;
   }
   public void boss_is_dead(){
       this.bossDie = true;
       setChanged();
       notifyObservers(this);
       this.bossDie = false;
       
   }
   
  }
