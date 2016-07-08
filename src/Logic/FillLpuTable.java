/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Равиль
 */
public class FillLpuTable {
    private ArrayList<String> lpuArr; 

    public ArrayList<String> getLpuArr() {
        return lpuArr;
    }

    
    public ResultSet FillLpuTable(Connection con,String txt){      
       String query = "SELECT lpu_id FROM mdk_server.lpu_directory  dd where dd.crbID like( '"+txt+"%') ORDER BY lpu_id";       
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            return rs;
        }catch (SQLException ex) {
            System.out.println(ex);
    }        
       return null;
   }
    
    public String GetLpuList(Connection con,String txt){      
       String query = "SELECT lpu_id FROM mdk_server.lpu_directory  dd where dd.crbID like( '"+txt+"%') ORDER BY lpu_id";       
       PreparedStatement post; 
       ResultSet rs;
       lpuArr = new ArrayList<>();
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                lpuArr.add(rs.getString(1));
            }
        }catch (SQLException ex) {
            System.out.println(ex);
    }        
       return null;
   }
    
    
    public void AddNewLpu(Connection con,String lpu,String crb){
       String query = "INSERT INTO `mdk_server`.`lpu_directory` "
               + " VALUES (?,?) ;";
       PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,lpu);
            pstmt.setString(2,crb);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
   }
    
    public void DeleteLpu(Connection con,String txt,String txt2){
        String query = "delete from mdk_server.lpu_directory where lpu_id = '"+txt+"' and crbID = '"+txt2+"'";
        PreparedStatement pstmt;  
        try {
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
