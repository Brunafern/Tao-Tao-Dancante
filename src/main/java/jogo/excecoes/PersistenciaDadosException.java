package jogo.excecoes;

/**
 * Exceção para erros relacionados à persistência de dados do jogo.
 */

public class PersistenciaDadosException extends Exception {

    /**
     * @param mensagem Descrição do problema ocorrido.
     */
    public PersistenciaDadosException(String mensagem) {
        super("Erro de persistência de dados: " + mensagem);
    }

    /**
     * Construtor com mensagem e causa raiz da exceção.
     * @param mensagem Descrição do problema ocorrido.
     * @param causa Exceção original que gerou esse erro.
     */
    public PersistenciaDadosException(String mensagem, Throwable causa) {
        super("Erro de persistência de dados: " + mensagem, causa);
    }
}
