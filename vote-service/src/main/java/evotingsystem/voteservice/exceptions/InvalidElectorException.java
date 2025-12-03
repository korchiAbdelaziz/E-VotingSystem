package evotingsystem.voteservice.exceptions;

public class InvalidElectorException extends RuntimeException {
    public InvalidElectorException(String message) {
        super(message);
    }
}