package gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.awt.Font;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;



public class PDFExport {


    public String Url="C:/wamp64/www/Pidev_Web/public/";

    // Necessite l'api java itextPDF
        public void exportPdf() throws BadElementException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tunmixfinal", "root", "root");
            Statement stmt = con.createStatement();
            // Read list of claims from database
            ResultSet query_set = stmt.executeQuery("SELECT r.libelle, r.description, r.date_reclamation, r.etat_reclamation, u.NomUser, u.PrenomUser, r.photo FROM reclamation r JOIN user u ON r.user_id = u.idUser");
            /* Step-2: Initialize PDF documents - logical objects */
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("ReclamationsList.pdf"));
            my_pdf_report.open();
            //we have four columns in our table
            PdfPTable my_report_table = new PdfPTable(7);
            //create a cell object
            PdfPCell table_cell;
            writePdfTableHeader(my_report_table);
            Paragraph paragraph = new Paragraph("Ce tableau représente la liste des réclamations");

            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            my_pdf_report.add(paragraph);
            my_report_table.setSpacingBefore(10);
            my_report_table.setSpacingAfter(10);


       while (query_set.next()) {
          String nom_utilisateur = query_set.getString("NomUser");
          table_cell = new PdfPCell(new Phrase(nom_utilisateur));
          my_report_table.addCell(table_cell);

          String prenom_utilisateur = query_set.getString("PrenomUser");
          table_cell = new PdfPCell(new Phrase(prenom_utilisateur));
          my_report_table.addCell(table_cell);

          String location_id = query_set.getString("libelle");
          table_cell = new PdfPCell(new Phrase(location_id));
          my_report_table.addCell(table_cell);

          String dept_id = query_set.getString("description");
          table_cell = new PdfPCell(new Phrase(Font.BOLD, dept_id));
          my_report_table.addCell(table_cell);

          String dept_name = query_set.getString("date_reclamation");
          table_cell = new PdfPCell(new Phrase(dept_name));
          my_report_table.addCell(table_cell);

          String manager_id = query_set.getString("etat_reclamation");
          table_cell = new PdfPCell(new Phrase(manager_id));
          my_report_table.addCell(table_cell);  
          
          String img = query_set.getString("photo");
   
          Image image = Image.getInstance(Url+img);   
          

           
                image.scalePercent(20);
                image.scaleAbsolute(20, 20);
                table_cell =new PdfPCell();
                table_cell.addElement(image);
                table_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                
my_report_table.addCell(table_cell);
//my_pdf_report.close();
                
}
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();
            query_set.close();
            stmt.close();
            con.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
        //Cette méthode va etre appelé par la méthode exportPDF 
    public void writePdfTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.PINK);
        cell.setPadding(5);

        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.BLACK);
        
        cell.setPhrase(new Phrase("Nom d'utilisateur", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prénom d'utilisateur", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Libellé", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date réclamation", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Etat réclamation", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Photo", font));
        table.addCell(cell); 
    }
}