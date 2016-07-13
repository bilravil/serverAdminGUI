/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import DB.StartDB;
import GUI.AdminJFrame;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Равиль
 */
public class DocsCreation {
    String log4jConfPath = "./src/resources/log4j.properties";
    GetServiceNorm norm = new GetServiceNorm();
    private boolean flagExistNorm;
    private String[] arrNorm;
    
    public DocsCreation() {
        PropertyConfigurator.configure(log4jConfPath);
    }
   private static final Logger log = Logger.getLogger(AdminJFrame.class);
    
   public void createPdfDoc(JTable Table, GetPatientData data, StartDB con, boolean flag, ArrayList<String> arrPatient,String crb){
        try {
            norm.getNorm(con.getConnection());
            int b = arrPatient.size();
            if(b == -1){
                return;
            }
            String path = System.getProperty("user.home");
                path = path+"//Desktop//";
                File theDir = new File(path,"Диспансеризация");
                // if the directory does not exist, create it
                if (!theDir.exists()) {

                    boolean result = false;

                    try{
                        theDir.mkdir();
                        result = true;
                    } 
                    catch(SecurityException se){
                        //handle it
                    }        
                    if(result) {    
                        System.out.println("DIR created");  
                    }
                }
            for (int k = 0; k < b; k++) {
            String id = arrPatient.get(k);//Table.getModel().getValueAt(k, 0).toString(); 
            data = new GetPatientData();
            data.getMainData(con.getConnection(), id, crb);
            data.getPatientDocs(con.getConnection(), id);
            String[] arr = null;
            String fname = data.getName().replaceAll(" ","") + data.getBirth();
            
            Document doc = new Document();
            String fileName =path+"\\Диспансеризация\\" + fname + ".pdf";
            String pdfName = "";
            FileOutputStream fos = new FileOutputStream(fileName);
            PdfWriter.getInstance(doc, fos); 
            doc.open();
            
            
            BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts\\ARIAL.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont bfn = BaseFont.createFont("C:\\Windows\\Fonts\\ARIALI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // титл
            Paragraph title = new Paragraph(data.getCrbName()+"\n",new Font(bf,16,Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            // участок 
            Paragraph lpu = new Paragraph("Участок №  "+ data.getLpu() ,new Font(bf,12));
            lpu.setAlignment(Element.ALIGN_RIGHT);
            doc.add(lpu);
            doc.add (new Phrase("____________________________________________________________________________________\n",new Font(bf, 11, Font.BOLD)));
            
            
            doc.add (new Phrase("ФИО: ",new Font(bf, 13, Font.BOLD)));
            doc.add (new Phrase(data.getName()+" ("+data.getSex()+")\n",new Font(bf, 13))); 
            doc.add (new Phrase("Дата рождения: ",new Font(bf, 13, Font.BOLD)));
            doc.add (new Phrase(data.getBirth()+"\n",new Font(bf, 13))); 
            doc.add (new Phrase("Дата проведения диспансеризации: ",new Font(bf, 13, Font.BOLD)));
            doc.add (new Phrase(data.getDate()+"\n",new Font(bf, 13))); 
            
            if(data.getSnils()!=null){
                doc.add (new Phrase("СНИЛС : ",new Font(bf, 13, Font.BOLD)));
                doc.add (new Phrase(data.getSnils()+"\n",new Font(bf, 13))); 
            }
            if(data.getDocs()!=null){
                doc.add (new Phrase("Документ: ",new Font(bf, 13, Font.BOLD)));
                doc.add (new Phrase(data.getDocs()+ " ", new Font(bf, 13))); ; 
            }
            if(data.getPolicy()!=null){
                doc.add (new Phrase("№ полиса: ",new Font(bf, 13, Font.BOLD)));
                doc.add (new Phrase(data.getPolicy()+"; ",new Font(bf, 13))); 
            }
            
            if(data.getPhone()!=null){
                doc.add (new Phrase("Номер телефона ",new Font(bf, 13, Font.BOLD)));
                doc.add (new Phrase(data.getPhone()+"; ",new Font(bf, 13))); 
            }
            // проведенные услуги
            
            data.getDoneServiceCount(con.getConnection(), id);
            data.getDoneServiceData(con.getConnection(), id);
            // data.getDoneServiceProp(con.getConnection(), id,data.getService_code().get(k));
            // проведенные услуги
            doc.add(new Paragraph("\nПроведенные услуги: \n",new Font(bf, 16, Font.BOLD)));   
            
            for (int i = 0; i < data.getCount(); i++) {
                data.getDoneServiceProp(con.getConnection(), id,data.getService_code().get(i));
                doc.add(new Phrase(data.getService_name().get(i)+ " : \n" ,new Font(bf, 12, Font.BOLD)));
                if(data.getService_result().get(i)!= null){
                    arr = data.getService_result().get(i).split(";");
                
                    for (int l = 0; l < norm.getCode().size(); l++) {
                        if (data.getService_code().get(i).equals(norm.getCode().get(l)) ) {
                            flagExistNorm = true;
                            if ("Ж".equals(data.getSex())) 
                                arrNorm = norm.getNormsWoman().get(l).split(";");
                            else
                                if ("М".equals(data.getSex())) {
                                    arrNorm = norm.getNormsMan().get(l).split(";");
                                }
                                
                            
                            break;
                        }  
                        else
                            flagExistNorm = false;
                    }    
                

                             
                //<editor-fold defaultstate="collapsed" desc="старая версия ">
                //                switch(data.getService_name().get(i)){            
//                    
//                    case "01.Опрос(анкетирование)":
//                    doc.add(new Phrase("Курите ли вы? - " +arr[0]+ " ;\n " ,new Font(bf, 12)));
//                    break;
//                    
//                    case "02.Антропометрия":                 
//                        doc.add(new Phrase("Рост - " + arr[0] +" ; ",new Font(bf, 12)));
//                        doc.add(new Phrase("Вес - " + arr[2] +" ; ",new Font(bf, 12)));
//                        doc.add(new Phrase("Окружность талии - " + arr[1] +" ;\n ",new Font(bf, 12)));               
//                        break; 
//
//                    case "03.Измерение артериального давления":                 
//                        doc.add(new Phrase("Верхнее артериальное давление - " + arr[0] +" ; ",new Font(bf, 12)));
//                        doc.add(new Phrase("Нижнее артериальное давление - " + arr[1] +" ;",new Font(bf, 12)));
//                        doc.add(new Phrase("Пульс - " + arr[2] +" ;\n ",new Font(bf, 12)));
//                        break; 
//
//                     case "06.Электрокардиография в покое" :                 
//                            
//                     {   
//                            doc.add(new Phrase("Прикреплен файл " +";\n ",new Font(bf, 12)));
//                            pdfName = "C:\\MDKTemp\\"+fname+"ECG.pdf";
//                            try (OutputStream targetFile = new FileOutputStream(pdfName)) {
//                            targetFile.write(data.getPDFData(con.getConnection(), id));
//                            targetFile.close();
//                            log.info("pdf файл для "+id+ "сохранен");
//                            } catch (Exception ex) {
//                                    log.error(ex, ex);                            
//                            }
//                        }
//                     break;         
//
//                     case "08.Флюорография легких":                 
//                        doc.add(new Phrase("Патологии выявлены? - " + arr[0] +" ;\n",new Font(bf, 12)));               
//                        break; 
//
//                     case "13.Общий анализ мочи":                 
//                        doc.add(new Phrase("\nГлюкоза (моча), ммоль/л - " + arr[0] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Протеин, г/л- " + arr[1] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Кетоны, ммоль/л - " + arr[2] +" - \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Эритроциты (моча),кол-во/мкл - " + arr[3] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Лейкоциты (моча), кол-во/мкл - " + arr[4] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Удельная плотность - " + arr[5] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("pH - " + arr[6] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Уробилиноген, мкмоль/л - " + arr[7] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Аскорбиновая кислота, г/л - " + arr[8] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Нитриты - " + arr[9] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Билирубен (моча), мкмоль/л - " + arr[10] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Цвет - " + arr[11] +" ; \n",new Font(bf, 12)));
//                        doc.add(new Phrase("Мутность - " + arr[12] +" ;\n",new Font(bf, 12)));
//                        break; 
//
//                        case "16.Измерение внутриглазного давления":                 
//                        doc.add(new Phrase("Правый - " + arr[0] +" ;",new Font(bf, 12)));
//                        doc.add(new Phrase("Левый - " + arr[1] +" ;",new Font(bf, 12)));                
//                        break;
//                    } 
//</editor-fold>    
//            
                    int arrSize = arr.length;     
                    for (int j = 0 ; (j < arrSize ); j++) {
                        
                        if (data.getService_name().get(i).contains("Электрокардиография") || data.getService_name().get(i).contains("ЭКГ")) {
                            doc.add(new Phrase("Прикреплен файл " +";\n ",new Font(bf, 12)));
                            pdfName = "C:\\MDKTemp\\"+fname+"ECG.pdf";
                            try (OutputStream targetFile = new FileOutputStream(pdfName)) {
                                targetFile.write(data.getPDFData(con.getConnection(), id));
                                targetFile.close();                           
                                log.info("pdf файл для "+id+ "сохранен");
                               
                                break;
                            } catch (Exception ex) {
                                    log.error(ex, ex);                            
                            }
                        }
                    if (flagExistNorm) {
                        String[] border;
                        border = new String[2];

                        border = arrNorm[j].split(" - ");
                        if (Float.parseFloat(arr[j]) >= Float.parseFloat(border[0])) {
                            if (Float.parseFloat(arr[j]) <= Float.parseFloat(border[1])) {
                                // в норме все

                                doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ;\n",new Font(bf, 12)));
                            }
                            else{
                            // не в норме вывод
                                doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ; ",new Font(bf, 12, Font.UNDERLINE)));
                                if (border[0].equals(border[1])) {
                                    doc.add(new Phrase("! (Норма "+border[0]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
                                }
                                else
                                    doc.add(new Phrase("! (Норма "+border[0]+" - "+border[1]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
                            }
                        }
                        else{
                        // не в норме вывод
                        doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ; ",new Font(bf, 12, Font.UNDERLINE)));
                            if (border[0].equals(border[1])) {
                                doc.add(new Phrase("! (Норма "+border[0]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
                            }
                            else
                                doc.add(new Phrase("! (Норма "+border[0]+" - "+border[1]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
                        }
                    }
                    else
                        doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ;\n",new Font(bf, 12)));
                        
                    }
                    
                }
            }
                // назначенные услуги
               data.getAppoitedServiceCount(con.getConnection(), id);
               data.getAppServiceData(con.getConnection(), id); 
               doc.add(new Phrase("Назначенные услуги :\n",new Font(bf, 16,Font.BOLD)));         
               for (int j = 0; j < data.getCount(); j++) {
                    if(data.getService_result().get(j) != null){
                       doc.add(new Phrase(data.getService_name().get(j)+" - ",new Font(bf, 12,Font.BOLD)));                    
                       doc.add(new Phrase(data.getService_result().get(j)+" ; ",new Font(bf, 12)));   
                    }
                    if(data.getService_result().get(j) == null){
                        doc.add(new Phrase(data.getService_name().get(j)+" ; ",new Font(bf, 12,Font.BOLD)));   
                    }
                }
                    // отказ
                data.getRenServiceCount(con.getConnection(), id);
                data.getRenServiceData(con.getConnection(), id);
                   doc.add(new Phrase("\nОтказ : ",new Font(bf, 16, Font.BOLD)));               
                  for (int i = 0; i < data.getCount(); i++) {
                      if(data.getService_name().get(i) == null){
                          doc.add(new Phrase("Нет ",new Font(bf, 12)));                          
                      }
                       doc.add(new Phrase(data.getService_name().get(i)+" ; ",new Font(bf, 12, Font.BOLD)));   
                  }
                  // ранее
                data.getEarlierServiceCount(con.getConnection(), id);
                data.getEarServiceData(con.getConnection(), id);            
                doc.add(new Phrase("\nВыполнено ранее : ",new Font(bf, 16,  Font.BOLD)));               
                  for (int i = 0; i < data.getCount(); i++) {     
                     if(data.getService_result().get(i)!= null){
                          doc.add(new Phrase(data.getService_name().get(i)+" - ",new Font(bf, 12, Font.BOLD)));                    
                          doc.add(new Phrase(data.getService_date().get(i)+" ; ",new Font(bf, 12)));  
                      }
                      if(data.getService_result().get(i)== null){
                           doc.add(new Phrase(data.getService_name().get(i)+" ;",new Font(bf, 12)));  
                      }                                          
                
                }
                    doc.close();  
                    if(!flag){
                      if(!pdfName.equals("")){
                        AddCover1 cov = new AddCover1();
                        String tempFile = fileName;
                        String ECGFile = pdfName;
                        String res = path+"//Диспансеризация//"+fname+"ECG.pdf";
                        File file = new File(res);
                        file.getParentFile().mkdirs();
                        cov.manipulatePdf(ECGFile, res,tempFile); 
                        File dfile = new File(fileName);
                        if(dfile.delete()){
                            System.out.println(dfile.getName() + " is deleted!");
                        }else{
                                System.out.println("Delete operation is failed.");
                        }                    

                    }  
                    }
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        
    }
    
//   public void createPrintPdfDoc(JTable Table, GetPatientData data,StartDB con){
//          try {
//              norm.getNorm(con.getConnection());
//            int b = Table.getRowCount();
//            if(b == -1){
//                return;
//            }
//            for (int k = 0; k < b; k++) {
//            String id = Table.getModel().getValueAt(k, 0).toString(); 
//            data = new GetPatientData();
//            data.getMainData(con.getConnection(), id);
//            data.getPatientDocs(con.getConnection(), id);
//            String[] arr = null;
//            String fname = data.getName().replaceAll(" ","") + data.getBirth();
//            
//            Document doc = new Document();
//            String fileName ="C:\\" + fname + ".pdf";
//            String pdfName = "";
//            FileOutputStream fos = new FileOutputStream(fileName);
//            PdfWriter.getInstance(doc, fos); 
//            doc.open();
//            
//            
//            BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts\\ARIAL.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            BaseFont bfn = BaseFont.createFont("C:\\Windows\\Fonts\\ARIALI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            // титл
//            Paragraph title = new Paragraph(data.getCrbName()+"\n",new Font(bf,16,Font.BOLD));
//            title.setAlignment(Element.ALIGN_CENTER);
//            doc.add(title);
//            // участок 
//            Paragraph lpu = new Paragraph("Участок №  "+ data.getLpu() ,new Font(bf,12));
//            lpu.setAlignment(Element.ALIGN_RIGHT);
//            doc.add(lpu);
//            doc.add (new Phrase("____________________________________________________________________________________\n",new Font(bf, 11, Font.BOLD)));
//            
//            
//            doc.add (new Phrase("ФИО: ",new Font(bf, 13, Font.BOLD)));
//            doc.add (new Phrase(data.getName()+" ("+data.getSex()+")\n",new Font(bf, 13))); 
//            doc.add (new Phrase("Дата рождения: ",new Font(bf, 13, Font.BOLD)));
//            doc.add (new Phrase(data.getBirth()+"\n",new Font(bf, 13))); 
//            doc.add (new Phrase("Дата проведения диспансеризации: ",new Font(bf, 13, Font.BOLD)));
//            doc.add (new Phrase(data.getDate()+"\n",new Font(bf, 13))); 
//            
//            if(data.getSnils()!=null){
//                doc.add (new Phrase("СНИЛС : ",new Font(bf, 13, Font.BOLD)));
//                doc.add (new Phrase(data.getSnils()+"\n",new Font(bf, 13))); 
//            }
//            if(data.getPass_ser()!=null){
//                doc.add (new Phrase("Паспорт серия: ",new Font(bf, 13, Font.BOLD)));
//                doc.add (new Phrase(data.getPass_ser()+"; номер: ",new Font(bf, 13))); 
//                doc.add (new Phrase(data.getPass_num()+"; \n",new Font(bf, 13))); 
//            }
//            if(data.getOldPol()!=null){
//                doc.add (new Phrase("№ полиса(старого образца) : ",new Font(bf, 13, Font.BOLD)));
//                doc.add (new Phrase(data.getOldPol()+";  ",new Font(bf, 13))); 
//            }
//            if(data.getNewPol()!=null){
//                doc.add (new Phrase("№ полиса(нового образца) : ",new Font(bf, 13, Font.BOLD)));
//                doc.add (new Phrase(data.getNewPol()+"; ",new Font(bf, 13))); 
//            }
//            doc.add(new Paragraph("\nПроведенные услуги: \n",new Font(bf, 14, Font.BOLD)));    
//            // проведенные услуги
//            for (int i = 0; i < data.getCount(); i++) {
//                data.getDoneServiceProp(con.getConnection(), id,data.getService_code().get(i));
//                doc.add(new Phrase(data.getService_name().get(i)+ " : \n" ,new Font(bf, 12, Font.BOLD)));
//                if(data.getService_result().get(i)!= null){
//                    arr = data.getService_result().get(i).split(";");
//                for (int l = 0; l < norm.getCode().size(); l++) {
//                        if (data.getService_code().get(i).equals(norm.getCode().get(l)) ) {
//                            flagExistNorm = true;
//                            if ("Ж".equals(data.getSex())) 
//                                arrNorm = norm.getNormsWoman().get(l).split(";");
//                            else
//                                arrNorm = norm.getNormsMan().get(l).split(";");
//                            break;
//                        }  
//                        else
//                            flagExistNorm = false;
//                    }
//                             
//                //<editor-fold defaultstate="collapsed" desc="старая версия ">
//                //                switch(data.getService_name().get(i)){            
////                    
////                    case "01.Опрос(анкетирование)":
////                    doc.add(new Phrase("Курите ли вы? - " +arr[0]+ " ;\n " ,new Font(bf, 12)));
////                    break;
////                    
////                    case "02.Антропометрия":                 
////                        doc.add(new Phrase("Рост - " + arr[0] +" ; ",new Font(bf, 12)));
////                        doc.add(new Phrase("Вес - " + arr[2] +" ; ",new Font(bf, 12)));
////                        doc.add(new Phrase("Окружность талии - " + arr[1] +" ;\n ",new Font(bf, 12)));               
////                        break; 
////
////                    case "03.Измерение артериального давления":                 
////                        doc.add(new Phrase("Верхнее артериальное давление - " + arr[0] +" ; ",new Font(bf, 12)));
////                        doc.add(new Phrase("Нижнее артериальное давление - " + arr[1] +" ;",new Font(bf, 12)));
////                        doc.add(new Phrase("Пульс - " + arr[2] +" ;\n ",new Font(bf, 12)));
////                        break; 
////
////                     case "06.Электрокардиография в покое" :                 
////                            
////                     {   
////                            doc.add(new Phrase("Прикреплен файл " +";\n ",new Font(bf, 12)));
////                            pdfName = "C:\\MDKTemp\\"+fname+"ECG.pdf";
////                            try (OutputStream targetFile = new FileOutputStream(pdfName)) {
////                            targetFile.write(data.getPDFData(con.getConnection(), id));
////                            targetFile.close();
////                            log.info("pdf файл для "+id+ "сохранен");
////                            } catch (Exception ex) {
////                                    log.error(ex, ex);                            
////                            }
////                        }
////                     break;         
////
////                     case "08.Флюорография легких":                 
////                        doc.add(new Phrase("Патологии выявлены? - " + arr[0] +" ;\n",new Font(bf, 12)));               
////                        break; 
////
////                     case "13.Общий анализ мочи":                 
////                        doc.add(new Phrase("\nГлюкоза (моча), ммоль/л - " + arr[0] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Протеин, г/л- " + arr[1] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Кетоны, ммоль/л - " + arr[2] +" - \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Эритроциты (моча),кол-во/мкл - " + arr[3] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Лейкоциты (моча), кол-во/мкл - " + arr[4] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Удельная плотность - " + arr[5] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("pH - " + arr[6] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Уробилиноген, мкмоль/л - " + arr[7] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Аскорбиновая кислота, г/л - " + arr[8] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Нитриты - " + arr[9] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Билирубен (моча), мкмоль/л - " + arr[10] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Цвет - " + arr[11] +" ; \n",new Font(bf, 12)));
////                        doc.add(new Phrase("Мутность - " + arr[12] +" ;\n",new Font(bf, 12)));
////                        break; 
////
////                        case "16.Измерение внутриглазного давления":                 
////                        doc.add(new Phrase("Правый - " + arr[0] +" ;",new Font(bf, 12)));
////                        doc.add(new Phrase("Левый - " + arr[1] +" ;",new Font(bf, 12)));                
////                        break;
////                    } 
////</editor-fold>              
//                    int arrSize = arr.length;     
//                    for (int j = 0 ; (j < arrSize ); j++) {
//                        
//                        if (data.getService_name().get(i).contains("Электрокардиография") || data.getService_name().get(i).contains("ЭКГ")) {
//                            doc.add(new Phrase("Прикреплен файл " +";\n ",new Font(bf, 12)));
//                            pdfName = "C:\\MDKTemp\\"+fname+"ECG.pdf";
//                            try (OutputStream targetFile = new FileOutputStream(pdfName)) {
//                                targetFile.write(data.getPDFData(con.getConnection(), id));
//                                targetFile.close();                           
//                                log.info("pdf файл для "+id+ "сохранен");
//                               
//                                break;
//                            } catch (Exception ex) {
//                                    log.error(ex, ex);                            
//                            }
//                        }
//                    if (flagExistNorm) {
//                        String[] border;
//
//                        border = arrNorm[j].split(" - ");
//                        if (Float.parseFloat(arr[j]) >= Float.parseFloat(border[0])) {
//                            if (Float.parseFloat(arr[j]) <= Float.parseFloat(border[1])) {
//                                // в норме все
//
//                                doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ;\n",new Font(bf, 12)));
//                            }
//                            else{
//                            // не в норме вывод
//                                doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ; ",new Font(bf, 12, Font.UNDERLINE)));
//                                if (border[0].equals(border[1])) {
//                                    doc.add(new Phrase("! (Норма "+border[0]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
//                                }
//                                else
//                                    doc.add(new Phrase("! (Норма "+border[0]+" - "+border[1]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
//                            }
//                        }
//                        else{
//                        // не в норме вывод
//                       // doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ; ! (Норма "+border[0]+" - "+border[1]+");\n",new Font(bfn, 12)));
//                        doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ; ",new Font(bf, 12, Font.UNDERLINE)));
//                            if (border[0].equals(border[1])) {
//                                    doc.add(new Phrase("! (Норма "+border[0]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
//                                }
//                                else
//                                    doc.add(new Phrase("! (Норма "+border[0]+" - "+border[1]+");\n",new Font(bfn, 12, Font.UNDERLINE)));
//                        }
//                    }
//                    else
//                        doc.add(new Phrase(data.getService_prop().get(j)+ " - " + arr[j] +" ;\n",new Font(bf, 12)));
//                        
//                    }
//                    
//                }
//            }
//                // назначенные услуги
//               data.getAppoitedServiceCount(con.getConnection(), id);
//               data.getAppServiceData(con.getConnection(), id); 
//               doc.add(new Phrase("Назначенные услуги :\n",new Font(bf, 16,Font.BOLD)));         
//               for (int j = 0; j < data.getCount(); j++) {
//                    if(data.getService_result().get(j) != null){
//                       doc.add(new Phrase(data.getService_name().get(j)+" - ",new Font(bf, 12,Font.BOLD)));                    
//                       doc.add(new Phrase(data.getService_result().get(j)+" ; ",new Font(bf, 12)));   
//                    }
//                    if(data.getService_result().get(j) == null){
//                        doc.add(new Phrase(data.getService_name().get(j)+" ; ",new Font(bf, 12,Font.BOLD)));   
//                    }
//                }
//                    // отказ
//                data.getRenServiceCount(con.getConnection(), id);
//                data.getRenServiceData(con.getConnection(), id);
//                   doc.add(new Phrase("\nОтказ : ",new Font(bf, 16, Font.BOLD)));               
//                  for (int i = 0; i < data.getCount(); i++) {
//                      if(data.getService_name().get(i) == null){
//                          doc.add(new Phrase("Нет ",new Font(bf, 12)));                          
//                      }
//                       doc.add(new Phrase(data.getService_name().get(i)+" ; ",new Font(bf, 12, Font.BOLD)));   
//                  }
//                  // ранее
//                data.getEarlierServiceCount(con.getConnection(), id);
//                data.getEarServiceData(con.getConnection(), id);            
//                doc.add(new Phrase("\nВыполнено ранее : ",new Font(bf, 16,  Font.BOLD)));               
//                  for (int i = 0; i < data.getCount(); i++) {     
//                     if(data.getService_result().get(i)!= null){
//                          doc.add(new Phrase(data.getService_name().get(i)+" - ",new Font(bf, 12, Font.BOLD)));                    
//                          doc.add(new Phrase(data.getService_date().get(i)+" ; ",new Font(bf, 12)));  
//                      }
//                      if(data.getService_result().get(i)== null){
//                           doc.add(new Phrase(data.getService_name().get(i)+" ;",new Font(bf, 12)));  
//                      }                                          
//                
//                }
//                    doc.close();                   
//                    if(!pdfName.equals("")){
//                    AddCover1 cov = new AddCover1();
//                    String tempFile = fileName;
//                    String ECGFile = pdfName;
//                    String res = "C:\\"+fname+"ECG.pdf";
//                    File file = new File(res);
//                    file.getParentFile().mkdirs();
//                    cov.manipulatePdf(ECGFile, res,tempFile); 
//                    File dfile = new File(fileName);
//                    if(dfile.delete()){
//    			System.out.println(dfile.getName() + " is deleted!");
//                    }else{
//                            System.out.println("Delete operation is failed.");
//                    }                       
//                    
//                    File pfile = new File(res); 
//                    try {
//                        Desktop.getDesktop().print(pfile);
//                        Thread.sleep(2000);
//                    } catch (IOException ex) {
//                                log.error(ex, ex);                   
//                    }
//                    }else{
//                    File pfile = new File(fileName); 
//                    try {
//                        Desktop.getDesktop().print(pfile);
//                        Thread.sleep(2000);
//                    } catch (IOException ex) {
//                         log.error(ex, ex);
//                        }
//                    }
//            }
//        } catch (Exception e) {
//            log.error(e, e);
//        }
//        
//    }

   /*public void createPdfDoc(JTable Table, GetPatientData data, StartDB con, boolean b, ArrayList<String> arrPatient) {
   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }*/
}
