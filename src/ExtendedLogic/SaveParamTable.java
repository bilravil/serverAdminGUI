/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtendedLogic;


import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JTable;
/**
 *
 * @author Aynur
 */
public class SaveParamTable {
    private AddParamToDB down ;
    private String service_border_low;
    private String service_border_up;
    private String id;
    
    public void saveToDB(JTable table,Connection con){
        down = new AddParamToDB(con);
        int length = table.getRowCount();
        boolean flag = false;
        for (int i = 0; i < length; i++) {
           // String value = table.getValueAt(i,1).toString();
           
           id = table.getValueAt(i,0).toString();
            if (table.getValueAt(i,2)!=null)
            {
                service_border_low = table.getValueAt(i,2).toString();
            }
            else{
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля таблицы");
                    flag = true;
                }
                
            }
           // value = table.getValueAt(i,2).toString();
            if (table.getValueAt(i,3)!=null)
            {
                service_border_up = table.getValueAt(i,3).toString();
            }
            else{
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля таблицы");
                    flag = true;
                }
             }
             down.addParam(service_border_low, service_border_up, id);
        }      
    }
}
