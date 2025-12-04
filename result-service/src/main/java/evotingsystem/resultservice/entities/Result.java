package evotingsystem.resultservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "results", indexes = {
    @Index(name = "idx_candidate_id", columnList = "candidateId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "candidate_id", nullable = false, unique = true)
    private Long candidateId;

    @Column(name = "total_votes", nullable = false)
    private Long totalVotes;

    @Transient
    private String candidateName; // Récupéré via Feign Client
}