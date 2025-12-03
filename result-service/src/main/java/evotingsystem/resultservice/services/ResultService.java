package evotingsystem.resultservice.services;

import evotingsystem.resultservice.dtos.ResultDTO;
import evotingsystem.resultservice.dtos.StatisticsDTO;

import java.util.List;

public interface ResultService {
    void calculateResults();
    List<ResultDTO> getResults();
    StatisticsDTO getStatistics();
}