/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;


public class StartDB {
    
    private GetConnection con;
    
    public void Start(){
        con = new GetConnection();
        con.init();   
    }
    
    public Connection getConnection(){
        return con.getConnection();
    }
    
    public void Stop(){
        con.destroy();
    }
   
}
