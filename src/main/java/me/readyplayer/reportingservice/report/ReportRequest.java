package me.readyplayer.reportingservice.report;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @NotNull(message = "Product id is invalid!")
    private int productId;
    @Min(1)
    private int quantity;
    private String note;
}
