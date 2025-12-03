package evotingsystem.voteservice.services;

import evotingsystem.voteservice.dtos.VoteRequest;
import evotingsystem.voteservice.dtos.VoteResponse;
import evotingsystem.voteservice.entities.Vote;
import evotingsystem.voteservice.exceptions.AlreadyVotedException;
import evotingsystem.voteservice.exceptions.InvalidElectorException;
import evotingsystem.voteservice.feignclients.Elector;
import evotingsystem.voteservice.feignclients.ElectorClient;
import evotingsystem.voteservice.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ElectorClient electorClient;

    @Override
    public VoteResponse submitVote(VoteRequest request) {
        // Vérifier si l'électeur existe
        Elector elector;
        try {
            elector = electorClient.getElectorById(request.getElectorId());
        } catch (Exception e) {
            throw new InvalidElectorException("Elector not found");
        }

        // Vérifier si l'électeur a déjà voté
        if (voteRepository.existsByElectorId(request.getElectorId())) {
            throw new AlreadyVotedException("Elector has already voted");
        }

        // Créer et sauvegarder le vote
        Vote vote = new Vote();
        vote.setElectorId(request.getElectorId());
        vote.setCandidateId(request.getCandidateId());
        Vote savedVote = voteRepository.save(vote);

        // Créer la réponse
        VoteResponse response = new VoteResponse();
        response.setIdVote(savedVote.getIdVote());
        response.setDateHeure(savedVote.getDateHeure());
        response.setElectorId(savedVote.getElectorId());
        response.setElectorName(elector.getNom() + " " + elector.getPrenom());
        response.setCandidateId(savedVote.getCandidateId());
        response.setMessage("Vote submitted successfully");

        return response;
    }

    @Override
    public List<Vote> listVotes() {
        return voteRepository.findAll();
    }

    @Override
    public List<Vote> getVotesByCandidate(Long candidateId) {
        return voteRepository.findByCandidateId(candidateId);
    }
}