package evotingsystem.resultservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private Long candidateId;
    private String candidateName;
    private Long totalVotes;
    private Double percentage;
}