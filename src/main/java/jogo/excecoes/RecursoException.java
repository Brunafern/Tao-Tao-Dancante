package jogo.excecoes;

import java.io.Serial;

/**
 * Exceção para erros ao acessar recursos externos do jogo, como imagens, sons, arquivos FXML, etc.
 */

public class RecursoException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param mensagem Descrição do problema encontrado ao carregar o recurso.
     */
    public RecursoException(String mensagem) {
        super("Erro de recurso: " + mensagem);
    }

    /**
     * Cria uma nova exceção informando a mensagem e a causa original do erro.
     * @param mensagem Descrição do problema encontrado ao carregar o recurso.
     * @param causa Exceção original que causou este erro.
     */
    public RecursoException(String mensagem, Throwable causa) {
        super("Erro de recurso: " + mensagem, causa);
    }
}
