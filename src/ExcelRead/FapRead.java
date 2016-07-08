/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelRead;

import ExtendedLogic.FapDownloading;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author Равиль
 */
public class FapRead {

    
    private FapDownloading down ;
    private String old_reg_cod;
    private String name;
    private String new_reg_cod;

    
    public void removeAllFields(){
      old_reg_cod = null;
      name = null;
      new_reg_cod = null;
    }
    public void readFromExcel(String file,JTable table) throws IOException{
        
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
 
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellIndex = cell.getColumnIndex();
                
                switch (cellIndex) {
                    case 0:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           old_reg_cod = String.valueOf((int)cell.getNumericCellValue());
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           old_reg_cod = cell.getStringCellValue();
                           break; 
                        } 
                    case 1:
                        name = cell.getStringCellValue();
                        break;
                    case 2:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           new_reg_cod = String.valueOf((int)cell.getNumericCellValue());
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           new_reg_cod = cell.getStringCellValue();
                           break; 
                        }
                    
                    default:
                        System.out.print("|");
                        break;
                }
            }       
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         String [] data = {old_reg_cod,name,new_reg_cod};
         model.addRow(data);
         
         removeAllFields();
        }
        
    }
    
    public void saveToDB(JTable table,Connection con){
        down = new FapDownloading(con);
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
            Object value = table.getValueAt(i,0);
            if (value!=null)
            {
                old_reg_cod = value.toString();
            }
            else{
                old_reg_cod = " ";
             }
             
             name = table.getValueAt(i, 1).toString();
            value = table.getValueAt(i,2);
            if (value!=null)
            {
                new_reg_cod = value.toString();
            }
            else{
                new_reg_cod = " ";
             }
             down.addFap(old_reg_cod, name, new_reg_cod);
        }      
    }
 
}
