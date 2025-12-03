package evotingsystem.resultservice.controllers;

import evotingsystem.resultservice.dtos.ResultDTO;
import evotingsystem.resultservice.dtos.StatisticsDTO;
import evotingsystem.resultservice.services.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateResults() {
        resultService.calculateResults();
        return ResponseEntity.ok("Results calculated successfully");
    }

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getResults() {
        return ResponseEntity.ok(resultService.getResults());
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDTO> getStatistics() {
        return ResponseEntity.ok(resultService.getStatistics());
    }
}