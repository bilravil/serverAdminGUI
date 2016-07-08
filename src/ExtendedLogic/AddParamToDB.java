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


    public void addParam(String borderLow, String borderUp, String id){
        String query = "UPDATE `mdk_server`.`service_border` SET service_border_low = '"+borderLow+"', service_border_up = '"+borderUp+"' WHERE `mdk_server`.`service_border`.`id` = '" + id + "'";       
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
      
            pstmt.executeUpdate();
 
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
