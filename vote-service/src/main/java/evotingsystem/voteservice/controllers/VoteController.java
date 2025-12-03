package evotingsystem.voteservice.controllers;

import evotingsystem.voteservice.dtos.VoteRequest;
import evotingsystem.voteservice.dtos.VoteResponse;
import evotingsystem.voteservice.entities.Vote;
import evotingsystem.voteservice.exceptions.AlreadyVotedException;
import evotingsystem.voteservice.exceptions.InvalidElectorException;
import evotingsystem.voteservice.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<?> submitVote(@RequestBody VoteRequest request) {
        try {
            VoteResponse response = voteService.submitVote(request);
            return ResponseEntity.ok(response);
        } catch (AlreadyVotedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (InvalidElectorException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        return ResponseEntity.ok(voteService.listVotes());
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Vote>> getVotesByCandidate(
            @PathVariable Long candidateId) {
        return ResponseEntity.ok(
                voteService.getVotesByCandidate(candidateId)
        );
    }
}