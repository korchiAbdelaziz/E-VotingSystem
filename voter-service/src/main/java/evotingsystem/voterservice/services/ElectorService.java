package evotingsystem.voterservice.services;

import evotingsystem.voterservice.dtos.ElectorDTO;
import evotingsystem.voterservice.dtos.ElectorRequest;
import evotingsystem.voterservice.dtos.ElectorResponse;

import java.util.List;

/**
 * Interface du service de gestion des électeurs.
 * 
 * Cette interface définit les opérations métier pour la gestion des électeurs,
 * incluant la création, la récupération et la liste des électeurs.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
public interface ElectorService {

    /**
     * Crée un nouvel électeur dans le système.
     * 
     * @param request Les données de l'électeur à créer
     * @return Un objet ElectorResponse contenant les informations de l'électeur créé
     * @throws ElectorAlreadyExistsException Si un électeur avec le même identifiant sécurisé existe déjà
     */
    ElectorResponse createElector(ElectorRequest request);

    /**
     * Récupère un électeur par son identifiant unique.
     * 
     * @param id L'identifiant unique de l'électeur
     * @return Un objet ElectorDTO contenant les informations de l'électeur
     * @throws ElectorNotFoundException Si aucun électeur n'est trouvé avec cet ID
     */
    ElectorDTO getElectorById(Long id);

    /**
     * Récupère un électeur par son identifiant sécurisé.
     * 
     * @param identifiantSecurise L'identifiant sécurisé de l'électeur
     * @return Un objet ElectorDTO contenant les informations de l'électeur
     * @throws ElectorNotFoundException Si aucun électeur n'est trouvé avec cet identifiant
     */
    ElectorDTO getElectorByIdentifiantSecurise(String identifiantSecurise);

    /**
     * Récupère la liste de tous les électeurs enregistrés.
     * 
     * @return Une liste d'objets ElectorDTO contenant les informations de tous les électeurs
     */
    List<ElectorDTO> listElectors();

    /**
     * Met à jour le statut de vote d'un électeur.
     * Cette méthode est généralement appelée lorsqu'un électeur a voté.
     * 
     * @param id L'identifiant unique de l'électeur
     * @param aVote Le nouveau statut de vote (true si l'électeur a voté, false sinon)
     * @throws ElectorNotFoundException Si aucun électeur n'est trouvé avec cet ID
     */
    void updateVoteStatus(Long id, Boolean aVote);
}

