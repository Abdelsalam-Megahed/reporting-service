package me.readyplayer.reportingservice;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@AllArgsConstructor
public class ReportGenerationService {
    public InputStream generateReport(ReportRequest request) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        document.add(createReportTitle());

        document.add(createReportTable(request));

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private Paragraph createReportTitle() {
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        Paragraph title = new Paragraph("Custom report", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        return title;
    }

    private PdfPTable createReportTable(ReportRequest request) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3});
        table.setSpacingBefore(40);

        table.addCell("Product name");
        table.addCell(request.getProductName());

        table.addCell("Product description");
        table.addCell(request.getDescription());

        table.addCell("Quantity");
        table.addCell(String.valueOf(request.getQuantity()));

        table.addCell("Unit price");
        table.addCell(request.getUnitPrice() + request.getCurrency());

        table.addCell("Total");
        table.addCell((request.getQuantity() * request.getUnitPrice()) + request.getCurrency());

        return table;
    }
}
