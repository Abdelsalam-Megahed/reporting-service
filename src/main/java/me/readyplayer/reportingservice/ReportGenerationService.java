package me.readyplayer.reportingservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportGenerationService {
    public ReportResponse generateReport(ReportRequest request){
        System.out.println(request);

        ReportResponse response = new ReportResponse("https://readyplayer.me/");

        return response;
    }
}
