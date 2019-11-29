package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.UserSkill;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CreatePdf {

    public ByteArrayInputStream getPdfAsByteArrayInputStream(List<UserSkill> userSkills, String text, String evaluation) throws IOException {
        com.itextpdf.layout.element.List listNume = new com.itextpdf.layout.element.List();
        com.itextpdf.layout.element.List listSkill = new com.itextpdf.layout.element.List();
        com.itextpdf.layout.element.List listEvaluare = new com.itextpdf.layout.element.List();

        for (UserSkill temp : userSkills) {
            listNume.add(temp.getUser().toString());
            listSkill.add(temp.getSkill().toString());
//            listEvaluare.add();
        }
        ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("expleoImg.png"));
        Image image = new Image(data);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        try {
            Paragraph paragraph = new Paragraph("View People with skill:" + text + " and evaluation from:" + evaluation + "\n\n");

            document.add(image);
            document.add(paragraph);
            document.add(listNume);
            document.add(listSkill);

            pdf.close();
            document.close();

        } finally {
            writer.close();
            os.close();
        }

        return new ByteArrayInputStream(os.toByteArray());
    }
}
