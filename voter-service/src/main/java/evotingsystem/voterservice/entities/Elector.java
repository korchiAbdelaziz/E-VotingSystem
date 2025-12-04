package evotingsystem.voterservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entité représentant un électeur dans le système de vote électronique.
 * 
 * Cette classe modélise les informations d'un électeur, incluant ses données personnelles
 * et son statut de vote. Elle est mappée à la table "electors" dans la base de données.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@Entity
@Table(name = "electors", indexes = {
    @Index(name = "idx_identifiant_securise", columnList = "identifiantSecurise"),
    @Index(name = "idx_a_vote", columnList = "aVote")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Elector {

    /**
     * Identifiant unique de l'électeur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_elector")
    private Long idElector;

    /**
     * Nom de famille de l'électeur.
     * Ne peut pas être null.
     */
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * Prénom de l'électeur.
     * Ne peut pas être null.
     */
    @Column(name = "prenom", nullable = false)
    private String prenom;

    /**
     * Date de naissance de l'électeur.
     * Utilisée pour vérifier l'éligibilité au vote.
     */
    @Column(name = "date_naissance", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    /**
     * Identifiant sécurisé unique de l'électeur.
     * Utilisé pour l'authentification et la vérification de l'identité.
     * Doit être unique dans la base de données.
     */
    @Column(name = "identifiant_securise", unique = true, nullable = false)
    private String identifiantSecurise;

    /**
     * Indicateur indiquant si l'électeur a déjà voté.
     * Par défaut, cette valeur est false lors de la création.
     */
    @Column(name = "a_vote", nullable = false)
    private Boolean aVote = false;

    /**
     * Constructeur pour créer un nouvel électeur sans spécifier l'ID.
     * L'ID sera généré automatiquement par la base de données.
     * 
     * @param nom Le nom de famille de l'électeur
     * @param prenom Le prénom de l'électeur
     * @param dateNaissance La date de naissance de l'électeur
     * @param identifiantSecurise L'identifiant sécurisé unique de l'électeur
     */
    public Elector(String nom, String prenom, Date dateNaissance, String identifiantSecurise) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.identifiantSecurise = identifiantSecurise;
        this.aVote = false;
    }
}

