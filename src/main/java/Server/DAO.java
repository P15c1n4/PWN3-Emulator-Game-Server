
package Server;

import Model.Char;
import Model.Item;
import Model.Mob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DAO {
    
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/pwn3?useTimezone=true&serverTimezone=UTC";
    private String user = "root";
    private String password = "";
    
    public DAO(Map config){
        this.url = "jdbc:mysql://"+config.get("DbIp").toString()+":"+config.get("DbPort").toString()+"/pwn3?useTimezone=true&serverTimezone=UTC";
        this.user = config.get("DbLogin").toString();
        this.password = config.get("DbPass").toString();
    }
    
    
    
    private Connection conectarMysql() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            return con;
            
        }catch (SQLException e) {
            System.out.println(e);
            return null;
            
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
   
    public ArrayList<Mob> GetBearsMobZ1(){
        ArrayList<Mob> mobs = new ArrayList();
        
        String sql = "Select * from mobsbears";
       
        Connection con = conectarMysql();
        
        try{
            PreparedStatement pst = con.prepareStatement(sql);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                Mob mob = new Mob();
                
                mob.setMobName(rs.getString("mobName"));
                mob.setMobTypeId(rs.getString("mobId"));
                mob.setMobCoord(rs.getString("mobCoord"));
                mob.setMobHp(rs.getInt("mobHp"));
                
                mobs.add(mob);
                
            }
            
            return mobs;
            
        }catch(SQLException e){
            System.out.println(e);
        }

        return mobs;
    }
    
    public ArrayList<Item> GetItens(Char chara){
        ArrayList<Item> itens = new ArrayList();        

        String sql = "Select * from charitens where charId = ?";

        Connection con = conectarMysql();

        try{
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.valueOf(chara.getCharId()));

            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                Item item = new Item();

                item.setItemName(rs.getString("item"));
                item.setItemId(String.valueOf(rs.getInt("id")));
                item.setItemQuant(rs.getInt("total"));
                item.setCharID(String.valueOf(rs.getInt("charID")));
                item.setHand(rs.getBoolean("hand"));
                item.setBulletAta(rs.getInt("bulletAta"));

                itens.add(item);
            }

            con.close();
        }catch(SQLException e){
            System.out.println(e);
        }
        return itens;
    }
    
    
   public void GiveItem(Item item){
       
        String sql = "insert into charitens (charId, item, total, hand, bulletAta) values (?,?,?,?,?)";

        Connection con = conectarMysql();
        
        try{
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, item.getCharID());
            pst.setString(2, item.getItemName());
            pst.setInt(3, item.getItemQuant());
            pst.setBoolean(4, item.isHand());
            pst.setInt(5, item.getBulletAta());
            
            pst.executeUpdate();
            
            con.close();
        }catch(SQLException e){
            System.out.println(e);
        }
   }

public void UpdateInv(Item item){
    if(Integer.valueOf(item.getItemQuant()) == 0){
        String sql = "delete from charitens where item = ? and charId = ?";

        Connection con = conectarMysql();
        try{
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, item.getItemName());
            pst.setString(2, item.getCharID()); 
            
            pst.executeUpdate();
            
            con.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }else if(ChackItem(item)){
        String sql = "update charitens set total = (select total from charitens where item = ? and charId = ?)+? where item = ? and charId = ?";

        Connection con = conectarMysql();
        
        try{
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, item.getItemName());
            pst.setString(2, item.getCharID());
            
            pst.setInt(3, item.getItemQuant());
            pst.setString(4, item.getItemName());
            pst.setString(5, item.getCharID());
            
            pst.executeUpdate();

            con.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
    
    public void UpdateInvBullet(Item item){
            String sql = "update charitens set bulletAta = ? where item = ? and charId = ?";

            Connection con = conectarMysql();

            try{
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, item.getBulletAta());
                pst.setString(2, item.getItemName());
                pst.setString(3, item.getCharID());

                pst.executeUpdate();

                con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
    }  
    
    public boolean ChackItem (Item item){

        String sql = "Select * from charitens where charId = ? and item = ?";

        Connection con = conectarMysql();

        try{
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.valueOf(item.getCharID()));
            pst.setString(2, item.getItemName());

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                con.close();
                return true;
            }else{
                con.close();
                return false;
            }

 
        }catch(SQLException e){
            System.out.println(e);
        }
        return false;
    }
}  


