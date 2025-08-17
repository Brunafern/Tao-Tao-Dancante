package jogo.excecoes;
/**
 * Exceção para erros ao acessar arquivos essenciais para funcionalidades do jogo.
 * Usada quando ocorre falha ao ler, gravar ou encontrar arquivos necessários para o funcionamento do sistema.
 */
public class ArquivoException extends Exception {
    public ArquivoException(String mensagem) {
        super("Erro de arquivo: " + mensagem);
    }
    public ArquivoException(String mensagem, Throwable causa) {
        super("Erro de arquivo: " + mensagem, causa);
    }
}
