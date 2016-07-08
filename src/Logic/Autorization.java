/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.sql.*;

/**
 *
 * @author Равиль
 */
public class Autorization implements IAutorization{
    private int status;
    private String crbCode;

    public int getStatus() {
        return status;
    }
    
    public String getCrbCode(){
        return crbCode;
    }
    
    
    @Override
    public boolean login(String login, String pass,Connection conn) {
        String query = "select * from mdk_server.users where username = '"+login+"' and pass = SHA('"+pass+"')";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = conn.prepareStatement(query);
            rs = post.executeQuery(query);
            if(rs.next()){
                if(login.equals(rs.getString("username"))){
                    status = Integer.parseInt(rs.getString("status"));
                    crbCode = rs.getString("crbID");
                    return true;
                }
                return login.equals(rs.getString("username"));
            }
        }catch (SQLException ex) {
            
    }
    return false; 
}
    
    public String getCrbName(Connection con, String crbCode){
        String query = "select crb_name from mdk_server.crb_directory where new_reg_cod = '"+crbCode+"'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
                
            }
        }catch (SQLException ex) {
            System.out.println(ex);
    }
        return null;
    }
    
}