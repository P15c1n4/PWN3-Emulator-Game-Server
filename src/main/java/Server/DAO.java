
package Server;

import Model.Mob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://127.0.0.1:3306/pwn3?useTimezone=true&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "";
    
    
    private Connection conectarMysql() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (Exception e) {
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
            
        }catch(Exception e){
            System.out.println(e);
        }

        return mobs;
    }
}  


