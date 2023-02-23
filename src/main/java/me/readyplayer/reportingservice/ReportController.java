package me.readyplayer.reportingservice;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<InputStreamResource> generateReport(@RequestBody @Valid ReportRequest request) {
        InputStream in = reportingService.generateReport(request);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }
}
