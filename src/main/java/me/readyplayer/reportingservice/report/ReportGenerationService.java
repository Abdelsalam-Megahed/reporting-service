package me.readyplayer.reportingservice.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import me.readyplayer.reportingservice.exception.CustomException;
import me.readyplayer.reportingservice.product.Product;
import me.readyplayer.reportingservice.product.ProductService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

@Service
@AllArgsConstructor
public class ReportGenerationService {
    private final ProductService productService;

    public InputStream generateReport(ReportRequest request) throws DocumentException, CustomException {
        Product product = productService.findProductById(request.getProductId());
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(createReportTitle());
        document.add(createReportTable(request, product));
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private Paragraph createReportTitle() {
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        Paragraph title = new Paragraph("Production report", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        return title;
    }

    private PdfPTable createReportTable(ReportRequest request, Product product) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3});
        table.setSpacingBefore(40);

        table.addCell("Product name");
        table.addCell(product.getName());

        table.addCell("Quantity");
        table.addCell(String.valueOf(request.getQuantity()));

        table.addCell("Unit price");
        table.addCell(product.getPrice() + " EUR");

        table.addCell("Total");
        table.addCell((request.getQuantity() * product.getPrice()) + " EUR");

        if (!request.getNote().isEmpty()) {
            table.addCell("Note");
            table.addCell(request.getNote());
        }

        table.addCell("Created at");
        table.addCell(new Date().toString());

        return table;
    }
}
