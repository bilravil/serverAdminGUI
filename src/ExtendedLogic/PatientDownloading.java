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
    
    
}
