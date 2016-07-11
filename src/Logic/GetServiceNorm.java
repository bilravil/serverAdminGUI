/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Aynur
 */
public class GetServiceNorm {
    String  service_code, servise_border_woman, servise_border_man,codeQuery;
    private ArrayList<String> norms_woman = new ArrayList();
    private ArrayList<String> norms_man = new ArrayList();
    private ArrayList<String> code = new ArrayList();
    
    public ArrayList<String> getNormsWoman() {
        return norms_woman;
    }

    public ArrayList<String> getNormsMan() {
        return norms_man;
    }
    
    public ArrayList<String> getCode() {
        return code;
    }
    
    public void getNorm(Connection con) throws SQLException {
        codeQuery = "SELECT DISTINCT service_code FROM mdk_server.service_border ORDER BY id";
        
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(codeQuery);
            rs = post.executeQuery(codeQuery);
            while(rs.next()){
                code.add(rs.getString(1));
            }
            
            String conc = "";
            String norm;
            for (int i = 0; i < code.size(); i++) {
                service_code = code.get(i);
                servise_border_woman = "SELECT service_border_low_woman, service_border_up_woman "
                + "FROM mdk_server.service_border WHERE service_code = '" + service_code + "' ORDER BY id";
                post = con.prepareStatement(servise_border_woman);
                rs = post.executeQuery(servise_border_woman);
                while (rs.next()){
                    norm = rs.getString(1) + " - " + rs.getString(2) + ";";
                    conc = conc+norm;   
                }
                norms_woman.add(conc);
                conc = "";
                norm = "";
                
                servise_border_man = "SELECT service_border_low_man, service_border_up_man "
                + "FROM mdk_server.service_border WHERE service_code = '" + service_code + "' ORDER BY id";
                post = con.prepareStatement(servise_border_man);
                rs = post.executeQuery(servise_border_man);
                while (rs.next()){
                    norm = rs.getString(1) + " - " + rs.getString(2) + ";";
                    conc = conc+norm;   
                }
                norms_man.add(conc);
                conc = "";
                norm = ""; 
            }
                       
          
        }catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    
//    public void getNormMan(Connection con) throws SQLException {
//        codeQuery = "SELECT DISTINCT service_code FROM mdk_server.service_border ORDER BY id";
//        
//        PreparedStatement post; 
//        ResultSet rs;
//        try{
//            post = con.prepareStatement(codeQuery);
//            rs = post.executeQuery(codeQuery);
//            while(rs.next()){
//                code.add(rs.getString(1));
//            }
//            
//            String conc = "";
//            String norm;
//            for (int i = 0; i < code.size(); i++) {
//                service_code = code.get(i);
//                servise_border_woman = "SELECT service_border_low_woman, service_border_up_woman "
//                + "FROM mdk_server.service_border WHERE service_code = '" + service_code + "' ORDER BY id";
//                post = con.prepareStatement(servise_border_woman);
//                rs = post.executeQuery(servise_border_woman);
//                while (rs.next()){
//                    norm = rs.getString(1) + " - " + rs.getString(2) + ";";
//                    conc = conc+norm;   
//                }
//                norms_woman.add(conc);
//                conc = "";
//                norm = "";
//                
//                servise_border_man = "SELECT service_border_low_man, service_border_up_man "
//                + "FROM mdk_server.service_border WHERE service_code = '" + service_code + "' ORDER BY id";
//                post = con.prepareStatement(servise_border_man);
//                rs = post.executeQuery(servise_border_man);
//                while (rs.next()){
//                    norm = rs.getString(1) + " - " + rs.getString(2) + ";";
//                    conc = conc+norm;   
//                }
//                norms_man.add(conc);
//                conc = "";
//                norm = ""; 
//            }
//                       
//          
//        }catch (SQLException ex) {
//            System.out.println(ex);
//        }
//        
//    }
}
