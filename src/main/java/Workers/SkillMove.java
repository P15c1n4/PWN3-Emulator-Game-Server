
package Workers;

import Server.Packer;
import Server.Responce;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkillMove extends Thread{
    Responce responce;
    Packer packer;
    String coord;
    String camera;
    float cX;
    float cY;
    float cZ;
    
    public SkillMove(Responce responce, Packer packer, String coord, String camera, String objId){
        this.responce = responce;
        this.packer = packer;
        this.coord = coord;
        this.camera = camera;
        this.cX = TradutorFloat(0,8, coord, true);
        this.cY = TradutorFloat(8,16, coord, true);
        this.cZ = TradutorFloat(16,24, coord, true);
        
        double vZ = Integer.valueOf(TradutorInt(0,4, camera, true));
        if(vZ <= 16384){
            vZ = (vZ * 0.01);
            
        }else if(vZ >= 49152){
            vZ = inverterValor((int)vZ,65535);
            vZ = (vZ * 0.01);
            vZ = vZ * -1;
        }
        double vX = 0;
        double vY = 0;
        
        double vXY = Integer.valueOf(TradutorInt(4,8, camera, true));
        
        if(vXY >= 0 && vXY <= 16383){
            vX = inverterValor((int)vXY,16383);
            vX = (vXY * 0.01);
            
        }else if(vXY >= 16384 && vXY <= 32767){
            vXY = 32767 - vXY;
            vXY = inverterValor((int)vXY, 16383);
            vXY = (vXY * 0.01);
            vX = vXY * -1;
            
        }else if(vXY >= 32768 && vXY <= 49151){
            vXY = 49151 - vXY;
            vXY = inverterValor((int)vXY,16383);
            vXY = (vXY * 0.01);
            vY = vXY * -1;
            
        }else if(vXY >= 49151 && vXY <= 65535){
            vXY = 65535 - vXY;
            vXY = inverterValor((int)vXY, 16384);
            vY = (vXY * 0.01);
 
        }
        for (int i = 0; i < 5; i++){
            
            cZ += vZ;
            cX += vX;
            cY += vY;
            
            String hexZ = ReverseString(floatToHex(cZ, "8"));
            String hexX = ReverseString(floatToHex(cX, "8"));
            String hexY = ReverseString(floatToHex(cY, "8"));
            
            responce.queu.add(packer.MoveObj(objId, hexX+hexY+hexZ));
            responce.SendQueuData();
            try {
                sleep(700);
            } catch (InterruptedException ex) {
                Logger.getLogger(SkillMove.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    

    
    @Override
    public void run() {
        
        
        
    }
    public static int inverterValor(int valor, int maxnum) {
        int valorMaximo = maxnum;
        int diferenca = valorMaximo - valor;
        int valorInvertido = diferenca % (valorMaximo + 1);
        return valorInvertido;
    }
   private String TradutorInt(int inicio, int fim, String Hex, boolean big){
        String result = "";
        
        if(inicio != -1 && fim != -1){
            result = Hex.substring(inicio, fim);
        }else{
            result = Hex;
        }
        
        if(big){
           result = ReverseString(result); 
        }
        
        int i = Integer.parseInt(result, 16);
        result = String.valueOf(i);
        return result;
   }  
    
    public static String floatToHex(float value,String size) {

        int intValue = Float.floatToIntBits(value);

        String hexString = String.format("%0"+size+"X", intValue);

        return hexString;
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
    
    
    
}