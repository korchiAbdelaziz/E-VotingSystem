package evotingsystem.resultservice.feignclients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {
    private Long idVote;
    private LocalDateTime dateHeure;
    private Long electorId;
    private Long candidateId;
}