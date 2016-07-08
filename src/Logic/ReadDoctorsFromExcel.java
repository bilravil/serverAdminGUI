/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

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
public class ReadDoctorsFromExcel {
    
    
 
    private String name;   
    private String snils;
    private String v002;
    private String v015;



    
    public void removeAllFields(){
      name = null;
      snils = null;
      v002 = null;
      v015 = null;
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
                         name =  cell.getStringCellValue();
                        break;
                    case 1:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           snils = String.valueOf((int)cell.getNumericCellValue());
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           snils = cell.getStringCellValue();
                           break; 
                        }
                        break;
 
                    case 2:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           v002 = String.valueOf((int)cell.getNumericCellValue());
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           v002 = cell.getStringCellValue();
                           break; 
                        }
                        break;
                    case 3:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           v015 = String.valueOf((int)cell.getNumericCellValue());
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           v015 = cell.getStringCellValue();
                           break; 
                        }
                        break;
                    
                    default:
                        System.out.print("|");
                        break;
                }
            }       
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         String [] data = {name,snils,v002,v015};
         model.addRow(data);
         
         removeAllFields();
        }
        
    }
    
    public void saveToDB(JTable table,Connection con, String crbCode,FillDocTable doctor){
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
             Object value = new Object();
            
             name = table.getValueAt(i, 0).toString();
             value = table.getValueAt(i,1);
                if (value!=null)
                {
                    snils = value.toString();
                }
                else{
                    snils = " ";
                 }
                value = table.getValueAt(i,2);
                if (value!=null)
                {
                    v002 = value.toString();
                }
                else{
                    v002 = " ";
                 }
             
             value = table.getValueAt(i,3);
                if (value!=null)
                {
                    v015 = value.toString();
                }
                else{
                    v015 = " ";
                 }
             doctor.AddNewDoctor(con, name, snils, v002, v015, crbCode);
             
        }       
    }
  
}
