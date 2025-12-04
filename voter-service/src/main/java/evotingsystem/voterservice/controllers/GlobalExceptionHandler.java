package evotingsystem.voterservice.controllers;

import evotingsystem.voterservice.exceptions.ElectorAlreadyExistsException;
import evotingsystem.voterservice.exceptions.ElectorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour le service des électeurs.
 * 
 * Cette classe centralise la gestion des exceptions et fournit des réponses
 * HTTP cohérentes pour toutes les erreurs qui peuvent survenir dans l'application.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de type ElectorNotFoundException.
     * 
     * @param ex L'exception levée
     * @return Une réponse HTTP 404 (Not Found) avec le message d'erreur
     */
    @ExceptionHandler(ElectorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleElectorNotFoundException(
            ElectorNotFoundException ex) {
        log.warn("Électeur non trouvé: {}", ex.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Électeur non trouvé");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Gère les exceptions de type ElectorAlreadyExistsException.
     * 
     * @param ex L'exception levée
     * @return Une réponse HTTP 409 (Conflict) avec le message d'erreur
     */
    @ExceptionHandler(ElectorAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleElectorAlreadyExistsException(
            ElectorAlreadyExistsException ex) {
        log.warn("Électeur déjà existant: {}", ex.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Électeur déjà existant");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", String.valueOf(HttpStatus.CONFLICT.value()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Gère les exceptions de validation des données (Bean Validation).
     * 
     * @param ex L'exception de validation levée
     * @return Une réponse HTTP 400 (Bad Request) avec les détails des erreurs de validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.warn("Erreurs de validation détectées");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Erreurs de validation");
        errorResponse.put("message", "Les données fournies ne sont pas valides");
        errorResponse.put("errors", errors);
        errorResponse.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Gère toutes les autres exceptions non gérées.
     * 
     * @param ex L'exception levée
     * @return Une réponse HTTP 500 (Internal Server Error) avec un message générique
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Erreur inattendue", ex);

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Erreur interne du serveur");
        errorResponse.put("message", "Une erreur inattendue s'est produite");
        errorResponse.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

