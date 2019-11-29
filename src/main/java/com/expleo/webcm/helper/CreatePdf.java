package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.UserSkill;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;


import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreatePdf {

    public ByteArrayInputStream getPdfAsByteArrayInputStream(List<UserSkill> userSkills, String text, String evaluation) throws IOException {

        ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("expleoImg.png"));
        Image image = new Image(data).scale(2,2);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(10,30,10,30);

        Table table = new Table(new float[6]).useAllAvailableWidth();
        table.setMarginTop(0);
        table.setMarginBottom(0);

        Cell antet = new Cell(1, 10).add(new Paragraph("Rezultate Cautare"));


        antet.setTextAlignment(TextAlignment.CENTER);
        antet.setPadding(5);
        antet.setBackgroundColor(new DeviceRgb(111, 66, 193));
        antet.setFontColor(new DeviceRgb(Color.white));
        table.addCell(antet);

        table.addCell(makeCell("Nume", TextAlignment.CENTER).setBold());
        table.addCell(makeCell("Rol", TextAlignment.CENTER).setBold());
        table.addCell(makeCell("Numar matricol", TextAlignment.CENTER).setBold());
        table.addCell(makeCell("Competenta", TextAlignment.CENTER).setBold());
        table.addCell(makeCell("Categorie", TextAlignment.CENTER).setBold());
        table.addCell(makeCell("Evaluare", TextAlignment.CENTER).setBold());


        for (int i = 0; i < userSkills.size(); i++) {
            table.addCell(makeCell(userSkills.get(i).getUser().getNume()+" "+userSkills.get(i).getUser().getPrenume(), TextAlignment.LEFT));
            table.addCell(makeCell(userSkills.get(i).getUser().getFunctie(), TextAlignment.LEFT));
            table.addCell(makeCell(String.valueOf(userSkills.get(i).getUser().getNumarMatricol()), TextAlignment.CENTER));
            table.addCell(makeCell(userSkills.get(i).getSkill().getNumeSkill(),TextAlignment.LEFT));
            table.addCell(makeCell(userSkills.get(i).getSkill().getCategorie(), TextAlignment.LEFT));
            table.addCell(makeCell(String.valueOf(userSkills.get(i).getEvaluation()), TextAlignment.CENTER));
        }

        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            Paragraph paragraph = new Paragraph(df.format(dateobj) + "\nAngajati cu competenta:" + text + " si evaluare >" + evaluation + " \n\n");

            document.add(image);
            document.add(paragraph);
            document.add(table);
            pdf.close();
            document.close();

        } finally {
            writer.close();
            os.close();
        }

        return new ByteArrayInputStream(os.toByteArray());
    }

    private Cell makeCell(String cellName, TextAlignment textAlignment) {
        Cell cell = new Cell().add(new Paragraph(cellName));
        cell.setTextAlignment(textAlignment);
        return cell;
    }
}
