/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelRead;

import ExtendedLogic.PatientDownloading;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
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
public class PatientRead {

    
    private  PatientDownloading down ;
    private int ID;
    private String name;
    private String surname;
    private String middleName;
    private String birthdate;
    private String sex;
    private String address;
    private String lpu_id;
    private String crb_id;
    private String pass_ser;
    private String pass_num;
    private String snils;
    private String old_police;
    private String new_police;

    @Override
    public String toString() {
        return "PatientRead{" + "ID=" + ID + ", name=" + name + ", surname=" + surname + ", middleName=" + middleName + ", birthdate=" + birthdate + ", sex=" + sex + ", address=" + address + ", lpu_id=" + lpu_id + ", crb_id=" + crb_id + ", pass_ser=" + pass_ser + ", pass_num=" + pass_num + ", snils=" + snils + ", old_police=" + old_police + ", new_police=" + new_police + ", phoneNum=" + phoneNum + '}';
    }
    private String phoneNum;

    
    public void removeAllFields(){
      ID = -1;
      name = null;
      surname = null;
      middleName = null;
      sex = null;
      birthdate = null;
      address = null;
      lpu_id = null;
      crb_id = null;
      pass_ser = null;
      pass_num = null;
      snils = null;
      old_police = null;
      new_police = null;
      phoneNum = null;
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
                        ID = (int)cell.getNumericCellValue();
                      //  table.setValueAt(ID, i, 0);
                        break;
                    case 1:
                        String[] fullName = cell.getStringCellValue().split(" ");
                        
                        name = fullName[1];
                        surname = fullName[0];
                        middleName = fullName[2];
                       // table.setValueAt(cell.getStringCellValue(), i, 1);
                        break;
 
                    case 2:
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        birthdate = sdf.format(cell.getDateCellValue());
                        //table.setValueAt(birthdate, i, 2);
                        
                        break;
                    case 3:
                        sex = cell.getStringCellValue();
                        //table.setValueAt(sex, i, 3);
                        break;
                    case 4:
                        address = cell.getStringCellValue();
                       // table.setValueAt(address, i, 4);
                        break; 
                    case 5:
                        
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           lpu_id = String.valueOf((int)cell.getNumericCellValue());
                          // table.setValueAt(lpu_id, i, 5);
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           lpu_id = cell.getStringCellValue();
                           //table.setValueAt(lpu_id, i, 5);
                           break; 
                        }
                        
                    case 6:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           crb_id = String.valueOf((int)cell.getNumericCellValue());
                          // table.setValueAt(crb_id, i, 6);
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           crb_id = cell.getStringCellValue();
                          // table.setValueAt(crb_id, i, 6);
                           break; 
                        } 
                    case 7:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           snils = String.valueOf((int)cell.getNumericCellValue());
                         //  table.setValueAt(snils, i, 7);
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           snils = cell.getStringCellValue();
                           //table.setValueAt(snils, i, 7);
                           break; 
                        } 
                    case 8:
                        String[] passport =  cell.getStringCellValue().split(" ");
                        //table.setValueAt(cell.getStringCellValue(), i, 8);
                        pass_ser = passport[0];
                        pass_num = passport[1];
                        break;
                    case 9:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           old_police = String.valueOf((int)cell.getNumericCellValue());
                          // table.setValueAt(old_police, i, 9);
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           old_police = cell.getStringCellValue();
                          // table.setValueAt(old_police, i, 9);
                           break; 
                        } 
                    case 10:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           new_police = String.valueOf((int)cell.getNumericCellValue());
                           //table.setValueAt(new_police, i, 10);
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           new_police = cell.getStringCellValue();
                           //table.setValueAt(new_police, i, 10);
                           break; 
                        } 
                    case 11:
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                           phoneNum = String.valueOf((int)cell.getNumericCellValue());
                           //table.setValueAt(phoneNum, i, 11);
                           break; 
                        }
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()){
                           phoneNum = cell.getStringCellValue();
                           //table.setValueAt(phoneNum, i, 11);
                           break; 
                        } 
                    default:
                        System.out.print("|");
                        break;
                }
            }       
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         String [] data = {String.valueOf(ID), surname +" "+ name+" "+ middleName,birthdate, sex,  
             address, lpu_id,crb_id, snils, pass_ser+ " "+ pass_num, old_police, new_police,phoneNum};
         model.addRow(data);
         
         removeAllFields();
        }
        
    }
    
    public void saveToDB(JTable table,Connection con){
        down = new PatientDownloading(con);
        int length = table.getRowCount();
        for (int i = 0; i < length; i++) {
             Object value = new Object();
            
             ID = Integer.parseInt(table.getValueAt(i, 0).toString());
             String[] fullName = table.getValueAt(i, 1).toString().split(" ");
             name = fullName[1];
             surname = fullName[0];
             middleName = fullName[2];
             
             sex = table.getValueAt(i, 3).toString();
             birthdate = table.getValueAt(i, 2).toString();
             address = table.getValueAt(i, 4).toString();
             lpu_id = table.getValueAt(i, 5).toString();
             value = table.getValueAt(i,6);
                if (value!=null)
                {
                    crb_id = value.toString();
                }
                else{
                    crb_id = " ";
                 }
             snils = table.getValueAt(i, 7).toString();
             String[] passport =  table.getValueAt(i, 8).toString().split(" ");
            pass_ser = passport[0];
            pass_num = passport[1];
            value = table.getValueAt(i,9);
            if (value!=null)
            {
                old_police = value.toString();
            }
            else{
                old_police = " ";
             }
            value = table.getValueAt(i,10);
            if (value!=null)
            {
                new_police = value.toString();
            }
            else{
                new_police = " ";
             }
            value = table.getValueAt(i,11);
            if (value!=null)
            {
                phoneNum = value.toString();
            }
            else{
                phoneNum = " ";
             }
            down.addPatient(ID, surname, name, middleName, sex, birthdate, address, lpu_id,crb_id);
            down.addPatientDocs(ID, snils, pass_ser, pass_num, old_police, new_police,phoneNum);
        }
        
    }
 
}
