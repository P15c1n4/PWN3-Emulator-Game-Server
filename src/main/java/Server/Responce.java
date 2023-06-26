package Server;

import Workers.MobMove;
import Workers.PingPong;
import Workers.ManaUpdate;
import Workers.SkillMove;
import Model.Mob;
import Workers.MobStatus;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Math;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HexFormat;

public class Responce extends Thread{

    
    public ArrayList<String> queu = new ArrayList();
    Packer packer = new Packer();
    DAO dao = new DAO(); 
    
    
    ManaUpdate manaUpdate; 
    InputStream inputStream;
    OutputStream outputStream;
    String playerId;
    String playerCoord;
    String playerCamera;
    PingPong pingPong;
    MobMove mobMove;
    boolean mobSpawned = false;
    MobStatus mobStatus;
    
    
    int atualWep = 0;
    String[][] wepStatus = new String[10][2];
    //public String[][] mobs = new String[10][10];
    public ArrayList<String[]> mobs = new ArrayList();
    public ArrayList<String[]> drops = new ArrayList();
    
    
    
    public int playerMana = 100;
    
    public Responce(InputStream inputStream,OutputStream outputStream, String playerId){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.playerId = playerId;
    }

    @Override
    public void run() {
                       
            PingPong pingPong = new PingPong(this);
            pingPong.start();
            ManaUpdate manaUpdate = new ManaUpdate(this, packer);
            manaUpdate.start();
            MobMove mobMove = new MobMove(this, packer);
            mobMove.start();
            MobStatus mobStatus = new MobStatus(this,packer);
            mobStatus.start();
            
            this.pingPong = pingPong;
            this.manaUpdate = manaUpdate;
            this.mobMove = mobMove;
            this.mobStatus = mobStatus;
            
            try{
        
                byte[] buffer = new byte[4096]; 
                int bytesRead;

                this.wepStatus[0][0] = "GreatBallsOfFire";
                this.wepStatus[0][1] = "0";
                this.wepStatus[1][0] = "CowboyCoder";
                this.wepStatus[1][1] = "0";
                atualWep = 1;
                
                while((bytesRead = inputStream.read(buffer)) != -1 && !Thread.currentThread().isInterrupted()){

                    String Hex = bytesToHex(buffer, bytesRead).replaceAll(" ", "");
                    //System.out.println("\nCliente-->\n"+Hex);
                    

                    String magicByte = Hex.substring(0,4);

                    switch(magicByte){
                        case "2A69":

                            SpawnObjSkill(Hex);
                            break;
                        
                        //Change Wep
                        case "733D":
                            WepChange(Hex);
                            break;
                            
                        //Wep Realod
                        case "726C":
                            WepReload();
                            break;
                        
                        //Coord
                        case "6D76":
                            VerificaCoord(Hex);
                            break;
                         
                        //Pick item
                        case "6565":
                            PickItem(Hex);
                            break;
                            
                        case "232A":
                            Chat(Hex);
                            break;
                            
                    }
                    
                }
            }catch(Exception e){
                e.printStackTrace();
            }
}

    private void Chat(String Hex){
        int posStg = (Integer.valueOf(TradutorInt(4, 8, Hex, true))*2)+8;
        
        if(posStg == 8){
            return;
        }
        
        String men = TradutorString(8, posStg, Hex);

        if(men.substring(0,1).equals("@")){
            String[] stg = men.split(" ");
            switch (stg[0]){
                case "@loot":
                    try{
                        ItemDrop(Integer.valueOf(stg[1]), "", 0, playerCoord);

                        queu.add(packer.SendChat(men,playerId));
                        SendQueuData();
                    }catch(Exception e){
                        
                    }
                    break;
                case "@makeitem":
                    try{
                        if(stg[2] != null){
                            ItemDrop(2, stg[1], Integer.valueOf(stg[2]), playerCoord);
                        }else{
                            ItemDrop(2, stg[1], 1, playerCoord);
                        }                    
                    }catch(Exception e){
                        
                    }
                    
                    queu.add(packer.SendChat(men,playerId));
                    SendQueuData();
                    
                    break;
            }
        }else{
            queu.add(packer.SendChat(men,playerId));
            SendQueuData();
        }
        
    }
    
    
    private void PickItem(String Hex){
        String id = Hex.substring(4,8);
        
        for (int i = 0; i < drops.size(); i++){
            if(drops.get(i)[4].equals(id)){
                switch (drops.get(i)[1]){
                    
                    case "RemoteExploit":
                        for(int t = 0; t < 10; t++){
                            if(wepStatus[t][0] == null){
                                wepStatus[t][0] = "RemoteExploit";
                                wepStatus[t][1] = "4";
                                break;
                            }
                        }
                        break;
                        
                    case "AKRifle":
                        for(int t = 0; t < 10; t++){
                            if(wepStatus[t][0] == null){
                                wepStatus[t][0] = "AKRifle";
                                wepStatus[t][1] = "30";
                                break;
                            }
                        }
                        break;
                        
                    case "Pistol":
                        for(int t = 0; t < 10; t++){
                            if(wepStatus[t][0] == null){
                                wepStatus[t][0] = "Pistol";
                                wepStatus[t][1] = "17";
                                break;
                            }
                        }
                        break;
                }
                    
                queu.add(packer.SendDrop(drops.get(i)[1], Integer.valueOf(drops.get(i)[2])));
                queu.add(packer.ObjFin(drops.get(i)[4]));
                drops.remove(i);
                SendQueuData();
                break;
            }
        }

    }
    
    
    
    //pacote spawn de Mobe outras ações com coordenada
    private void VerificaCoord(String Hex){
        
        playerCoord = Hex.substring(4,28);
        playerCamera = Hex.substring(28,36);
        
        float x = TradutorFloat(4,12,Hex,true);
        float y = TradutorFloat(12,20,Hex,true);
        float z = TradutorFloat(20,28,Hex,true);
        
        int x1 = -26280;
        int y1 = -28564;
        int x2 = -29340;
        int y2 = -37209;

        // Calcula os coeficientes angular e linear
        double m = (double) (y2 - y1) / (x2 - x1);
        double c = y1 - m * x1;

        // Calcula o valor y esperado para o ponto rx
        double expectedY = m * x + c;
        
        // spawn de ursos z1
        if(y < expectedY && !mobSpawned){
            ArrayList<Mob> arrayMobs = new ArrayList();
            arrayMobs = dao.GetBearsMobZ1();
            
            int objId = (int) (Math.random() * 8000) + 1000;
            
            for(int i = 0; i < arrayMobs.size(); i++){
                String[] newMob = new String[10];
                
                newMob[0] = String.valueOf(objId + (100 * i));
                newMob[1] = arrayMobs.get(i).getMobName();
                newMob[2] = arrayMobs.get(i).getMobTypeId();
                newMob[3] = arrayMobs.get(i).getMobCoord();
                newMob[4] = "Run";
                newMob[5] = "00";
                newMob[6] = String.valueOf(arrayMobs.get(i).getMobHp());
                newMob[7] = "1";
                
                mobs.add(newMob);
                
                queu.add(packer.SpawnObj(mobs.get(i)[0], mobs.get(i)[1], mobs.get(i)[3], mobs.get(i)[2]));
                queu.add(packer.AgrroStatus(mobs.get(i)[0],mobs.get(i)[4],mobs.get(i)[5]));
            }
           SendQueuData();
           mobSpawned = true;
           mobMove.continuar();
           mobStatus.continuar();
           
        }else if(y > expectedY && mobSpawned){
            String result = "";
            for(int i = 0; i < mobs.size(); i++){       
                result += packer.ObjFin(mobs.get(i)[0]);
                
            }
            mobMove.pausar();
            mobStatus.pausar();
            mobSpawned = false;
            mobs.clear();
            queu.add(result+"0000");
            SendQueuData();

        }
        
        
        
    }
    
    
    //change Wep
    private void WepChange(String Hex){
        
        String wepNum = Hex.substring(4,6);
        
        atualWep = Integer.valueOf(wepNum);        
        queu.add(packer.WepChange(wepNum));
        SendQueuData();
        
    }

    
    
    //Pacote de reload;
    private void WepReload(){
        String wep = wepStatus[atualWep][0];
        int bullet = Integer.valueOf(wepStatus[atualWep][1]);
        
        switch (wep){
            case "CowboyCoder":
                queu.add(packer.Reload(wep, "RevolverAmmo", 6 - bullet));
                wepStatus[atualWep][1] = "6";
                SendQueuData();
                break;
            case "RemoteExploit":
                queu.add(packer.Reload(wep, "SniperAmmo", 4 - bullet));
                wepStatus[atualWep][1] = "4";
                SendQueuData();
                break;
            
            case "Pistol":
                queu.add(packer.Reload(wep, "PistolAmmo", 17 - bullet));
                wepStatus[atualWep][1] = "17";
                SendQueuData();
                break;     
                
            case "AKRifle":
                queu.add(packer.Reload(wep, "RifleAmmo", 30 - bullet));
                wepStatus[atualWep][1] = "30";
                SendQueuData();
                break;  
        }
        
        
    }
    
    
    //Pacote spawn de Obj
    private void SpawnObjSkill(String Hex){
        
        int posStg = (Integer.valueOf(TradutorInt(4, 8, Hex, true))*2)+8;
        String skillName = TradutorString(8, posStg, Hex);
        String objId = String.valueOf((int) (Math.random() * 9000) + 1000);
        
        int posCood = Hex.indexOf("6D76")+4;
        float cX = TradutorFloat(posCood,posCood+8, Hex, true);
        float cY = TradutorFloat(posCood+8,posCood+16, Hex, true);
        float cZ = TradutorFloat(posCood+16,posCood+24, Hex, true);
        
        switch (skillName){
            case "GreatBallsOfFire":
                
                if(playerMana >= 6){
                    queu.add(packer.CreatObj(objId, playerId, "Fireball", Hex.substring(posCood, posCood+8)+Hex.substring(posCood+8, posCood+16)+Hex.substring(posCood+16, posCood+24)+Hex.substring(posCood+24,posCood+32)));
                    playerMana -= 6;
                    queu.add(packer.ManaSetUpdate(playerMana));
                    //queu.add(packer.MoveObj(objId,playerCoord+playerCamera));
                    SendQueuData();
                    
//                    SkillMove move = new SkillMove(this, packer,playerCoord,playerCamera,objId); // tentativa de mover objeto(Falta o calculo certo)
//                    move.start();


                    if(manaUpdate.pausada){
                        manaUpdate.continuar();
                    }
                    
                }  
                break;
                
            case "CowboyCoder":
                
                if(Integer.valueOf(wepStatus[1][1]) > 0){
                    wepStatus[1][1] = String.valueOf(Integer.valueOf(wepStatus[1][1]) - 1);
                    queu.add(packer.TotalBullet(skillName, Integer.valueOf(wepStatus[1][1])));
                    SendQueuData();
                    
                    HitMob(cX,cY,70);

                }else{
                    wepStatus[1][1] = "0";
                }
                break;
                
            case "RemoteExploit":

                    if(Integer.valueOf(wepStatus[atualWep][1]) > 0){
                         wepStatus[atualWep][1] = String.valueOf(Integer.valueOf(wepStatus[atualWep][1]) - 1);
                         queu.add(packer.TotalBullet(skillName, Integer.valueOf(wepStatus[atualWep][1])));
                         SendQueuData();

                         HitMob(cX,cY,175);

                     }else{
                         wepStatus[atualWep][1] = "0";
                     }

                break;
                
            case "Pistol":

                    if(Integer.valueOf(wepStatus[atualWep][1]) > 0){
                         wepStatus[atualWep][1] = String.valueOf(Integer.valueOf(wepStatus[atualWep][1]) - 1);
                         queu.add(packer.TotalBullet(skillName, Integer.valueOf(wepStatus[atualWep][1])));
                         SendQueuData();

                         HitMob(cX,cY,15);

                     }else{
                         wepStatus[atualWep][1] = "0";
                     }

                break;

            case "AKRifle":

                    if(Integer.valueOf(wepStatus[atualWep][1]) > 0){
                         wepStatus[atualWep][1] = String.valueOf(Integer.valueOf(wepStatus[atualWep][1]) - 1);
                         queu.add(packer.TotalBullet(skillName, Integer.valueOf(wepStatus[atualWep][1])));
                         SendQueuData();

                         HitMob(cX,cY,15);

                     }else{
                         wepStatus[atualWep][1] = "0";
                     }

                break;
        }
        
            


        
    }
    private void HitMob(float cX, float cY,int hpLess){
        float menor = 0;
        int index = -1;

        for(int i = 0; i < mobs.size(); i++){
            float x = TradutorFloat(0,8,mobs.get(i)[3],true);
            float y = TradutorFloat(8,16,mobs.get(i)[3],true);
            float z = TradutorFloat(16,24,mobs.get(i)[3],true);

            float result = calculateDistance(x,y,cX,cY);

            if(menor == 0){
                menor = result;
                index = i;

            }else if(menor > result){
                menor = result;
                index = i;

            } 

        }

        if(index != -1 && Integer.valueOf(mobs.get(index)[6]) != 0){
            int hp = Integer.valueOf(mobs.get(index)[6]);

            if(hp > hpLess){
                hp -= hpLess;
                queu.add(packer.ObjHp(mobs.get(index)[0],hp));

            }else{
                hp = 0;
                queu.add(packer.ObjHp(mobs.get(index)[0],hp));
                queu.add(packer.MobSkill(mobs.get(index)[0],"Dead", "0000"));
                mobs.get(index)[7] = "0"; // LiveStatus

                int randon = (int) (Math.random() * 101);

                if(randon < 60){
                    ItemDrop(0,"",0,mobs.get(index)[3]);

                }else if(randon < 85){
                    ItemDrop(1,"",0,mobs.get(index)[3]);

                }else{
                    ItemDrop(2,"",0,mobs.get(index)[3]);
                }

            }
            mobs.get(index)[6] = String.valueOf(hp);

            SendQueuData(); 
        }        
    }
    
    
    
    private void ItemDrop(int rare, String item, int quant, String coord){
        String[] newDrop = new String[5];
        
        int randon = (int) (Math.random() * 7001) + 1000;
        switch (rare){
            case 0:
                newDrop[0] = "WhiteDrop";
                newDrop[1] = "RevolverAmmo";
                newDrop[2] = "10";
                break;
            case 1:
                newDrop[0] = "GreenDrop";
                newDrop[1] = "SniperAmmo";
                newDrop[2] = "20";
                break;
            case 2:
                newDrop[0] = "BlueDrop";
                newDrop[1] = "RemoteExploit";
                newDrop[2] = "1";
                break;
                
            default:
                newDrop[0] = "WhiteDrop";
                newDrop[1] = "RevolverAmmo";
                newDrop[2] = "1";
                break;
        }
        
        if(!item.equals("")){
            newDrop[1] = item;
        }        
        if(quant != 0){
            newDrop[2] = String.valueOf(quant);
        }
        
        newDrop[3] = coord;
        
        newDrop[4] = String.valueOf(randon);
        
        drops.add(newDrop);
        
        queu.add(packer.SpawnObj(newDrop[4], newDrop[0], newDrop[3], "6400"));
        SendQueuData();
        
    }
    
    
   
    //===================================================================================
    
    public static float calculateDistance(float x1, float y1, float pX, float pY) {
        float dx = pX - x1;
        float dy = pY - y1;
        return (float) Math.sqrt(dx * dx + dy * dy);
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
    
    private static String bytesToHex(byte[] bytes, int length) {
       StringBuilder sb = new StringBuilder();

       for (int i = 0; i < length; i++) {
           sb.append(String.format("%02X ", bytes[i]));
       }

       return sb.toString().trim();
    }
    public void SendQueuData(){

        try {
            if(queu.size() > 0){
                for(int i = 0; i < queu.size(); i++){

                    byte[] bytes = HexFormat.of().parseHex(queu.get(i));
                    int bytesTotal = bytes.length;

                    outputStream.write(bytes, 0, bytesTotal);
                    queu.remove(i);
                }
           }  
        }catch (IOException ex) {
            System.out.println("Conexão Finalizada!");
            pingPong.interrupt();
            mobMove.interrupt();
            mobStatus.interrupt();
            this.interrupt();
        }

    }

       //Inversor de String
    private String ReverseString(String Hex){
         String result = "";

         for (int i = Hex.length() - 3; i >= -2; i-=2) {
             result += Hex.charAt(i+1);
             result += Hex.charAt(i+2);
         }

         return result;
    }
   private String TradutorInt(int inicio, int fim, String Hex, boolean big){
        String result = "";
        
        if(inicio != 0 && fim != 0){
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
   private String TradutorString(int inicio, int fim, String Hex){
        String result = "";
        
        result = Hex.substring(inicio, fim);
        result = new String(new BigInteger(result, 16).toByteArray());
        return result;
   }
   
   
}
