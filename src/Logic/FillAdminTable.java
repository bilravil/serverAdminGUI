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
public class FillAdminTable {
    public ResultSet FillTable(Connection con,String d1,String d2 , String crb){      
        String query = "SELECT distinct a.`ID`,a.`ФИО`,a.`Пол`,a.`Дата рождения`,a.`Дата приема`,a.`Печать` FROM mdk_server.admin_view a where a.`Дата приема` between '"+d1+"' and '"+d2+"' and  a.`Код ЦРБ` = '"+crb+"'";
        if(d1.equals("null") && d2.equals("null")){
            query = "SELECT distinct a.`ID`,a.`ФИО`,a.`Пол`,a.`Дата рождения`,a.`Дата приема`,a.`Печать` FROM mdk_server.admin_view a where a.`Код ЦРБ` = '"+crb+"'";
        }
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
    
//     public ResultSet FillTable(Connection con,String d1,String d2){      
//       String query = "SELECT distinct * FROM mdk_base.admin_view a where a.`Дата приема` between '"+d1+"' and '"+d2+"' ";
//       if(d1.equals("null") && d2.equals("null")){
//        query = "SELECT distinct * FROM mdk_base.admin_view ";
//       }
//       
//       PreparedStatement post; 
//       ResultSet rs;
//        try{
//            post = con.prepareStatement(query);
//            rs = post.executeQuery(query);
//            return rs;
//        }catch (SQLException ex) {
//            System.out.println(ex);
//    }        
//       return null;
//   }
}
