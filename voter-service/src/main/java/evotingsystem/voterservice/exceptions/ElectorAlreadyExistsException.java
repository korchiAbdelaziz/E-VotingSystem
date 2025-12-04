package evotingsystem.voterservice.exceptions;

/**
 * Exception levée lorsqu'un électeur avec le même identifiant sécurisé existe déjà.
 * 
 * Cette exception est utilisée lors de la création d'un nouvel électeur
 * si un électeur avec le même identifiant sécurisé existe déjà dans la base de données.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
public class ElectorAlreadyExistsException extends RuntimeException {

    /**
     * Constructeur avec un message d'erreur.
     * 
     * @param message Le message d'erreur décrivant la raison de l'exception
     */
    public ElectorAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message et une cause.
     * 
     * @param message Le message d'erreur décrivant la raison de l'exception
     * @param cause La cause de l'exception
     */
    public ElectorAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

