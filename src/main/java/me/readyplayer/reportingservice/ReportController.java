package me.readyplayer.reportingservice;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/generate-report")
@CrossOrigin
public class ReportController {
    private final ReportGenerationService reportingService;

    @PostMapping
    public ResponseEntity<ReportResponse> generateReport(@RequestBody @Valid ReportRequest request) {
        ReportResponse response = reportingService.generateReport(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
