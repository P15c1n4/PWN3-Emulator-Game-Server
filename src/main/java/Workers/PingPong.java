package Workers;

import Server.Responce;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PingPong extends Thread{
    Responce responce;
    
    public PingPong(Responce responce){
       this.responce = responce;
 
    }
    
    @Override
    public void run() {               
        try{ 

            while(true && !Thread.currentThread().isInterrupted()){
                sleep(100);
                responce.queu.add("0000");
                responce.SendQueuData();
            }
            
        } catch (InterruptedException ex) {
            System.out.println("PingPong Finalizado");
            
        }
    
    }     

}