/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtendedLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Aynur
 */
public class AddParamToDB {
    private Connection con;
    
    public AddParamToDB(Connection con) {
        this.con = con;
    }


    public void addParam(String borderLowWoman, String borderUpWoman, String borderLowMan, String borderUpMan, String id){
        String query = "UPDATE `mdk_server`.`service_border` SET service_border_low_woman = '"+borderLowWoman+"',"
                + " service_border_up_woman = '"+borderUpWoman+"', service_border_low_man = '"+borderLowMan+"',"
                + "service_border_up_man = '"+borderUpMan+"' "
                + "WHERE `mdk_server`.`service_border`.`id` = '" + id + "'";       
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
      
            pstmt.executeUpdate();
 
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
