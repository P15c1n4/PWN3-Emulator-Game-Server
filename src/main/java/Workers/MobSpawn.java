package Workers;

import Server.Packer;
import Server.Responce;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

class MobSpawn extends Thread{
    Packer packer;
    Responce responce;
    int i;
    
    public MobSpawn(Responce responce, Packer packer, int i){
       this.responce = responce;
       this.packer = packer;
       this.i = i;
    }

    @Override
    public void run() { 
        responce.mobs.get(i)[7] = "1";
       
        try {
           sleep(10000);
            
           responce.mobs.get(i)[6] = "145";

           if(responce.mobs.size() > 0){
               responce.queu.add(packer.ObjFin(responce.mobs.get(i)[0]));

               responce.mobs.get(i)[0] = objId();


               responce.queu.add(packer.SpawnObj(responce.mobs.get(i)[0], responce.mobs.get(i)[1], responce.mobs.get(i)[3], responce.mobs.get(i)[2]));
               responce.queu.add(packer.AgrroStatus(responce.mobs.get(i)[0],responce.mobs.get(i)[4],responce.mobs.get(i)[5]));

               responce.SendQueuData();
           }
           
        } catch (Exception e) {
            this.interrupt();
        }
        


    }
    
    private String objId(){
        int objId = (int) (Math.random() * 8000) + 1000;
        
        for(int i = 0; i < responce.mobs.size(); i++){
            if(responce.mobs.get(i)[0].equals(String.valueOf(objId))){
                return objId();
            }else{
                return String.valueOf(objId);
            }

        }
        return null;
    }

}