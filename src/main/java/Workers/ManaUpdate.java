package Workers;

import Server.Packer;
import Server.Responce;
import Server.Responce;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManaUpdate extends Thread{
    Responce responce;
    Packer packer;
    public boolean pausada = true;
    
    
    public ManaUpdate(Responce responce,Packer packer){
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

        while (true) {
            synchronized (this) {
                while (pausada) {
                    try {
                        wait(); // Espera até ser notificado para continuar
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            while (responce.playerMana < 100){
                try {
                    sleep(700);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManaUpdate.class.getName()).log(Level.SEVERE, null, ex);
                }

                responce.playerMana += 1;
                responce.queu.add(packer.ManaSetUpdate(responce.playerMana));
                responce.SendQueuData();
            }

        }
    
    }     

}