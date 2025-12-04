package evotingsystem.voterservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO (Data Transfer Object) pour représenter un électeur dans les réponses API.
 * 
 * Cette classe est utilisée pour transférer les données d'un électeur sans exposer
 * directement l'entité JPA. Elle contient toutes les informations nécessaires
 * pour représenter un électeur dans les réponses REST.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectorDTO {

    /**
     * Identifiant unique de l'électeur.
     */
    private Long idElector;

    /**
     * Nom de famille de l'électeur.
     */
    private String nom;

    /**
     * Prénom de l'électeur.
     */
    private String prenom;

    /**
     * Date de naissance de l'électeur.
     */
    private Date dateNaissance;

    /**
     * Identifiant sécurisé de l'électeur.
     */
    private String identifiantSecurise;

    /**
     * Indicateur indiquant si l'électeur a déjà voté.
     */
    private Boolean aVote;
}

