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
               return  rs.getInt("ID");
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
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);   
            pstmt.setInt(1,ID);
            pstmt.setString(2,CODE_MO);
            pstmt.setString(3,CODE_FAP);
            pstmt.setString(4,AREA_TYPE);
            pstmt.setString(5,ATTACH_TYPE);
            pstmt.setString(6,N_CLAIM);
            pstmt.setString(7,D_CLAIM);
            pstmt.setString(8,DOCTYPE);
            pstmt.setString(9,(DOC));
            pstmt.setString(10,SNILS);  
            pstmt.setString(11,CODE_REESTR_SMO);
            pstmt.setString(12,ENP);
            pstmt.setString(13,POLICY);
            pstmt.setString(14,D_ATTACH);
            pstmt.setString(15,AOID);
            pstmt.setString(16,HOUSE);
            pstmt.setString(17,KORP);
            pstmt.setString(18,FLAT);
            pstmt.setString(19,ADDRESS);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void addPatientToDirectory(
             String ID, 
             String CODE_REESTR_UL,  
             String N_AREA,
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
        String query = "INSERT INTO `mdk_server`.`patient_directory` (`REESTR_ID`, `CODE_REESTR_UL`,`N_AREA`, `FAM`, `IM`, `OT`, `SEX`, `DR`, `ADDRESS`)  "
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);          
            pstmt.setString(1,ID);
            pstmt.setString(2,CODE_REESTR_UL); 
            pstmt.setString(3, N_AREA);
            pstmt.setString(4,FAM);
            pstmt.setString(5,IM);
            pstmt.setString(6,OT);
            pstmt.setString(7,SEX);
            pstmt.setString(8,DR);
            String address = ADDRESS +" корп.( "+ KORP+" )  д.( "+ HOUSE+" )  кв.( "+FLAT+" )";
            pstmt.setString(9,address);
            id++;
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void UpdateStatus(int idd){      
       try{    
            String query = "insert into `mdk_server`.`patient_on_mdk` values('"+idd+"','no')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);            
        }catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
