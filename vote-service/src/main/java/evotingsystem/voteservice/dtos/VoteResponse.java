package evotingsystem.voteservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private Long idVote;
    private LocalDateTime dateHeure;
    private Long electorId;
    private String electorName;
    private Long candidateId;
    private String message;
}