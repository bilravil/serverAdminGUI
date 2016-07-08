/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
/**
 *
 * @author Равиль
 */
public class GetConnection {
   
    private Connection Connection;
    private String driver;
    private String host;
    private String login;
    private String password;
    
    public GetConnection() {

    }

    public void init(){
     FileInputStream fis;
     Properties property = new Properties();
     
     try {
            fis = new FileInputStream("./src/resources/config.properties");
            property.load(fis);
            driver = property.getProperty("db.driver");
            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");       
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
     try{       
        Class.forName(driver);
        Connection=DriverManager.getConnection(
                host,login, password
                );
         System.out.println("good");
        }
        catch(Exception e){
            System.out.println("Failed to get connection");
            e.printStackTrace();
        }
     
    }
    
    
    
    public Connection getConnection(){
        return Connection;
    }
    
    
    public void close(ResultSet rs){
        
        if(rs !=null){
            try{
               rs.close();
            }
            catch(Exception e){}       
        }
    }
    
     public void close(Statement stmt){
        
        if(stmt !=null){
            try{
               stmt.close();
            }
            catch(Exception e){}
        
        }
    }
     
  public void destroy(){  
    if(Connection !=null){   
         try{
               Connection.close();
               System.out.println("closing");
            }
            catch(Exception e){}        
    }
  }
}
