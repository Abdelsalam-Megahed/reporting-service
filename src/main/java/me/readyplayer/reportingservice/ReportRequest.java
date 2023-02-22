package me.readyplayer.reportingservice;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @NotBlank(message = "Product name is invalid!")
    private String productName;
    @Min(1)
    private int quantity;
    @Min(200)
    @Max(4000)
    private float unitPrice;
    @NotBlank(message = "Currency is invalid!")
    private String currency;
    @NotBlank(message = "Description is invalid!")
    private String description;
}
