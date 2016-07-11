/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtendedLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aynur
 */
public class FillServiceParamTable {
    
    public ResultSet FillServParamTable(Connection con){      
        String query = "SELECT DISTINCT service_code, service_name FROM mdk_server.service_border, mdk_server.service_directory WHERE mdk_server.service_border.service_code = mdk_server.service_directory.service_id";
       
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
    
    public String CountOfNorm (Connection con, String text){
        String query = "SELECT  COUNT(*) FROM mdk_server.service_properties WHERE service_code = '"+ text +"'";
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
    
    public ResultSet FillParamTable(Connection con, String text){      
       String query = "SELECT distinct id, service_param, service_border_low_woman, "
               + "service_border_up_woman,service_border_low_man, service_border_up_man "
               + "FROM mdk_server.service_border "
               + "WHERE service_code = '"+ text +"'";
       
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
}
    

