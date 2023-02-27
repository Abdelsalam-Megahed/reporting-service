package me.readyplayer.reportingservice.report;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @NotNull
    @Min(1)
    private int productId;
    @Min(1)
    @Max(10000)
    private int quantity;
    private String note;
}
