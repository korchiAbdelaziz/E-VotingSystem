package evotingsystem.resultservice.services;

import evotingsystem.resultservice.dtos.ResultDTO;
import evotingsystem.resultservice.dtos.StatisticsDTO;
import evotingsystem.resultservice.entities.Result;
import evotingsystem.resultservice.feignclients.VoteClient;
import evotingsystem.resultservice.feignclients.models.VoteDTO;
import evotingsystem.resultservice.repositories.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final VoteClient voteClient;

    @Override
    public void calculateResults() {
        List<VoteDTO> allVotes = voteClient.getAllVotes();

        // Compter les votes par candidat
        Map<Long, Long> voteCounts = allVotes.stream()
                .collect(Collectors.groupingBy(
                        VoteDTO::getCandidateId,
                        Collectors.counting()
                ));

        // Sauvegarder les résultats
        voteCounts.forEach((candidateId, count) -> {
            Result result = resultRepository
                    .findByCandidateId(candidateId)
                    .orElse(new Result());

            result.setCandidateId(candidateId);
            result.setTotalVotes(count);
            resultRepository.save(result);
        });
    }

    @Override
    public List<ResultDTO> getResults() {
        List<Result> results = resultRepository.findAll();
        Long totalVotes = results.stream()
                .mapToLong(Result::getTotalVotes)
                .sum();

        return results.stream()
                .map(r -> {
                    ResultDTO dto = new ResultDTO();
                    dto.setCandidateId(r.getCandidateId());
                    dto.setTotalVotes(r.getTotalVotes());
                    dto.setPercentage(totalVotes > 0 ?
                            (r.getTotalVotes() * 100.0) / totalVotes : 0.0);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public StatisticsDTO getStatistics() {
        List<ResultDTO> results = getResults();
        Long totalVotes = results.stream()
                .mapToLong(ResultDTO::getTotalVotes)
                .sum();

        StatisticsDTO stats = new StatisticsDTO();
        stats.setTotalVotes(totalVotes);
        stats.setTotalCandidates(results.size());
        stats.setResults(results);

        return stats;
    }
}