package evotingsystem.voterservice.repositories;

import evotingsystem.voterservice.entities.Elector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repository pour l'entité Elector.
 * 
 * Cette interface étend JpaRepository pour fournir des opérations CRUD de base
 * et des méthodes personnalisées pour rechercher des électeurs.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@Repository
public interface ElectorRepository extends JpaRepository<Elector, Long> {

    /**
     * Recherche un électeur par son identifiant sécurisé.
     * 
     * @param identifiantSecurise L'identifiant sécurisé de l'électeur
     * @return Un Optional contenant l'électeur trouvé, ou vide si aucun n'est trouvé
     */
    Optional<Elector> findByIdentifiantSecurise(String identifiantSecurise);

    /**
     * Vérifie si un électeur existe avec l'identifiant sécurisé donné.
     * 
     * @param identifiantSecurise L'identifiant sécurisé à vérifier
     * @return true si un électeur existe avec cet identifiant, false sinon
     */
    boolean existsByIdentifiantSecurise(String identifiantSecurise);
}

