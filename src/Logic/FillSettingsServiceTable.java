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
import java.util.ArrayList;

/**
 *
 * @author Равиль
 */
public class FillSettingsServiceTable {
    private ArrayList<String> vopArr;
    private ArrayList<String> specArr;

    public ArrayList<String> getVopArr() {
        return vopArr;
    }

    public ArrayList<String> getSpecArr() {
        return specArr;
    }
    
            
    public ResultSet FillServiceTable(Connection con,String crb){      
       String query = "select d.codServ, s.service_mnemonic from mdk_server.service_params s inner join"
               + " mdk_server.doc_service_directory d on(d.codServ = s.service_id) "
               + "where d.crbID = '"+crb+"' and d.spec is not null order by (disp_stage) desc";
                              
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
    
    public void insertServiceListToCrb(Connection con,String crbID){
        if(CheckServiceList(con, crbID) == 0){
          String insertQuery = "insert into mdk_server.doc_service_directory (codServ) (select service_id from mdk_server.service_params) ";   
          String updateQuery = "update mdk_server.doc_service_directory set crbID = '"+crbID+"' where crbID is null;";
          PreparedStatement pstmt1;
          PreparedStatement pstmt2;  
        try {
            pstmt1 = con.prepareStatement(insertQuery);
            pstmt1.executeUpdate(); 
            pstmt2 = con.prepareStatement(updateQuery);
            pstmt2.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        }       
    }
    
    public void insertVopOrSpecToDocServ(Connection con,String crbID,String codServ,int sit){
        String query = "";
        switch(sit){
            case 1: query = "update mdk_server.doc_service_directory set vop = '1' "
                    + "where crbID = '"+crbID+"' and codServ = '"+codServ+"'";
            break;
            
            case 2 : query = "update mdk_server.doc_service_directory set spec = '1' "
                    + "where crbID = '"+crbID+"' and codServ = '"+codServ+"'";
            break;
                
            case 3 : query = "update mdk_server.doc_service_directory set vop = NULL "
                    + "where crbID = '"+crbID+"' and codServ = '"+codServ+"'";
            break;
            
            case 4 : query = "update mdk_server.doc_service_directory set spec = NULL "
                    + "where crbID = '"+crbID+"' and codServ = '"+codServ+"'";
            break;
        }
        
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
//    public void insertDocToDocServTable(Connection con,String crbID,String codServ,String docID){
//       String query = "update mdk_server.doc_service_directory set docID = concat_ws(',',docID,'"+codServ+"') "
//               + "where crbID = '"+crbID+"' and codServ = '"+codServ+"'"; 
//       PreparedStatement pstmt;
//        try {
//            pstmt = con.prepareStatement(query);
//            pstmt.executeUpdate(); 
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    
     public ResultSet FillServiceSettingsTable(Connection con,String crbID){      
      insertServiceListToCrb( con, crbID);
         String query = "select d.codServ,s.service_mnemonic,d.checkBox,d.checkBox from mdk_server.doc_service_directory d inner join"
               + "  mdk_server.service_params s on(d.codServ = s.service_id)  where d.crbID = '"+crbID+"' order by (s.disp_stage) desc";
                              
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
    
    public int CheckServiceList(Connection con,String crbID){
        String query = "Select * from mdk_server.doc_service_directory where crbID = '"+crbID+"'";
        PreparedStatement post; 
        ResultSet rs;
         try{
             post = con.prepareStatement(query);
             rs = post.executeQuery(query);
             if (!rs.next()){
                 return 0;
             }
         }catch (SQLException ex) {
             System.out.println(ex);
    }       
        return -1;
    }
    
    public void getVopOrSpecStatus(Connection con,String crbID,int sit){
        String query = "";
        if(sit == 1){
            query = "select dd.codServ from mdk_server.doc_service_directory dd "
                    + "where dd.crbID = '"+crbID+"' and dd.vop is not null";                  
        }
        if(sit == 2){
            query = "select dd.codServ from mdk_server.doc_service_directory dd "
                    + "where dd.crbID = '"+crbID+"' and dd.spec is not null";
        }
        PreparedStatement pstmt;
        ResultSet rs;
        vopArr = new ArrayList<>();
        specArr = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery(query);
            while(rs.next()){
                switch(sit){
                    case 1: vopArr.add(rs.getString(1)); break;
                    case 2: specArr.add(rs.getString(1)); break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
}
