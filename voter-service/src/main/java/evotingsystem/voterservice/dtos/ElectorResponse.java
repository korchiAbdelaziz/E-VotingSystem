package evotingsystem.voterservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO pour les réponses lors de la création ou de la mise à jour d'un électeur.
 * 
 * Cette classe est utilisée pour retourner les informations d'un électeur
 * après une opération de création ou de mise à jour, incluant un message de confirmation.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectorResponse {

    /**
     * Identifiant unique de l'électeur créé ou mis à jour.
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

    /**
     * Message de confirmation de l'opération.
     */
    private String message;
}

