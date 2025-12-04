package evotingsystem.voterservice.exceptions;

/**
 * Exception levée lorsqu'un électeur n'est pas trouvé dans la base de données.
 * 
 * Cette exception est utilisée lorsque l'on tente d'accéder à un électeur
 * qui n'existe pas, par exemple lors de la récupération par ID.
 * 
 * @author Équipe E-Voting System
 * @version 1.0.0
 */
public class ElectorNotFoundException extends RuntimeException {

    /**
     * Constructeur avec un message d'erreur.
     * 
     * @param message Le message d'erreur décrivant la raison de l'exception
     */
    public ElectorNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message et une cause.
     * 
     * @param message Le message d'erreur décrivant la raison de l'exception
     * @param cause La cause de l'exception
     */
    public ElectorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

