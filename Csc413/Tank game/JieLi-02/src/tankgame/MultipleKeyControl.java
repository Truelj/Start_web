/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author lenovo
 */
public class MultipleKeyControl extends KeyAdapter {
        GameEvents gameEvents;
        LinkedList<Integer> keys;//create a list to store the keys that are pressed
        MultipleKeyControl(GameEvents gameEvents){
            super();
            this.gameEvents = gameEvents;
            //keys = Collections.synchronizedList(new LinkedList<Integer>());
            keys = new LinkedList<Integer>();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("One key is pressed: "+ e.getKeyChar());
            
            //check if the list keys contains the key
            //if it contains, it means the user is holding on the key
            if(keys.contains(new Integer(e.getKeyCode()))){
                //Add nothing to the list
            }else{//the list keys doesn't contain the key
                //append the new keyCode to the end of the list
                keys.add(new Integer(e.getKeyCode()));               
            }
            //clone a list of keys
            LinkedList<Integer> newKeys = (LinkedList)keys.clone();
            gameEvents.keyEvent(newKeys);//report the event
        }
        @Override
        public void keyReleased(KeyEvent e){
            //remove keyCode of value e.getKeyCode() from the list
            keys.remove(new Integer(e.getKeyCode()));
            //clone a list of keys
            LinkedList<Integer> newKeys = (LinkedList)keys.clone();
            System.out.println("One key is released: "+ e.getKeyChar());
            gameEvents.keyEvent(newKeys);
        }
    }