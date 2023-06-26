package Workers;

import Server.Packer;
import Server.Responce;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobStatus extends Thread{
    
    Packer packer;
    Responce responce;
    public boolean pausada = true;
    
    
    public MobStatus(Responce responce, Packer packer){
       this.responce = responce;
       this.packer = packer;
    }
    public synchronized void pausar() {
        this.pausada = true;
    }

    public synchronized void continuar() {
        this.pausada = false;
        notify(); 
    }
    @Override
    public void run() {
        
        while (true && !Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (pausada) {
                    try {
                        wait(); // Espera até ser notificado para continuar
                    } catch (InterruptedException e) {

                    }
                }
            }

            for(int i = 0; i < responce.mobs.size(); i++){
                if(responce.mobs.get(i)[7].equals("0")){
                    MobSpawn mobSpawn = new MobSpawn(responce , packer , i);
                    mobSpawn.start();
                }

            }
            try {
                sleep(1000);
            } catch (InterruptedException ex) {

            }
        }       
    }

}
