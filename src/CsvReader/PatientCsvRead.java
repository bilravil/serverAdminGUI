/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CsvRead;

import ExtendedLogic.PatientDownloading;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Равиль
 */
public class PatientCsvRead {
    
    private  PatientDownloading down ;
    
    private String ID;
    private String CODE_REESTR_UL;
    private String CODE_MO;
    private String CODE_FAP;
    private String N_AREA;
    private String AREA_TYPE;
    private String ATTACH_TYPE;
    private String N_CLAIM;
    private String D_CLAIM;
    private String DOCTYPE;
    private String DOC;
    private String SNILS;
    private String FAM;
    private String IM;
    private String OT;
    private String SEX;
    private String DR;
    private String CODE_REESTR_SMO;
    private String ENP;
    private String POLICY;
    private String D_ATTACH;
    private String AOID;
    private String HOUSE;
    private String KORP;
    private String FLAT;
    private String ADDRESS;
    
    private void cleanAllFields(){
     ID = "";
     CODE_REESTR_UL = "";
     CODE_MO = "";
     CODE_FAP = "";
     N_AREA = "";
     AREA_TYPE = "";
     ATTACH_TYPE = "";
     N_CLAIM = "";
     D_CLAIM = "";
     DOCTYPE = "";
     DOC = "";
     SNILS = "";
     FAM = "";
     IM = "";
     OT = "";
     SEX = "";
     DR = "";
     CODE_REESTR_SMO = "";
     ENP = "";
     POLICY = "";
     D_ATTACH = "";
     AOID = "";
     HOUSE = "";
     KORP = "";
     FLAT = "";
     ADDRESS = "";
    }
    
    public void read(String filename,JTable table){
     List<String> lines = null;
     
        try {          
           lines = Files.readAllLines(Paths.get(filename));
           } catch (Exception e) {
            System.out.println(e);
        }   
            for (int i = 1; i < lines.size(); i++) {
                
                String line = lines.get(i);
                line = line.replace("\"", "");
                String [] patient_arr = line.split(System.getProperty("line.separator"));
                String [] result =  patient_arr[0].split(";");
                String [] data = new String[result.length+1];
                
                
                for (int j = 1; j <= result.length; j++) {                    
                    ID = result[0]+result[11]+result[12]+result[13]+result[15];
                    
//                    CODE_REESTR_UL = result[0];
//                    CODE_MO = result[1];
//                    CODE_FAP = result[2];
//                    N_AREA = result[3];
//                    AREA_TYPE = result[4];
//                    ATTACH_TYPE = result[5];
//                    N_CLAIM = result[6];
//                    D_CLAIM = result[7];
//                    DOCTYPE = result[8];
//                    DOC = result[9];
//                    SNILS = result[10];
//                    FAM = result[11];
//                    IM = result[12];
//                    OT = result[13];
//                    SEX  =result[14];
//                    DR = result[15];
//                    CODE_REESTR_SMO = result[16];
//                    ENP = result[17];
//                    POLICY = result[18];
//                    D_ATTACH = result[19];
//                    AOID = result[20];
//                    HOUSE = result[21];
//                    KORP = result[22];
//                    FLAT = result[23];
//                    ADDRESS = result[24];
                    data[j] = result[j-1];                   
                }   
                    data[0] = ID;
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(data);   
                    
            }     
    }
    
    
    public void insertIntoDB(JTable table,Connection con){
        down = new PatientDownloading(con);
        for (int i = 0; i < table.getRowCount(); i++) {
             Object value = new Object();
             cleanAllFields();
             
             ID = table.getValueAt(i, 0).toString();
             
             value = table.getValueAt(i, 1);
             if(value!=null){CODE_REESTR_UL = value.toString();}
             
             value = table.getValueAt(i, 2);
             if(value!=null){CODE_MO = value.toString();}
             
             value = table.getValueAt(i, 3);
             if(value!=null){CODE_FAP = value.toString();}
             
             value = table.getValueAt(i, 4);
             if(value!=null){N_AREA = value.toString();}
             
             value = table.getValueAt(i, 5);
             if(value!=null){AREA_TYPE = value.toString();}
             
             value = table.getValueAt(i, 6);
             if(value!=null){ATTACH_TYPE = value.toString();}
             
             value = table.getValueAt(i, 7);
             if(value!=null){N_CLAIM = value.toString();}
             
             value = table.getValueAt(i, 8);
             if(value!=null){D_CLAIM = value.toString();}
             
             value = table.getValueAt(i, 9);
             if(value!=null){DOCTYPE = value.toString();}
             
             value = table.getValueAt(i, 10);
             if(value!=null){DOC = value.toString();}
             
             value = table.getValueAt(i, 11);
             if(value!=null){SNILS = value.toString();}
             
             value = table.getValueAt(i, 12);
             if(value!=null){FAM = value.toString();}
             
             value = table.getValueAt(i, 13);
             if(value!=null){IM = value.toString();}
             
             value = table.getValueAt(i, 14);
             if(value!=null){OT = value.toString();}
             
             value = table.getValueAt(i, 15);
             if(value!=null){SEX = value.toString();}
             
             value = table.getValueAt(i, 16);
             if(value!=null){DR = value.toString();}
             
             value = table.getValueAt(i, 17);
             if(value!=null){CODE_REESTR_SMO = value.toString();}
             
             value = table.getValueAt(i, 18);
             if(value!=null){ENP = value.toString();}
             
             value = table.getValueAt(i, 19);
             if(value!=null){POLICY = value.toString();}
             
             value = table.getValueAt(i, 20);
             if(value!=null){D_ATTACH = value.toString();}
             
             value = table.getValueAt(i, 21);
             if(value!=null){AOID = value.toString();}
             
             value = table.getValueAt(i, 22);
             if(value!=null){HOUSE = value.toString();}
             
             value = table.getValueAt(i, 23);
             if(value!=null){KORP = value.toString();}
             
             value = table.getValueAt(i, 24);
             if(value!=null){FLAT = value.toString();}
             
             value = table.getValueAt(i, 25);
             if(value!=null){ADDRESS = value.toString();}
             
             down.addPatientToDirectory(ID, CODE_REESTR_UL, CODE_MO, CODE_FAP, N_AREA, AREA_TYPE, ATTACH_TYPE, N_CLAIM, D_CLAIM, DOCTYPE, DOC, SNILS, FAM, IM, OT, SEX, DR, CODE_REESTR_SMO, ENP, POLICY, D_ATTACH, AOID, HOUSE, KORP, FLAT, ADDRESS);
             down.UpdateStatus(ID);
        }
    }
    
    
    
}
