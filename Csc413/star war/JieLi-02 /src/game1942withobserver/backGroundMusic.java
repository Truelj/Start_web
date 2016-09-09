package game1942withobserver;

import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiSystem;
import java.io.*;
/*
 * play midi audio file
 */

public class backGroundMusic {
    //Obtains the default Sequencer connected to a default device.
    Sequencer sequencer;
    InputStream is;
    public backGroundMusic(String filename){
        //Opens the device
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            //create a stream from a file
            is = new BufferedInputStream(new FileInputStream(new File(filename)));
            //set the current sequence on which the sequencer operates
            sequencer.setSequence(is);
            play();           
        }catch(Exception e){
            
         }
       
    }
    public void play(){
        
        sequencer.start();
        sequencer.setLoopCount(5);
        System.out.println("loop continuoulsy is :"+ Sequencer.LOOP_CONTINUOUSLY);
        System.out.println("loop account is:"+ sequencer.getLoopCount());
        if(sequencer.isRunning()){
            System.out.println("the sequencer is still running");
        }
        //System.out.println("back ground music end");
    }
    public void end(){
        sequencer.stop();
        System.out.println("The back ground music stops");
    }
    
}
