package evotingsystem.voterservice.controllers;

import evotingsystem.voterservice.dtos.ElectorDTO;
import evotingsystem.voterservice.dtos.ElectorRequest;
import evotingsystem.voterservice.dtos.ElectorResponse;
import evotingsystem.voterservice.exceptions.ElectorAlreadyExistsException;
import evotingsystem.voterservice.exceptions.ElectorNotFoundException;
import evotingsystem.voterservice.services.ElectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des électeurs.
 * 
 * Ce contrôleur expose les endpoints REST pour toutes les opérations
 * liées à la gestion des électeurs dans le système de vote électronique.
 * 
 * Endpoints disponibles:
 * - POST /api/electors : Créer un nouvel électeur
 * - GET /api/electors/{id} : Récupérer un électeur par son ID
 * - GET /api/electors : Récupérer la liste de tous les électeurs
 * - GET /api/electors/identifiant/{identifiantSecurise} : Récupérer un électeur par son identifiant sécurisé
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/electors")
@RequiredArgsConstructor
@Slf4j
public class ElectorController {

    private final ElectorService electorService;

    /**
     * Crée un nouvel électeur dans le système.
     * 
     * @param request Les données de l'électeur à créer (validées automatiquement)
     * @return ResponseEntity contenant les informations de l'électeur créé avec le statut HTTP 201 (Created)
     *         ou une erreur avec le statut HTTP 409 (Conflict) si l'électeur existe déjà
     *         ou le statut HTTP 400 (Bad Request) si les données sont invalides
     */
    @PostMapping
    public ResponseEntity<?> createElector(@Valid @RequestBody ElectorRequest request) {
        log.info("Requête POST reçue pour créer un nouvel électeur: {} {}", 
                request.getPrenom(), request.getNom());

        try {
            ElectorResponse response = electorService.createElector(request);
            log.info("Électeur créé avec succès. ID: {}", response.getIdElector());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ElectorAlreadyExistsException e) {
            log.warn("Tentative de création d'un électeur déjà existant: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erreur: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la création de l'électeur", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur lors de la création de l'électeur");
        }
    }

    /**
     * Récupère un électeur par son identifiant unique.
     * 
     * @param id L'identifiant unique de l'électeur
     * @return ResponseEntity contenant les informations de l'électeur avec le statut HTTP 200 (OK)
     *         ou le statut HTTP 404 (Not Found) si l'électeur n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getElector(@PathVariable Long id) {
        log.info("Requête GET reçue pour récupérer l'électeur avec l'ID: {}", id);

        try {
            ElectorDTO elector = electorService.getElectorById(id);
            log.info("Électeur trouvé: {} {}", elector.getPrenom(), elector.getNom());
            return ResponseEntity.ok(elector);
        } catch (ElectorNotFoundException e) {
            log.warn("Électeur non trouvé avec l'ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'électeur avec l'ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur lors de la récupération de l'électeur");
        }
    }

    /**
     * Récupère la liste de tous les électeurs enregistrés.
     * 
     * @return ResponseEntity contenant la liste de tous les électeurs avec le statut HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<ElectorDTO>> listElectors() {
        log.info("Requête GET reçue pour récupérer la liste de tous les électeurs");

        try {
            List<ElectorDTO> electors = electorService.listElectors();
            log.info("Nombre d'électeurs retournés: {}", electors.size());
            return ResponseEntity.ok(electors);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la liste des électeurs", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère un électeur par son identifiant sécurisé.
     * 
     * @param identifiantSecurise L'identifiant sécurisé de l'électeur
     * @return ResponseEntity contenant les informations de l'électeur avec le statut HTTP 200 (OK)
     *         ou le statut HTTP 404 (Not Found) si l'électeur n'existe pas
     */
    @GetMapping("/identifiant/{identifiantSecurise}")
    public ResponseEntity<?> getElectorByIdentifiantSecurise(
            @PathVariable String identifiantSecurise) {
        log.info("Requête GET reçue pour récupérer l'électeur avec l'identifiant sécurisé: {}", 
                identifiantSecurise);

        try {
            ElectorDTO elector = electorService.getElectorByIdentifiantSecurise(identifiantSecurise);
            log.info("Électeur trouvé: {} {}", elector.getPrenom(), elector.getNom());
            return ResponseEntity.ok(elector);
        } catch (ElectorNotFoundException e) {
            log.warn("Électeur non trouvé avec l'identifiant sécurisé: {}", identifiantSecurise);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'électeur avec l'identifiant sécurisé: {}", 
                    identifiantSecurise, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur lors de la récupération de l'électeur");
        }
    }
}

