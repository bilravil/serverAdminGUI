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
public class FillFapTable {
    
    public ResultSet FillFapTable(Connection con,String txt){      
       String query = "select *from mdk_server.fap_directory  fap   "
                            + "where fap.new_reg_cod like( '"+txt+"%')";      
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
