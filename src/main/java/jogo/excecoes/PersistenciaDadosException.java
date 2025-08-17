package jogo.excecoes;

/**
 * Exceção para erros relacionados à persistência de dados do jogo.
 * Usada para sinalizar falhas ao salvar, carregar ou acessar informações que precisam ser mantidas entre execuções,
 * como arquivos de configuração, progresso do jogador ou banco de dados.
 */

public class PersistenciaDadosException extends Exception {
    public PersistenciaDadosException(String mensagem) {
        super("Erro de persistência de dados: " + mensagem);
    }
    public PersistenciaDadosException(String mensagem, Throwable causa) {
        super("Erro de persistência de dados: " + mensagem, causa);
    }
}
