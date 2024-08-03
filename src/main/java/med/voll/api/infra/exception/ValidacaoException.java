package med.voll.api.infra.exception;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException() {
        super("Erro de validação.");
    }

    public ValidacaoException(String message) {
        super(message);
    }
}
