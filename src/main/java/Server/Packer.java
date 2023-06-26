package Server;

import java.math.BigInteger;

public class Packer {
    
    public String SendChat(String men, String playerId){
        StringBuilder result = new StringBuilder();       
        
        String menLen = ReverseString(IntToHex(men.length(), "4"));
        String menHex = StringToHex(men);
        
        result.insert(0, "232A")
              .insert(result.length(), playerId)
              .insert(result.length(), "0000")
              .insert(result.length(), menLen)
              .insert(result.length(), menHex)
              .insert(result.length(), "0000");
        
        return result.toString();
    }
    
    
    public String SendDrop(String ItemName, int quant){
        String total = ReverseString(IntToHex(quant, "4"));
        
        String itemNameLen = ReverseString(IntToHex(ItemName.length(), "4"));
        String itemNameHex = StringToHex(ItemName);
        
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "6370")
              .insert(result.length(), itemNameLen)
              .insert(result.length(), itemNameHex)
              .insert(result.length(), total)
              .insert(result.length(), "00000000");
        
        return result.toString();      
        
    }
    
    public String MobSkill(String mobId, String skillName, String target){
        String skillNameLen = ReverseString(IntToHex(skillName.length(), "4"));
        String skillNameHex = StringToHex(skillName);
        
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "7472")
              .insert(result.length(), mobId+"0000")
              .insert(result.length(), skillNameLen)
              .insert(result.length(), skillNameHex)
              .insert(result.length(), target+"0000");
        
        return result.toString();
    }
    
    
    public String ObjHp(String objId, int hp){
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "2B2B")
              .insert(result.length(), objId+"0000")
              .insert(result.length(), ReverseString(IntToHex(hp, "4"))+"00000000");
        
        return result.toString();
    }
    
    
    public String ObjFin(String objId){
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "7878")
              .insert(result.length(), objId)
              .insert(result.length(), "0000");
       
        return result.toString();
    }
    
    
    public String MobMove(String mobId, String mobCoord, String rote){
        
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "7073")
              .insert(result.length(), mobId+"0000")
              .insert(result.length(), mobCoord)
              .insert(result.length(), rote);
              //.insert(result.length(), "0000" + "0080" + "00"+"00" + "9F"+"FF" + "00"+"00" + "0000");
        
        return result.toString();
    }
    
    
    public String AgrroStatus(String objId, String statusName, String status){

        
        String statusNameLen = ReverseString(IntToHex(statusName.length(), "4"));
        String statusNameHex = StringToHex(statusName);
        
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "7374")
              .insert(result.length(), objId+"0000")
              .insert(result.length(), statusNameLen)
              .insert(result.length(), statusNameHex)
              .insert(result.length(), status);
        
        return result.toString();
    }
    
    
    public String SpawnObj(String idObjBase, String mobName, String mobCoord, String idMobType){
        StringBuilder result = new StringBuilder();

        String mobNameLen = ReverseString(IntToHex(mobName.length(), "4"));
        String mobNameHex = StringToHex(mobName);
        
        result.insert(0, "6D6B")
              .insert(result.length(), idObjBase+"0000")
              .insert(result.length(), "0000000000") // quantidade ?
              .insert(result.length(), mobNameLen)
              .insert(result.length(), mobNameHex)
              .insert(result.length(), mobCoord)
              .insert(result.length(), "000000000000")
              .insert(result.length(), idMobType) // id mob type? 
              .insert(result.length(), "00000000");
            
        return result.toString();
    }
    
    
    public String ManaSetUpdate(int mpTotal){
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "6D61")
              .insert(result.length(), ReverseString(IntToHex(mpTotal, "8")))
              .insert(result.length(), "0000");
        
        return result.toString();
    }
    
    
    
    public String Reload(String wepName, String bulletName, int totalBulletUsed){
        
        String wepNameLen = ReverseString(IntToHex(wepName.length(), "4"));
        String wepNameHex = StringToHex(wepName);
        
        String bullStringLen = ReverseString(IntToHex(bulletName.length(), "4"));
        String bullStringHex = StringToHex(bulletName);
        
        String bullet = ReverseString(IntToHex(totalBulletUsed, "8"));        
        
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "726C")
              .insert(result.length(), wepNameLen)
              .insert(result.length(), wepNameHex)
              .insert(result.length(), bullStringLen)
              .insert(result.length(), bullStringHex)
              .insert(result.length(), bullet)
              .insert(result.length(), "0000");
        
        
        return result.toString();
        
    }
    
    
    public String TotalBullet(String wepName, int totalBullet){
        String wepNameLen = ReverseString(IntToHex(wepName.length(), "4"));
        String SkillNameHex = StringToHex(wepName);
        
        String bullet = ReverseString(IntToHex(totalBullet, "8"));
        
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "6C61")
              .insert(result.length(), wepNameLen)
              .insert(result.length(), SkillNameHex)
              .insert(result.length(), bullet)
              .insert(result.length(), "0000");
        
        return result.toString();
    }
    
    public String WepChange(String num){
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "733D")
              .insert(result.length(), num)
              .insert(result.length(), "0000");
        
        return result.toString();
    }
    
    
    
    public String CreatObj(String objId, String playerId, String skillName, String coord){
        StringBuilder result = new StringBuilder();
                
        String SkillNameLen = ReverseString(IntToHex(skillName.length(), "4"));
        String SkillNameHex = StringToHex(skillName);
        
        result.insert(0, "6D6B")
              .insert(result.length(), objId+"0000")
              .insert(result.length(), playerId+"000000")
              .insert(result.length(), SkillNameLen)
              .insert(result.length(), SkillNameHex)
              .insert(result.length(), coord)
              .insert(result.length(), "000064000000");
        
        return result.toString();
    }

    public String MoveObj(String objId, String coordObj){
        StringBuilder result = new StringBuilder();
        
        result.insert(0, "6D76")
              .insert(result.length(), objId+"0000")
              .insert(result.length(), coordObj) 
              .insert(result.length(), "00000000");
        
        return result.toString();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Conversão basicas
    private String StringToHex(String text){
   
        byte[] bytes = text.getBytes();
        BigInteger bigInteger = new BigInteger(1, bytes);
        String hexadecimal = bigInteger.toString(16);
        
        return hexadecimal;
        
    }
    
    private String IntToHex(int num, String size){
        String hexadecimal = String.format("%0"+size+"X", num);
        
        return hexadecimal;
    }

    public static String floatToHex(float value,String size) {

        int intValue = Float.floatToIntBits(value);

        String hexString = String.format("%0"+size+"X", intValue);

        return hexString;
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
