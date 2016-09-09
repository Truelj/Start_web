/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game1942withobserver;
import java.io.*;
import sun.audio.*;
/**
 *
 * @author lenovo
 */
public class ExplosionMusic {
    AudioPlayer MGP;
    AudioStream BGM;
    //AudioData MD;
    //ContinuousAudioDataStream loop;
    public ExplosionMusic(String filename){
        try{
             MGP = AudioPlayer.player;
             BGM = new AudioStream(new FileInputStream(filename));
             //MD = BGM.getData();
             //loop = new ContinuousAudioDataStream(MD);
        }catch(IOException error){
            System.out.println("sound file not found");
        }
        System.out.println("Start to play an explosion sound");
        MGP.start(BGM);
        //play();
    }
    //Maybe we don't need to start it in another thread
    public void play(){
       try{
            //create a new thread to run the backGroundMusic
            new Thread(){
                public void run(){
                    MGP.start(BGM);
                }
            }.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
