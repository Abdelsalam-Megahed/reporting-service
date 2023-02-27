package me.readyplayer.reportingservice.report;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.readyplayer.reportingservice.exception.CustomException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.InputStream;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/generate-report")
@CrossOrigin
public class ReportController {
    private final ReportGenerationService reportingService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateReport(@RequestBody @Valid ReportRequest request) throws CustomException, IOException {
        InputStream inputStream = reportingService.generateReport(request);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }
}
