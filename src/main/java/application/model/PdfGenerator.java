package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.net.client.Client;
import javafx.stage.DirectoryChooser;

public class PdfGenerator {
private static PdfGenerator generatore;
	
	public static PdfGenerator getInstance() {
		if(generatore == null)
			generatore = new PdfGenerator();
		return generatore;
	}
	
	public void pdfs(){
		Document my_pdf = new Document();
		
        try {
        	DirectoryChooser directoryChooser = new DirectoryChooser();
	        File selectedDirectory = directoryChooser.showDialog(null);
	        
            PdfWriter.getInstance(my_pdf, new FileOutputStream(selectedDirectory.getAbsolutePath() + "/downloadProduct.pdf"));
            my_pdf.open();
            
            /*String filename = getClass().getResource("/application/image/logoBasso.png").toString();
            Image image = Image.getInstance(filename);
            image.scaleAbsolute(new Rectangle(50,50));
            my_pdf.add(image);
            
            Paragraph paragrafo = new Paragraph("\n\n\nLista dei prodotti nel catalogo dell'app. \nDEVELOPED BY FRA_VUL69 \n\n\n", new Font(FontFamily.HELVETICA,18, Font.BOLDITALIC, new BaseColor(0, 0, 255)));
            my_pdf.add(paragrafo);*/
            
            // Creating image by file name
            String filename = getClass().getResource("/application/image/logoBasso.png").toString();
            Image image = Image.getInstance(filename);
            image.scaleAbsolute(new Rectangle(50,50));
            my_pdf.add(image);
                 
            Paragraph paragrafo = new Paragraph("\n\n\nLista dei prodotti nel catalogo dell'app. \nDEVELOPED BY FRA_VUL69 \n\n\n", new Font(FontFamily.HELVETICA,18, Font.BOLDITALIC, new BaseColor(0, 0, 255)));
            my_pdf.add(paragrafo);
            
            Vector<Product> prodotti = Client.getInstance().getProduct("");

            PdfPTable my_report_table = new PdfPTable(3);
	        
	        //create a cell object
	        PdfPCell table_cell;
	        
	        String nome = "Nome Prodotto";
	        String prezzo = "Prezzo prodotto";
	        String img = "Immagine";
	        table_cell = new PdfPCell(new Phrase(nome));
	        my_report_table.addCell(table_cell);
	        table_cell = new PdfPCell(new Phrase(prezzo));
	        my_report_table.addCell(table_cell);
	        table_cell = new PdfPCell(new Phrase(img));
	        my_report_table.addCell(table_cell);
	        
	        for(Product p : prodotti) {   
	            String n = p.getNomeProdotto();
	            table_cell=new PdfPCell(new Phrase(n));
	            my_report_table.addCell(table_cell);
	            String pre = p.getPrezzoGenerico() + "";
	            table_cell=new PdfPCell(new Phrase(pre));
	            my_report_table.addCell(table_cell);
	            
	            Image imm;
	            if(p.getImgProdotto() != null) {
	            	imm = Image.getInstance(p.getImgProdotto());
		            
	            }else {
	            	imm = Image.getInstance(getClass().getResource("/application/image/noImageProduct.png"));
	            }
	            imm.scaleToFit(100, 100);
	            table_cell=new PdfPCell(imm);
	            my_report_table.addCell(table_cell);
	            
	        }
	        
	        my_pdf.add(my_report_table);                       
	        my_pdf.close();
        } catch (FileNotFoundException e) {
        	System.out.println("File non trovato");
        } catch (DocumentException e) {
        	System.out.println("problemi col documento");
        	e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("problemi generici");
        }

	}
}