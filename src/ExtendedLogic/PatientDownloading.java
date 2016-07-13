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
public class PatientDownloading {
   
    private Connection con;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public PatientDownloading(Connection con) {
        this.con = con;
    }
    
    public void addPatient(int id,String surname,String name,String middle,String sex,String birth,String adress,
        String lpu_id,String crb_id){
        String query = "INSERT INTO `mdk_server`.`patient_list`  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";        
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            
            pstmt.setInt(1,(id));
            pstmt.setString(2,surname);  
            pstmt.setString(3,name);
            pstmt.setString(4,middle);
            pstmt.setString(5,birth);
            pstmt.setString(6,sex);
            pstmt.setString(7,adress);
            pstmt.setString(8,lpu_id);
            pstmt.setString(9,crb_id);
            //method to insert a stream of bytes
            System.out.println("Пациент с "+ id + " добавлен");
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void addPatientDocs(int  id,String snils,String seria,String num,String oldPol, String newPol,String phoneNum){
        String query = "INSERT INTO `mdk_server`.`patient_docs`  VALUES (?,?,?,?,?,?,?) ";        
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,id);
            pstmt.setString(2,snils);  
            pstmt.setString(3,seria);
            pstmt.setString(4,num);
            pstmt.setString(5,oldPol); 
            pstmt.setString(6,newPol); 
            pstmt.setString(7,phoneNum); 
            //method to insert a stream of bytes
            System.out.println("Док-ты пациент с  id"+ id + " добавлены");
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void addPatientToDirectory(
             String REESTR_ID, 
             String CODE_REESTR_UL, 
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
             String FAM,
             String IM,
             String OT,
             String SEX,
             String DR,
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
        String query = "INSERT INTO `mdk_server`.`patient_directory` (`REESTR_ID`, `CODE_REESTR_UL`, `CODE_MO`, `CODE_FAP`, `N_AREA`, `AREA_TYPE`, `ATTACH_TYPE`, `N_CLAIM`, `D_CLAIM`, `DOCTYPE`, `DOC`, `SNILS`, `FAM`, `IM`, `OT`, `SEX`, `DR`, `CODE_REESTR_SMO`, `ENP`, `POLICY`, `D_ATTACH`, `AOID`, `HOUSE`, `KORP`, `FLAT`, `ADDRESS`)  "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);          
            pstmt.setString(1,REESTR_ID);
            pstmt.setString(2,CODE_REESTR_UL);  
            pstmt.setString(3,CODE_MO);
            pstmt.setString(4,CODE_FAP);
            pstmt.setString(5,N_AREA);
            pstmt.setString(6,AREA_TYPE);
            pstmt.setString(7,ATTACH_TYPE);
            pstmt.setString(8,N_CLAIM);
            pstmt.setString(9,D_CLAIM);
            pstmt.setString(10,DOCTYPE);
            pstmt.setString(11,(DOC));
            pstmt.setString(12,SNILS);  
            pstmt.setString(13,FAM);
            pstmt.setString(14,IM);
            pstmt.setString(15,OT);
            pstmt.setString(16,SEX);
            pstmt.setString(17,DR);
            pstmt.setString(18,CODE_REESTR_SMO);
            pstmt.setString(19,ENP);
            pstmt.setString(20,POLICY);
            pstmt.setString(21,D_ATTACH);
            pstmt.setString(22,AOID);
            pstmt.setString(23,HOUSE);
            pstmt.setString(24,KORP);
            pstmt.setString(25,FLAT);
            pstmt.setString(26,ADDRESS);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
