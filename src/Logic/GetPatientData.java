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
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Равиль
 */
public class GetPatientData {
    private String  ID;
    private String name;
    private String birth;
    private String sex;
    private String date;
    private String lpu;
    private String snils;
    private String oldPol;
    private String newPol;

    public void setOldPol(String oldPol) {
        this.oldPol = oldPol;
    }

    public void setNewPol(String newPol) {
        this.newPol = newPol;
    }

    public String getOldPol() {
        return oldPol;
    }

    public String getNewPol() {
        return newPol;
    }
    
    private String pass_ser;
    private String pass_num;

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public void setPass_ser(String pass_ser) {
        this.pass_ser = pass_ser;
    }

    public void setPass_num(String pass_num) {
        this.pass_num = pass_num;
    }
    private int count;
    private ArrayList<String> service_name = new ArrayList();
    private ArrayList<String> service_state = new ArrayList();
    private ArrayList<String> service_date = new ArrayList();
    private ArrayList<String> service_result = new ArrayList();
    
    
    public String getSnils() {
        return snils;
    }

    public String getPass_ser() {
        return pass_ser;
    }

    public String getPass_num() {
        return pass_num;
    }
    
    private static final Logger log = Logger.getLogger(GetPatientData.class);
    
    public ArrayList<String> getService_name() {
        return service_name;
    }

    public ArrayList<String> getService_state() {
        return service_state;
    }

    public ArrayList<String> getService_date() {
        return service_date;
    }

    public ArrayList<String> getService_result() {
        return service_result;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int сount) {
        this.count = сount;
    }

    public String getLpu() {
        return lpu;
    }

    public void setLpu(String lpu) {
        this.lpu = lpu;
    }
    
    
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
       
    public String getId(){
        return null;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getSex() {
        return sex;
    }
    
    public void getMainData(Connection con,String id){      
       System.out.println("data was got");
//       String query = "SELECT  * FROM mdk_server.admin_view a where a.ID ='"+id+"' ";    
//       String query1 = "SELECT lpu_id FROM mdk_server.admin_view  natural join mdk_server.patient_list p where p.patient_id ='"+id+"'";
       String query = "SELECT  * FROM mdk_base.admin_view a where a.ID ='"+id+"' ";    
       String query1 = "SELECT lpu_id FROM mdk_base.admin_view  natural join mdk_base.patient p where p.id ='"+id+"'";
   
       
       PreparedStatement post; 
       ResultSet rs;
       log.info("Получены данные из базы");
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                setName(rs.getString("ФИО")) ;
                setBirth(rs.getString("Дата рождения"));
                setSex(rs.getString("Пол"));
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                String d = df.format(rs.getDate("Дата приема"));
                setDate(d);
            }
            post = con.prepareStatement(query1);
            rs = post.executeQuery(query1);
            while(rs.next()){
                setLpu(rs.getString("lpu_id"));               
            }
        }catch (SQLException ex) {
            log.error("Ошибка при получении данных для "+ id ,ex);
        }
    }
    
    public void getPatientDocs(Connection con,String id){
       String query1 = "select * from mdk_base.patient_docs where id = '"+id+"'";
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query1);
            rs = post.executeQuery(query1);
            while(rs.next()){
                setSnils(rs.getString(2));
                setPass_ser(String.valueOf(rs.getInt(3)));
                setPass_num(String.valueOf(rs.getInt(4)));
                setOldPol(rs.getString(5));
                setNewPol(rs.getString(6));
            }
        }catch (SQLException ex) {
            log.error("ошибка при получении док-ов  для " + id,ex);
        }   
    }
        
       public byte[] getPDFData(Connection conn,String id) {
        String query;
        try {
            //query =  "select pdf_bin from mdk_server.ecgpdf_directory where pat_id = '"+id+"'";
            query =  "select pdf_bin from mdk_base.pdf_base where id = '"+id+"'";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                log.info("получен pdf файл для "+ id);
                return  rs.getBytes(1);                  
            }           
        } catch (Exception e) {
            log.error("ошибка при получении pdf файла для " + id,e);
        }return null;
    }
        
       public void getDoneServiceData(Connection con,String id){               
       //String query1 = "SELECT s.service_mnemonic,r.state,r.date,r.result from   mdk_server.patient_result r inner join mdk_server.service_params s on(r.service_code = s.service_id) where r.patient_id = '"+id+"' and r.state = 'Выполнено'";
        String query1 = "SELECT s.service_name,r.state,r.date,r.rezult from   mdk_base.rezult r "
                + "inner join mdk_base.service s on(r.service_code = s.service_code) where r.id = '"+id+"' and r.state = 'Выполнено'";

       PreparedStatement post; 
       ResultSet rs;
        service_name.clear();
        service_state.clear();
        service_date.clear();
        service_result.clear();
        try{
            post = con.prepareStatement(query1);
            rs = post.executeQuery(query1);
            while(rs.next()){
                service_name.add(rs.getString(1));
                service_state.add(rs.getString(2));
                service_date.add(rs.getString(3));
                service_result.add(rs.getString(4));
                
            }
        }catch (SQLException ex) {
            log.error("ошибка при получении 'выполненнных услуг'  для " + id,ex);
        }       
   }
       
       public void getAppServiceData(Connection con,String id){               
       //String query1 = "SELECT s.service_mnemonic,r.state,r.date,r.result from   mdk_server.patient_result r inner join mdk_server.service_params s on(r.service_code = s.service_id) where r.patient_id = '"+id+"' and r.state = 'Назначено'";
        String query1 = "SELECT s.service_name,r.state,r.date,r.rezult from   mdk_base.rezult r "
                + "inner join mdk_base.service s on(r.service_code = s.service_code) where r.id = '"+id+"' and r.state = 'Назначено'";

       PreparedStatement post; 
       ResultSet rs;
       service_name.clear();
        service_state.clear();
        service_date.clear();
        service_result.clear();
        try{
            post = con.prepareStatement(query1);
            rs = post.executeQuery(query1);
            while(rs.next()){
                service_name.add(rs.getString(1));
                service_state.add(rs.getString(2));
                service_date.add(rs.getString(3));               
                service_result.add(rs.getString(4));            
            }
        }catch (SQLException ex) {
             log.error("ошибка при получении 'назначенных' услуг'  для " + id,ex);
        }       
   }
       
       public void getRenServiceData(Connection con,String id){               
       //String query1 = "SELECT s.service_mnemonic,r.state,r.date,r.result from   mdk_server.patient_result r inner join mdk_server.service_params s on(r.service_code = s.service_id) where r.patient_id = '"+id+"' and r.state = 'Отказ'";
        String query1 = "SELECT s.service_name,r.state,r.date,r.rezult from   mdk_base.rezult r "
                + "inner join mdk_base.service s on(r.service_code = s.service_code) where r.id = '"+id+"' and r.state = 'Отказ'";

       PreparedStatement post; 
       ResultSet rs;
       service_name.clear();
        service_state.clear();
        service_date.clear();
        service_result.clear();
        try{
            post = con.prepareStatement(query1);
            rs = post.executeQuery(query1);
            while(rs.next()){
                service_name.add(rs.getString(1));
                service_state.add(rs.getString(2));
                service_date.add(rs.getString(3));
                service_result.add(rs.getString(4));
            }
        }catch (SQLException ex) {
            log.error("ошибка при получении 'отказанных услуг'  для " + id,ex);
        }       
   }
       
       public void getEarServiceData(Connection con,String id){               
       //String query1 = "SELECT s.service_mnemonic,r.state,r.date,r.result from   mdk_server.patient_result r inner join mdk_server.service_params s on(r.service_code = s.service_id) where r.patient_id = '"+id+"' and r.state = 'Выполнено ранее'";
         String query1 = "SELECT s.service_name,r.state,r.date,r.rezult from   mdk_base.rezult r inner join "
                 + "mdk_base.service s on(r.service_code = s.service_code) where r.id = '"+id+"' and r.state = 'Выполнено ранее'";

       PreparedStatement post; 
       ResultSet rs;
       service_name.clear();
        service_state.clear();
        service_date.clear();
        service_result.clear();
        try{
            post = con.prepareStatement(query1);
            rs = post.executeQuery(query1);
            while(rs.next()){
                service_name.add(rs.getString(1));
                service_state.add(rs.getString(2));
                service_date.add(rs.getString(3));
                service_result.add(rs.getString(4));
            }
        }catch (SQLException ex) {
            log.error("ошибка при получении 'выполненнных ранее услуг'  для " + id,ex);
        }       
   }
       
       public void getDoneServiceCount(Connection con,String id){      
       //String query =  "SELECT count(patient_id) FROM mdk_server.patient_result a where a.patient_id = '"+id+"' and  a.state = 'Выполнено'";          
         String query =  "SELECT count(id) FROM mdk_base.rezult a "
                 + "where a.id = '"+id+"' and  a.state = 'Выполнено'";          

       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                setCount(rs.getInt(1));   
                //System.out.println(getCount());
            }
            
        }catch (SQLException ex) {
             log.error("ошибка при получении  кол-ва 'выполненнных' услуг  для " + id,ex);
         } 
        }
       
       public void getAppoitedServiceCount(Connection con,String id){      
       //String query =  "SELECT count(patient_id) FROM mdk_server.patient_result a where a.patient_id = '"+id+"' and  a.state = 'Назначено'";          
       String query =  "SELECT count(id) FROM mdk_base.rezult a "
                 + "where a.id = '"+id+"' and  a.state = 'Назначено'";    
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                setCount(rs.getInt(1));                
            }           
        }catch (SQLException ex) {
            log.error("ошибка при получении  кол-ва 'назначенных' услуг  для " + id,ex);
         } 
        }
       
       public void getRenServiceCount(Connection con,String id){      
       //String query =  "SELECT count(patient_id) FROM mdk_server.patient_result a where a.patient_id = '"+id+"' and  a.state = 'Отказ'";          
       String query =  "SELECT count(id) FROM mdk_base.rezult a "
                 + "where a.id = '"+id+"' and  a.state = 'Отказ'";    
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                setCount(rs.getInt(1));                
            }           
        }catch (SQLException ex) {
            log.error("ошибка при получении  кол-ва 'отказанных' услуг  для " + id,ex);
         } 
        }
       
       public void getEarlierServiceCount(Connection con,String id){      
       //String query =  "SELECT count(patient_id) FROM mdk_server.patient_result a where a.patient_id = '"+id+"' and  a.state = 'Выполнено ранее'";          
       String query =  "SELECT count(id) FROM mdk_base.rezult a "
                 + "where a.id = '"+id+"' and  a.state = 'Выполнено ранее'";    
       PreparedStatement post; 
       ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                setCount(rs.getInt(1));                
            }           
        }catch (SQLException ex) {
            log.error("ошибка при получении  кол-ва 'выполненнных ранее' услуг  для " + id,ex);
         } 
        }
}
