package evotingsystem.voterservice.services;

import evotingsystem.voterservice.dtos.ElectorDTO;
import evotingsystem.voterservice.dtos.ElectorRequest;
import evotingsystem.voterservice.dtos.ElectorResponse;
import evotingsystem.voterservice.entities.Elector;
import evotingsystem.voterservice.exceptions.ElectorAlreadyExistsException;
import evotingsystem.voterservice.exceptions.ElectorNotFoundException;
import evotingsystem.voterservice.repositories.ElectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des électeurs.
 * 
 * Cette classe contient la logique métier pour toutes les opérations
 * liées à la gestion des électeurs dans le système de vote électronique.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ElectorServiceImpl implements ElectorService {

    private final ElectorRepository electorRepository;

    /**
     * Crée un nouvel électeur dans le système.
     * 
     * Vérifie d'abord si un électeur avec le même identifiant sécurisé existe déjà.
     * Si c'est le cas, une exception est levée. Sinon, l'électeur est créé et sauvegardé.
     * 
     * @param request Les données de l'électeur à créer
     * @return Un objet ElectorResponse contenant les informations de l'électeur créé
     * @throws ElectorAlreadyExistsException Si un électeur avec le même identifiant sécurisé existe déjà
     */
    @Override
    public ElectorResponse createElector(ElectorRequest request) {
        log.info("Tentative de création d'un nouvel électeur avec l'identifiant sécurisé: {}", 
                request.getIdentifiantSecurise());

        // Vérifier si un électeur avec le même identifiant sécurisé existe déjà
        if (electorRepository.existsByIdentifiantSecurise(request.getIdentifiantSecurise())) {
            log.warn("Tentative de création d'un électeur avec un identifiant sécurisé déjà existant: {}", 
                    request.getIdentifiantSecurise());
            throw new ElectorAlreadyExistsException(
                    "Un électeur avec l'identifiant sécurisé '" + 
                    request.getIdentifiantSecurise() + "' existe déjà."
            );
        }

        // Créer un nouvel électeur
        Elector elector = new Elector(
                request.getNom(),
                request.getPrenom(),
                request.getDateNaissance(),
                request.getIdentifiantSecurise()
        );

        // Sauvegarder l'électeur dans la base de données
        Elector savedElector = electorRepository.save(elector);
        log.info("Électeur créé avec succès. ID: {}, Nom: {} {}", 
                savedElector.getIdElector(), savedElector.getPrenom(), savedElector.getNom());

        // Construire et retourner la réponse
        ElectorResponse response = new ElectorResponse();
        response.setIdElector(savedElector.getIdElector());
        response.setNom(savedElector.getNom());
        response.setPrenom(savedElector.getPrenom());
        response.setDateNaissance(savedElector.getDateNaissance());
        response.setIdentifiantSecurise(savedElector.getIdentifiantSecurise());
        response.setAVote(savedElector.getAVote());
        response.setMessage("Électeur créé avec succès");

        return response;
    }

    /**
     * Récupère un électeur par son identifiant unique.
     * 
     * @param id L'identifiant unique de l'électeur
     * @return Un objet ElectorDTO contenant les informations de l'électeur
     * @throws ElectorNotFoundException Si aucun électeur n'est trouvé avec cet ID
     */
    @Override
    @Transactional(readOnly = true)
    public ElectorDTO getElectorById(Long id) {
        log.info("Récupération de l'électeur avec l'ID: {}", id);

        Elector elector = electorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Électeur non trouvé avec l'ID: {}", id);
                    return new ElectorNotFoundException("Électeur non trouvé avec l'ID: " + id);
                });

        log.info("Électeur trouvé: {} {}", elector.getPrenom(), elector.getNom());
        return convertToDTO(elector);
    }

    /**
     * Récupère un électeur par son identifiant sécurisé.
     * 
     * @param identifiantSecurise L'identifiant sécurisé de l'électeur
     * @return Un objet ElectorDTO contenant les informations de l'électeur
     * @throws ElectorNotFoundException Si aucun électeur n'est trouvé avec cet identifiant
     */
    @Override
    @Transactional(readOnly = true)
    public ElectorDTO getElectorByIdentifiantSecurise(String identifiantSecurise) {
        log.info("Récupération de l'électeur avec l'identifiant sécurisé: {}", identifiantSecurise);

        Elector elector = electorRepository.findByIdentifiantSecurise(identifiantSecurise)
                .orElseThrow(() -> {
                    log.warn("Électeur non trouvé avec l'identifiant sécurisé: {}", identifiantSecurise);
                    return new ElectorNotFoundException(
                            "Électeur non trouvé avec l'identifiant sécurisé: " + identifiantSecurise
                    );
                });

        log.info("Électeur trouvé: {} {}", elector.getPrenom(), elector.getNom());
        return convertToDTO(elector);
    }

    /**
     * Récupère la liste de tous les électeurs enregistrés.
     * 
     * @return Une liste d'objets ElectorDTO contenant les informations de tous les électeurs
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElectorDTO> listElectors() {
        log.info("Récupération de la liste de tous les électeurs");

        List<ElectorDTO> electors = electorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        log.info("Nombre d'électeurs trouvés: {}", electors.size());
        return electors;
    }

    /**
     * Met à jour le statut de vote d'un électeur.
     * Cette méthode est généralement appelée lorsqu'un électeur a voté.
     * 
     * @param id L'identifiant unique de l'électeur
     * @param aVote Le nouveau statut de vote (true si l'électeur a voté, false sinon)
     * @throws ElectorNotFoundException Si aucun électeur n'est trouvé avec cet ID
     */
    @Override
    public void updateVoteStatus(Long id, Boolean aVote) {
        log.info("Mise à jour du statut de vote pour l'électeur ID: {} à {}", id, aVote);

        Elector elector = electorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Électeur non trouvé avec l'ID: {}", id);
                    return new ElectorNotFoundException("Électeur non trouvé avec l'ID: " + id);
                });

        elector.setAVote(aVote);
        electorRepository.save(elector);

        log.info("Statut de vote mis à jour avec succès pour l'électeur ID: {}", id);
    }

    /**
     * Convertit une entité Elector en DTO ElectorDTO.
     * 
     * @param elector L'entité Elector à convertir
     * @return Un objet ElectorDTO contenant les données de l'électeur
     */
    private ElectorDTO convertToDTO(Elector elector) {
        ElectorDTO dto = new ElectorDTO();
        dto.setIdElector(elector.getIdElector());
        dto.setNom(elector.getNom());
        dto.setPrenom(elector.getPrenom());
        dto.setDateNaissance(elector.getDateNaissance());
        dto.setIdentifiantSecurise(elector.getIdentifiantSecurise());
        dto.setAVote(elector.getAVote());
        return dto;
    }
}

