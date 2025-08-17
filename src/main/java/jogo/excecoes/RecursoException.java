package jogo.excecoes;

/**
 * Exceção para erros ao acessar recursos externos do jogo (imagens, sons, FXML, etc).
 * Usada para sinalizar problemas ao carregar arquivos essenciais para a interface ou áudio.
 */

public class RecursoException extends Exception {
    public RecursoException(String mensagem) {
        super("Erro de recurso: " + mensagem);
    }
    public RecursoException(String mensagem, Throwable causa) {
        super("Erro de recurso: " + mensagem, causa);
    }
}
