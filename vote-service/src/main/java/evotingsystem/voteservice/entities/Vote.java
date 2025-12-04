package evotingsystem.voteservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes", indexes = {
    @Index(name = "idx_elector_id", columnList = "electorId"),
    @Index(name = "idx_candidate_id", columnList = "candidateId"),
    @Index(name = "idx_date_heure", columnList = "dateHeure")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vote")
    private Long idVote;

    @Column(name = "date_heure", nullable = false)
    private LocalDateTime dateHeure;

    @Column(name = "elector_id", nullable = false)
    private Long electorId;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Transient
    private String electorName; // Récupéré via Feign

    @PrePersist
    protected void onCreate() {
        dateHeure = LocalDateTime.now();
    }
}