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
 * @author Равиль
 */
public class FapDownloading {
        private Connection con;
    
    public FapDownloading(Connection con) {
        this.con = con;
    }
    
    public void addFap(String old_reg,String name,String new_reg){
        String query = "INSERT INTO `mdk_server`.`fap_directory`  VALUES (?, ?, ?);";        
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            
            pstmt.setString(1,old_reg); 
            pstmt.setString(2,name);  
            pstmt.setString(3,new_reg);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
