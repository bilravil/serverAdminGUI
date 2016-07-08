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

/**
 *
 * @author Равиль
 */
public class FillUsersTable {
    
    
    public ResultSet FillUsersTable(Connection con,String crb){      
       String query = "SELECT `id`, `username`, `status`  FROM mdk_server.users   where users.`crbID`= '"+crb+"'";      
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
    
    
    public void InsertUser(Connection con,String username,String pass,String crbID,String status){      
       String query = "Insert into mdk_server.users (username,pass,crbID,`status`) values(?,SHA(?),?,?)";      
       PreparedStatement post; 
        try{
            post = con.prepareStatement(query);
            post.setString(1,username);
            post.setString(2,pass);  
            post.setString(3,crbID);
            post.setString(4,status);
            post.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex);
    }              
   }
    
   public void DeleteUser(Connection con,String username){      
       String query = "delete from mdk_server.users  where username = '"+username+"'";      
       PreparedStatement post; 
        try{
            post = con.prepareStatement(query);
            post.executeUpdate();
        }catch (SQLException ex) {
            System.out.println(ex);
    }              
   }
   
   public int getUsername(Connection con,String username){
       String query = "select * frmo mdk_server.users u where u.username = '"+username+"'";      
       PreparedStatement post;
       ResultSet rs;
         try{
             post = con.prepareStatement(query);
             rs = post.executeQuery(query);
             if (!rs.next()){
                 return -1;
             }
         }catch (SQLException ex) {
             System.out.println(ex);
    }       
        return 0;
   }
}
