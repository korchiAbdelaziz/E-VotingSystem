package evotingsystem.resultservice.feignclients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectorDTO {
    private Long idElector;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String identifiantSecurise;
    private boolean aVote;
}