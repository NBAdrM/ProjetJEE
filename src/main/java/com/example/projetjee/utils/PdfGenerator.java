package com.example.projetjee.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfGenerator {
    //TODO Rajouter la liste des notes
    public static void generatBultin(){
        String dest = "example.pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                //Pour du text
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Année scolaire : YYYY/YYYY");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(350, 750);
                contentStream.showText("Bulletin de l'année");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(350, 730);
                contentStream.showText("NOM Prenom");
                contentStream.endText();

                //Pour un ligne
                contentStream.setLineWidth(1);
                contentStream.setStrokingColor((float) 0.2,(float) 0.2,(float) 0.2,(float)0.1);
                contentStream.moveTo(50, 700);
                contentStream.lineTo(550, 700);
                contentStream.stroke();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(150, 680);
                contentStream.showText("Matiere");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(350, 680);
                contentStream.showText("Note");
                contentStream.endText();

                contentStream.setLineWidth(1);
                contentStream.moveTo(50, 670);
                contentStream.lineTo(550, 670);
                contentStream.stroke();
            }

            document.save(dest);
            System.out.println("PDF créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
