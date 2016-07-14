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
import java.sql.Statement;

/**
 *
 * @author Равиль
 */
public class PatientDownloading {
   
    private Connection con;
    private int id = 0;

    public void setId(int id) {
        this.id = id;
    }

    
    public PatientDownloading(Connection con) {
        this.con = con;
    }
    
    public int getID(){
        String query = "select ID from mdk_server.patient_directory ";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query); 
            rs = pstmt.executeQuery();
            rs.afterLast();
            while(rs.previous()){
                id = rs.getInt("ID");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }
    
    public void addPatientToAddDirectory(
             int ID,
             String CODE_MO,
             String CODE_FAP,
             String N_AREA,
             String AREA_TYPE,
             String ATTACH_TYPE,
             String N_CLAIM,
             String D_CLAIM,
             String DOCTYPE,
             String DOC,
             String SNILS,
             String CODE_REESTR_SMO,
             String ENP,
             String POLICY,
             String D_ATTACH,
             String AOID,
             String HOUSE,
             String KORP,
             String FLAT,
             String ADDRESS)
    {
        String query = "INSERT INTO `mdk_server`.`patient_add_directory`  "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);   
            pstmt.setInt(1,ID);
            pstmt.setString(2,CODE_MO);
            pstmt.setString(3,CODE_FAP);
            pstmt.setString(4,N_AREA);
            pstmt.setString(5,AREA_TYPE);
            pstmt.setString(6,ATTACH_TYPE);
            pstmt.setString(7,N_CLAIM);
            pstmt.setString(8,D_CLAIM);
            pstmt.setString(9,DOCTYPE);
            pstmt.setString(10,(DOC));
            pstmt.setString(11,SNILS);  
            pstmt.setString(12,CODE_REESTR_SMO);
            pstmt.setString(13,ENP);
            pstmt.setString(14,POLICY);
            pstmt.setString(15,D_ATTACH);
            pstmt.setString(16,AOID);
            pstmt.setString(17,HOUSE);
            pstmt.setString(18,KORP);
            pstmt.setString(19,FLAT);
            pstmt.setString(20,ADDRESS);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void addPatientToDirectory(
             String ID, 
             String CODE_REESTR_UL,            
             String FAM,
             String IM,
             String OT,
             String SEX,
             String DR,             
             String HOUSE,
             String KORP,
             String FLAT,
             String ADDRESS)
    {
        String query = "INSERT INTO `mdk_server`.`patient_directory` (`REESTR_ID`, `CODE_REESTR_UL`, `FAM`, `IM`, `OT`, `SEX`, `DR`, `HOUSE`, `KORP`, `FLAT`, `ADDRESS`)  "
                + "VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);          
            pstmt.setString(1,ID);
            pstmt.setString(2,CODE_REESTR_UL);    
            pstmt.setString(3,FAM);
            pstmt.setString(4,IM);
            pstmt.setString(5,OT);
            pstmt.setString(6,SEX);
            pstmt.setString(7,DR);
            String address = ADDRESS + KORP+ HOUSE+ FLAT;
            pstmt.setString(8,address);
            id++;
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void UpdateStatus(int id){      
       try{    
            String query = "UPDATE `mdk_server`.`patient_on_mdk` rp SET rp.`ON_MDK`='no' WHERE rp.`ID`='"+id+" ';";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);            
        }catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
