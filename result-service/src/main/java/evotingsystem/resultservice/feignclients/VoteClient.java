package evotingsystem.resultservice.feignclients;

import evotingsystem.resultservice.feignclients.models.VoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vote-service")
public interface VoteClient {

    @GetMapping("/api/votes")
    List<VoteDTO> getAllVotes();

    @GetMapping("/api/votes/candidate/{candidateId}")
    List<VoteDTO> getVotesByCandidate(@PathVariable("candidateId") Long candidateId);
}