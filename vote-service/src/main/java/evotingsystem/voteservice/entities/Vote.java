package evotingsystem.voteservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVote;

    private LocalDateTime dateHeure;
    private Long electorId;
    private Long candidateId;

    @Transient
    private String electorName; // Récupéré via Feign

    @PrePersist
    protected void onCreate() {
        dateHeure = LocalDateTime.now();
    }
}