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


public class FillDocTable {
   
   // заполнение таблицы докторов ЦРБ 
   public ResultSet FillDoctorTable(Connection con,String txt){      
       String query = "SELECT docName,SNILS,V002,V015,lpuID, speciality_flag FROM mdk_server.doctor_directory  dd where dd.crbID like( '"+txt+"%')";       
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
    
   // добавление нового доктора в црб
   public void AddNewDoctor(Connection con,String docName,String snils,String v002,String v015,String lpu, String crb, String spec_flag){
       String query = "INSERT INTO `mdk_server`.`doctor_directory`  (`docName`, `SNILS`, `V002`, `V015`,`lpuID`, `crbID`, `speciality_flag`)"
               + " VALUES (?,?,?,?,?,?,?) ;";
       PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,docName);
            pstmt.setString(2,snils);  
            pstmt.setString(3,v002);
            pstmt.setString(4,v015);
            pstmt.setString(5,lpu);
            pstmt.setString(6,crb);
            pstmt.setString(7, spec_flag);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
   } 
   
   
   public void AddNewDoctor(Connection con,String docName,String snils,String v002,String v015,String lpu, String crb){
       String query = "INSERT INTO `mdk_server`.`doctor_directory`  (`docName`, `SNILS`, `V002`, `V015`,`lpuID`, `crbID`)"
               + " VALUES (?,?,?,?,?,?) ;";
       PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,docName);
            pstmt.setString(2,snils);  
            pstmt.setString(3,v002);
            pstmt.setString(4,v015);
            pstmt.setString(5,lpu);
            pstmt.setString(6,crb);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
   } 
   // редактирование доктора
   public void ChangeDoctor(Connection con,String docName,String snils,String v002,String v015,String lpu, String spec_flag, String id){
       String query = "UPDATE `mdk_server`.`doctor_directory` SET `docName`= '"+ docName+"', "
               + "`SNILS`='"+ snils+"', `V002`='"+ v002+"', `V015`='"+ v015+"',`lpuID`='"+ lpu+"', "
               + " `speciality_flag`='"+ spec_flag+"' WHERE docID = '"+ id +"'";
       PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
//            pstmt.setString(1,docName);
//            pstmt.setString(2,snils);  
//            pstmt.setString(3,v002);
//            pstmt.setString(4,v015);
//            pstmt.setString(5,lpu);
//            pstmt.setString(6, spec_flag);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
   } 
   // удаление доткора из црб
    public void DeleteDoctor(Connection con,String txt1,String txt2, String name, String v002, String v015){
        String query = "delete from mdk_server.doctor_directory  where SNILS = '"+txt1+"' and crbID = '"+txt2+"'and docName ='"+name+"' and V002 = '"+v002+"' and V015 = '"+v015+"'";

        PreparedStatement pstmt;  
        try {
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   
   // изменение участка у врача
   public void ChangeDoctorLpu(Connection con,String txt1,String txt2,String txt3){
        String query = "UPDATE `mdk_server`.`doctor_directory` SET `lpuID`= concat_ws(',',lpuID,'"+txt1+"') "
                + "WHERE SNILS ='"+txt2+"' and crbID = '"+txt3+"';";
        PreparedStatement pstmt;  
        try {
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   
   // удаление участка у врача
   public void DeleteLpuFromDoctor(Connection con,String snils,String lpu,String crb,String name,String v002,String v015){
       String query = "UPDATE mdk_server.doctor_directory dd SET dd.lpuID = REPLACE(dd.lpuID,'"+lpu+"','') "
               + "where dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
       PreparedStatement pstms;
       try {
           pstms = con.prepareStatement(query);
           pstms.executeUpdate();
       } catch (Exception e) {
       }
   }
   
   // удаление лишних запятых со строки участок у врача 
   public void CleanCommaFromLpuString(Connection con, int sit, String snils, String crb, String name, String v002, String v015){
      String query = "";
      if(sit == 1){
        query = "UPDATE mdk_server.doctor_directory dd SET dd.lpuID = REPLACE(dd.lpuID,',,',',') where "
                + "dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
      }
      if(sit == 2){
        query = "UPDATE mdk_server.doctor_directory  dd SET dd.lpuID = substr(dd.lpuID,2) where "
                + "dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
      }
      if(sit == 3){
        query = "UPDATE mdk_server.doctor_directory dd SET dd.lpuID = substr(dd.lpuID,1,LENGTH(dd.lpuID)-1) where "
                + "dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
      }
      PreparedStatement pstms;
       try {
           
           pstms = con.prepareStatement(query);
           pstms.executeUpdate();
       } catch (Exception e) {
           System.out.println(e);
       }
   }
   
   // удаление лишних запятых со строки услуга у врача
   public void CleanCommaFromServiceString(Connection con, int sit, String snils, String crb, String name, String v002, String v015){
      String query = "";
      if(sit == 1){
        query = "UPDATE mdk_server.doctor_directory dd SET dd.servCode = REPLACE(dd.servCode,',,',',') where "
                + "dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
      }
      if(sit == 2){
        query = "UPDATE mdk_server.doctor_directory  dd SET dd.servCode = substr(dd.servCode,2) where "
                + "dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
      }
      if(sit == 3){
        query = "UPDATE mdk_server.doctor_directory dd SET dd.servCode = substr(dd.servCode,1,LENGTH(dd.servCode)-1) where "
                + "dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
      }
      PreparedStatement pstms;
       try {
           
           pstms = con.prepareStatement(query);
           pstms.executeUpdate();
       } catch (Exception e) {
           System.out.println(e);
       }
   }
   
   // получение снилса врача по фио и коду wh, 
   public String getDocSnils(Connection con,String name,String crb,String v002,String v015){
       String query = "Select dd.SNILS  FROM mdk_server.doctor_directory dd "
               + "where dd.docName = '"+name+"' and dd.crbID like( '"+crb+"%') and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
    }  
       return null;
   }
  
   // получить код участка для опред врача
   public String getDocLpu(Connection con,String snils,String name,String crb,String v002,String v015){
       String query = "Select dd.lpuID  FROM mdk_server.doctor_directory dd "
               + "where dd.SNILS = '"+snils+"' and dd.docName = '"+name+"' and dd.crbID like( '"+crb+"%') and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
    }  
       return null;
   }
   
    public String getDocID(Connection con,String docName,String snils,String v002,String v015,String lpu){
        String query = "SELECT dd.docID FROM mdk_server.doctor_directory dd "
                + "WHERE dd.SNILS = '"+snils+"' and dd.docName ='"+docName+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"' and dd.lpuID = '"+lpu+"'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
            
            
        }catch (SQLException ex) {
            System.out.println(ex);
    }  
        return null;
   }
   
   // получение услуги у определ врача
   public String getDocService(Connection con,String snils,String name,String crb,String v002,String v015){
       String query = "Select dd.servCode FROM mdk_server.doctor_directory dd "
               + "where `dd`.`SNILS` = '"+snils+"' and dd.docName = '"+name+"' and dd.crbID like( '"+crb+"%') and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
    }  
       return null;
   }
   
   // получение докторов для учасктов. 
   public ResultSet FillLpuDocTable(Connection con,String txt1,String txt2){      
       String query = "SELECT docName,V002,V015 FROM mdk_server.doctor_directory  dd "
               + "where dd.crbID like( '"+txt1+"%') and dd.lpuID like( '"+txt2+"%') or dd.lpuID like( '%"+txt2+"') ";       
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
   
    // получение докторов для таблицы услуг
    public ResultSet FillServDocTable(Connection con,String crb,String servCod){      
       String query = "SELECT docName,V002,V015 FROM mdk_server.doctor_directory  dd "
               + "where dd.crbID like( '"+crb+"%') and dd.servCode like( '"+servCod+"%') or dd.servCode like( '%"+servCod+"') ";       
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
   
    // добавление/изменение услуги у врача 
   public void ChangeDoctorService(Connection con,String codServ,String snils,String crb,String name,String v002,String v015){
        String query = "UPDATE `mdk_server`.`doctor_directory`  SET `servCode`= concat_ws(',',servCode,'"+codServ+"') "
               + "where SNILS = '"+snils+"' and crbID like( '"+crb+"%') and docName ='"+name+"' and V002 = '"+v002+"' and V015 = '"+v015+"'";
        PreparedStatement pstmt;  
        try {
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
        }catch (Exception e) {
            System.out.println(e);
        }
    }
  
   // удаление услуги 
   public void DeleteServiceFromDoctor(Connection con,String snils,String servCode,String crb,String name,String v002,String v015){
       String query = "UPDATE mdk_server.doctor_directory dd SET dd.servCode = REPLACE(dd.servCode,'"+servCode+"','') "
               + "where dd.SNILS = '"+snils+"' and dd.crbID like( '"+crb+"%') and dd.docName ='"+name+"' and dd.V002 = '"+v002+"' and dd.V015 = '"+v015+"'";
       PreparedStatement pstms;
       try {
           pstms = con.prepareStatement(query);
           pstms.executeUpdate();
       } catch (Exception e) {
           System.out.println(e);
       }
   }
   // проверка существеут ли участок
   public String existLPU(Connection con, String lpu_id){
        String query = "SELECT * FROM mdk_server.lpu_directory WHERE lpu_id LIKE ('"+ lpu_id +"%')";
        PreparedStatement pstms;
        ResultSet rs;
        try{
            pstms = con.prepareStatement(query);
            rs = pstms.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
