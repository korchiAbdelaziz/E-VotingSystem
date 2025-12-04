package evotingsystem.voterservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO pour les requêtes de création d'un électeur.
 * 
 * Cette classe est utilisée pour recevoir les données lors de la création
 * d'un nouvel électeur. Elle contient des validations pour s'assurer
 * que les données fournies sont correctes.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectorRequest {

    /**
     * Nom de famille de l'électeur.
     * Ne peut pas être vide ou null.
     */
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    /**
     * Prénom de l'électeur.
     * Ne peut pas être vide ou null.
     */
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    /**
     * Date de naissance de l'électeur.
     * Doit être dans le passé et ne peut pas être null.
     */
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private Date dateNaissance;

    /**
     * Identifiant sécurisé unique de l'électeur.
     * Ne peut pas être vide ou null.
     */
    @NotBlank(message = "L'identifiant sécurisé est obligatoire")
    private String identifiantSecurise;
}

