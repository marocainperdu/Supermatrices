package supermat;

/**
 * Exception personnalisée pour les erreurs liées aux supermatrices.
 */
public class SupermatException extends Exception {
    
    public SupermatException(String message) {
        super(message);
    }
    
    public SupermatException(String message, Throwable cause) {
        super(message, cause);
    }
}
