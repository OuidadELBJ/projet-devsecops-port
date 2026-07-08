package ma.portnador.escale.exception;

public class EscaleNotFoundException extends RuntimeException {

    public EscaleNotFoundException(Long id) {
        super("Escale introuvable avec l'id : " + id);
    }
}
