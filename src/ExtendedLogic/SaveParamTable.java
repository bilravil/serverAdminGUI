/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtendedLogic;


import GUI.SuperAdminFrame;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JTable;
/**
 *
 * @author Aynur
 */
public class SaveParamTable {
    private AddParamToDB down ;
    private String service_border_low_woman;
    private String service_border_up_woman;
    private String service_border_low_man;
    private String service_border_up_man;
    private String id;
   
    
    
    
    public boolean saveToDB(JTable table,Connection con){
        
        down = new AddParamToDB(con);
        int length = table.getRowCount();
        boolean flag = false;
        for (int i = 0; i < length; i++) {
           // String value = table.getValueAt(i,1).toString();
           
            id = table.getValueAt(i,0).toString();
            if (!"".equals(table.getValueAt(i,2).toString()) && !flag)
            {
                service_border_low_woman = table.getValueAt(i,2).toString();
            }
            else{
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля таблицы");
//                    String text = (String) ServiceTable.getValueAt(ServiceTable.getSelectedRow(), 0);
//                ParamTable.remove(this);
//                saf.ParamTableShow(text);
                    flag = true;
                }
                
            }
           // value = table.getValueAt(i,2).toString();
            if (!"".equals(table.getValueAt(i,3).toString()) && !flag)
            {
                service_border_up_woman = table.getValueAt(i,3).toString();
            }
            else{
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля таблицы");
                    flag = true;
                }
             }
            if (!"".equals(table.getValueAt(i,4).toString()) && !flag)
            {
                service_border_low_man = table.getValueAt(i,4).toString();
            }
            else{
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля таблицы");
                    flag = true;
                }
                
            }
            if (!"".equals(table.getValueAt(i,5).toString()) && !flag)
            {
                service_border_up_man = table.getValueAt(i,5).toString();
            }
            else{
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля таблицы");
                    flag = true;
                }
             }
            
            
             down.addParam(service_border_low_woman, service_border_up_woman, service_border_low_man, service_border_up_man, id);
        } 
        return flag;
    }
    
   
}
