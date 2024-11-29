package com.example.projetjee.utils;

import com.example.projetjee.models.Grade;
import com.example.projetjee.models.Student;
import com.example.projetjee.models.StudentCourse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.projetjee.utils.DbConnect.*;

public class PdfGenerator {
    public static void generatBulltin(int id,int year) throws SQLException, ClassNotFoundException {

        List<StudentCourse> studentCourses = DbConnect.getStudentCoursesByYear(id,year);
        Student student = DbConnect.getStudent(id);

        String dest = "BULLTIN_"+student.getFirstName().toUpperCase()+"_"+student.getLastName().toUpperCase()+"_"+year+"_"+(year+1)+".pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                //Pour du text
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Année scolaire : "+year+"/"+(year+1));
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
                contentStream.showText(student.getLastName().toUpperCase()+" "+student.getFirstName());
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

                int xgrade = 60;
                int xname = 350;
                int yline = 650;
                float moyenneGene = 0;
                int nbCourse = 0;
                for (StudentCourse sc: studentCourses){

                    List<Grade> grades = sc.getGrades();
                    if (grades.size()>0) {
                        float moyenne = 0;
                        int nbg = grades.size();
                        for (Grade g:grades) {
                            moyenne += g.getGrade();
                        }
                        moyenne = moyenne/nbg;
                        moyenneGene += moyenne;
                        nbCourse++;
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.beginText();
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(xgrade, yline);
                        contentStream.showText(DbConnect.getCourseById(sc.getCourseId()).getName());
                        contentStream.endText();

                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.beginText();
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(xname, yline);
                        contentStream.showText(String.valueOf(moyenne));
                        contentStream.endText();

                        yline -= 20;
                    }
                }
                contentStream.setLineWidth(1);
                contentStream.moveTo(50, yline+14);
                contentStream.lineTo(550, yline+14);
                contentStream.stroke();

                moyenneGene = moyenneGene/nbCourse;
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(xgrade, yline);
                contentStream.showText("Moyenne générale");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(xname, yline);
                contentStream.showText(String.valueOf(moyenneGene));
                contentStream.endText();
            }

            document.save(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}