/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author lenovo
 */
public class KeyControl extends KeyAdapter {
        GameEvents gameEvents;
        LinkedList<Integer> keys;//create a list to store the keys that are pressed
        KeyControl(GameEvents gameEvents){
            super();
            this.gameEvents = gameEvents;
            //keys = Collections.synchronizedList(new LinkedList<Integer>());
            keys = new LinkedList<Integer>();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("One key is pressed: "+ e.getKeyChar());
            
            //check if the list keys contains the key
            //if it contains, it means the user is holding on the key, and also
            //it should be the last element in the list
            if(keys.contains(new Integer(e.getKeyCode()))){
                //To let multiply keys be held on together
                //Add all elements in the list with no duplication
                int lastIndex = keys.indexOf(new Integer(e.getKeyCode()));
                
                Integer keyCode = keys.get(0);//get the first keyCode
                //Add a copy of the fist keyCode right after the first appearance of
                //the first keyCode
                keys.add(1,keyCode);
                
                for(int i = 2; i <= lastIndex;i++){
                    Integer keyCode2 = keys.get(i);//get the next keyCode
                    if(keyCode.intValue() != keyCode2.intValue()){
                        keys.add(i,keyCode2); i++;
                        keyCode = keyCode2;
                    }
                }//end of if
                
            }else{//the list keys doesn't contain the key
                keys.add(new Integer(e.getKeyCode()));//append the new keyCode to the end of the list               
            }
            //clone a list of keys
            LinkedList<Integer> newKeys = (LinkedList)keys.clone();
            gameEvents.keyEvent(newKeys);//report the event
        }
        @Override
        public void keyReleased(KeyEvent e){
            //remove all keyCode of value e.getKeyCode() from the list
            while(keys.remove(new Integer(e.getKeyCode()))){}
            //clone a list of keys
            LinkedList<Integer> newKeys = (LinkedList)keys.clone();
            System.out.println("One key is released: "+ e.getKeyChar());
            gameEvents.keyEvent(newKeys);
        }
    }