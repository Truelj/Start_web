/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author lenovo
 */
public class SourceReader {
    BufferedReader b;
    //boolean isPriorEndLine = true;
    String nextLine;
    public SourceReader(String filename)throws IOException{
        b = new BufferedReader(new FileReader(filename));
    }
    public String readLine()throws IOException{
            nextLine = b.readLine();
        return nextLine;
    }
    
      
}
