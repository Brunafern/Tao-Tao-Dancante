package jogo.excecoes;

/**
 * Responsável por tratar erros ao processar os dados de uma fase
 *
 */

public class DadosFaseException extends Exception {
    private final int numeroFase;
    public DadosFaseException(String mensagem, int numeroFase) {
        super("Erro nos dados da fase: " + mensagem + " - Fase: " + numeroFase);
        this.numeroFase = numeroFase;
    }
    public int getNumeroFase() {
        return numeroFase;
    }
}
