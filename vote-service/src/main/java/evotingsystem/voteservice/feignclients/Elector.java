package evotingsystem.voteservice.feignclients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Elector {
    private Long idElector;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String identifiantSecurise;
    private boolean aVote;
}