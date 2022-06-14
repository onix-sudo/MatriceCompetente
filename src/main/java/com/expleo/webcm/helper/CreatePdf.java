package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.UserSkill;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.Canvas;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The class used for the Search People PDF create and formatting
 * The iText library was used to create and manipulate the PDF file
 */

public class CreatePdf {
    /**
     * Creates a {@code ByteArrayInputStream}
     * @throws IOException  If an input or output exception occurred
     * @param   userSkills  the table cells content.
     * @param text the search term
     * @param evaluation the minimum evaluation filter
     */
    public ByteArrayInputStream getPdfAsByteArrayInputStream(List<UserSkill> userSkills, String text, String evaluation) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date dateobj = new Date();
        Paragraph paragraph = new Paragraph(df.format(dateobj) + "\nSearch keyword: " + text + "\nMinimum rating: " + evaluation + " \n\n");

        Header headerHandler = new Header(paragraph);
        Footer footerHandler = new Footer();

        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);

        // Write the total number of pages to the placeholder
        footerHandler.writeTotal(pdf);

        document.setMargins(100,30,10,30);

        Table table = new Table(new float[6]).useAllAvailableWidth();
        table.setMarginTop(0);
        table.setMarginBottom(0);

        table.addCell(makeCell("Nume", TextAlignment.CENTER).setBold().setBackgroundColor(new DeviceRgb(0, 39, 118)).setFontColor(new DeviceRgb(Color.white)));
        table.addCell(makeCell("Rol", TextAlignment.CENTER).setBold().setBackgroundColor(new DeviceRgb(0, 39, 118)).setFontColor(new DeviceRgb(Color.white)));
        table.addCell(makeCell("Numar matricol", TextAlignment.CENTER).setBold().setBackgroundColor(new DeviceRgb(0, 39, 118)).setFontColor(new DeviceRgb(Color.white)));
        table.addCell(makeCell("Competenta", TextAlignment.CENTER).setBold().setBackgroundColor(new DeviceRgb(0, 39, 118)).setFontColor(new DeviceRgb(Color.white)));
        table.addCell(makeCell("Categorie", TextAlignment.CENTER).setBold().setBackgroundColor(new DeviceRgb(0, 39, 118)).setFontColor(new DeviceRgb(Color.white)));
        table.addCell(makeCell("Evaluare", TextAlignment.CENTER).setBold().setBackgroundColor(new DeviceRgb(0, 39, 118)).setFontColor(new DeviceRgb(Color.white)));


        for (int i = 0; i < userSkills.size(); i++) {
            if(i%2 != 0) {
                table.addCell(makeCell(userSkills.get(i).getUser().getNume() + " " + userSkills.get(i).getUser().getPrenume(), TextAlignment.LEFT));
                table.addCell(makeCell(userSkills.get(i).getUser().getFunctie(), TextAlignment.LEFT));
                table.addCell(makeCell(String.valueOf(userSkills.get(i).getUser().getNumarMatricol()), TextAlignment.CENTER));
                table.addCell(makeCell(userSkills.get(i).getSkill().getNumeSkill(), TextAlignment.LEFT));
                table.addCell(makeCell(userSkills.get(i).getSkill().getCategorie(), TextAlignment.LEFT));
                table.addCell(makeCell(String.valueOf(userSkills.get(i).getEvaluation()), TextAlignment.CENTER));
            }else{
                table.addCell(makeCell(userSkills.get(i).getUser().getNume()+" "+userSkills.get(i).getUser().getPrenume(), TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(Color.LIGHT_GRAY)));
                table.addCell(makeCell(userSkills.get(i).getUser().getFunctie(), TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(Color.LIGHT_GRAY)));
                table.addCell(makeCell(String.valueOf(userSkills.get(i).getUser().getNumarMatricol()), TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(Color.LIGHT_GRAY)));
                table.addCell(makeCell(userSkills.get(i).getSkill().getNumeSkill(),TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(Color.LIGHT_GRAY)));
                table.addCell(makeCell(userSkills.get(i).getSkill().getCategorie(), TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(Color.LIGHT_GRAY)));
                table.addCell(makeCell(String.valueOf(userSkills.get(i).getEvaluation()), TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(Color.LIGHT_GRAY)));
            }
        }

        try {

            document.add(table.setMargins(0,20,40,20));
            pdf.close();
            document.close();

        } finally {
            writer.close();
            os.close();
        }

        return new ByteArrayInputStream(os.toByteArray());
    }

    /**
     * Creates customized cells for table styling purpose
     */
    private Cell makeCell(String cellName, TextAlignment textAlignment) {
        Cell cell = new Cell().add(new Paragraph(cellName));
        cell.setTextAlignment(textAlignment);
        return cell;
    }

    /**
     * The class responsible for the header of the pages
     */

    static protected class Header implements IEventHandler {
        private Paragraph header;

        public Header(Paragraph header) {
            this.header = header;
        }

        @Override
        public void handleEvent(com.itextpdf.kernel.events.Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();

            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();

            Canvas canvas = new Canvas(new PdfCanvas(page),pdf,pageSize);

            ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("hays.png"));
            Image image = new Image(data).scale((float)2.8, (float) 2.8);

            canvas.showTextAligned(header,
                    pageSize.getWidth() / 2 + 250,
                    pageSize.getTop() - 80, TextAlignment.RIGHT).add(image);
            canvas.close();
        }
    }

    /**
     * The class responsible for the footer of the pages
     */
    static protected class Footer implements IEventHandler {
         PdfFormXObject placeholder;
         float side = 20;
         float x = 50;
         float y = 25;
         float space = 4.5f;
         float descent = 3;

        public Footer() {
            placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
        }

        public void writeTotal(PdfDocument pdf) {
            Canvas canvas = new Canvas(placeholder, pdf);
            canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()),
                    0, descent, TextAlignment.LEFT);
            canvas.close();
        }

        @Override
        public void handleEvent(com.itextpdf.kernel.events.Event event) {

            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdf.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();

            PdfCanvas pdfCanvas = new PdfCanvas(page);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);

                    Paragraph p = new Paragraph().add("© HTSR | Restricted");
                    p.add(new Tab());
                    p.addTabStops(new TabStop(500, TabAlignment.RIGHT));
                    p.add(pageNumber +"/"+ pdf.getNumberOfPages());

            canvas.showTextAligned(p, x, y, TextAlignment.LEFT);

            canvas.close();

        }
    }

}
