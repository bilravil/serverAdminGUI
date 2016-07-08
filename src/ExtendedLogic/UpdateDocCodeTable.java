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
public class UpdateDocCodeTable {
    private Connection con;
    
    public UpdateDocCodeTable(Connection con) {
        this.con = con;
    }
    
    public void insert(String serviceCode,String v002,String v015){
        String query = "INSERT INTO `mdk_server`.`doc-scpeciality`  VALUES (?, ?, ?);";        
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            
            pstmt.setString(1,serviceCode); 
            pstmt.setString(2,v002);  
            pstmt.setString(3,v015);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
