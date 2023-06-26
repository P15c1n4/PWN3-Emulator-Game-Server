package Workers;

import Server.Packer;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import Server.Responce;
import Server.Responce;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MobMove extends Thread{

    Packer packer;
    Responce responce;
    public boolean pausada = true;
    
    
    public MobMove(Responce responce, Packer packer){
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
            String result = "";
            String roteSpeed = "";
            for(int t = 0; t < 2; t++){
                for(int i = 0; i < responce.mobs.size(); i++){
                    if( responce.mobs.get(i)[7].equals("1")){
                        float x = TradutorFloat(0,8,responce.mobs.get(i)[3],true);
                        float y = TradutorFloat(8,16,responce.mobs.get(i)[3],true);
                        float z = TradutorFloat(16,24,responce.mobs.get(i)[3],true);

                        switch (t){
                            case 1:
                                if(i == 0){
                                    x += 900;
                                    z -= 50;
                                    roteSpeed = "0000" + "0080" + "00"+"00" + "3F"+"FF" + "00"+"00" + "1000";
                                }else if(i == 1){
                                    x += 900;
                                    z -= 117;
                                    roteSpeed = "0000" + "0080" + "00"+"00" + "3F"+"FF" + "00"+"00" + "1900"; //vertical/horizontal/rotação/ /speedX/speedY/speedZ
                                }else{
                                    x += 900;
                                    roteSpeed = "0000" + "0080" + "00"+"00" + "3F"+"FF" + "00"+"00" + "0000"; //vertical/horizontal/rotação/ /speedX/speedY/speedZ                                    
                                }
                                break;
                            case 0:
                                if(i == 0){
                                    x -= 900;
                                    z += 50;
                                    roteSpeed = "0000" + "0000" + "00"+"00" + "C0"+"00" + "00"+"00" + "F5FF"; //vertical/horizontal/rotação/ /speedX/speedY/speedZ                                                                        
                                }else if(i == 1){
                                    x -= 900;
                                    z += 117;
                                    roteSpeed = "0000" + "0000" + "00"+"00" + "C0"+"00" + "00"+"00" + "E6FF"; //vertical/horizontal/rotação/ /speedX/speedY/speedZ

                                }else{
                                    x -= 900;
                                    roteSpeed = "0000" + "0000" + "00"+"00" + "C0"+"00" + "00"+"00" + "0000"; //vertical/horizontal/rotação/ /speedX/speedY/speedZ                                    
                                }
                                break;

                        }

                        String rX = ReverseString(floatToHex(x,"8"));
                        String rY = ReverseString(floatToHex(y,"8"));
                        String rZ = ReverseString(floatToHex(z,"8"));
                        responce.mobs.get(i)[3] = (rX+rY+rZ);
                        result += packer.MobMove(responce.mobs.get(i)[0], responce.mobs.get(i)[3],roteSpeed);
                        roteSpeed = "";
                    }
                }
                responce.SendQueuData();
                responce.queu.add(result+"0000");
                result = "";
                try {
                    sleep(5000);
                } catch (InterruptedException ex) {
                    this.interrupt();

                }
            }
        }
    
    }     
   private Float TradutorFloat(int inicio, int fim, String Hex, boolean big){
        String result = "";
        
        result = Hex.substring(inicio, fim);
        
        if(big){
           result = ReverseString(result); 
        }
               
        Long i = Long.parseLong(result, 16);
        Float f = Float.intBitsToFloat(i.intValue());
        result = f.toString();
        return f;
   }
    private String ReverseString(String Hex){
         String result = "";

         for (int i = Hex.length() - 3; i >= -2; i-=2) {
             result += Hex.charAt(i+1);
             result += Hex.charAt(i+2);
         }

         return result;
    }
    
   public static String floatToHex(float value,String size) {

        int intValue = Float.floatToIntBits(value);

        String hexString = String.format("%0"+size+"X", intValue);

        return hexString;
    }
}

