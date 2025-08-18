package jogo.excecoes;

/**
 * Exceção para erros ao acessar arquivos essenciais para funcionalidades do jogo.
 * Usada quando ocorre falha ao ler, gravar ou encontrar arquivos necessários para o funcionamento do sistema.
 */
public class ArquivoException extends Exception {

    /**
     * Construtor que recebe uma mensagem descritiva do erro.
     *
     * @param mensagem Mensagem explicando o erro ocorrido.
     */
    public ArquivoException(String mensagem) {
        super("Erro de arquivo: " + mensagem);
    }

    /**
     * Construtor que recebe uma mensagem e uma causa (outra exceção) que originou este erro.
     *
     * @param mensagem Mensagem explicando o erro ocorrido.
     * @param causa A exceção original que causou este erro.
     */
    public ArquivoException(String mensagem, Throwable causa) {
        super("Erro de arquivo: " + mensagem, causa);
    }
}
