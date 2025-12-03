package evotingsystem.voteservice.services;

import evotingsystem.voteservice.dtos.VoteRequest;
import evotingsystem.voteservice.dtos.VoteResponse;
import evotingsystem.voteservice.entities.Vote;

import java.util.List;

public interface VoteService {
    VoteResponse submitVote(VoteRequest request);
    List<Vote> listVotes();
    List<Vote> getVotesByCandidate(Long candidateId);
}