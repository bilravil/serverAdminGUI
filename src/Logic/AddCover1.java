/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.FileOutputStream;
import java.io.IOException;
import sandbox.WrapToTest;
 
@WrapToTest
public class AddCover1 {
 

 
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     */
    public void manipulatePdf(String src, String dest,String cov)
        throws IOException, DocumentException {
        PdfReader cover = new PdfReader(cov);
        PdfReader reader = new PdfReader(src);
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dest));
        document.open();
        copy.addDocument(cover);
        copy.addDocument(reader);
        
        document.close();
        cover.close();
        reader.close();
    }
 
    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
}
