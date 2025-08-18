package jogo.excecoes;

/**
 * Exceção lançada quando há erro ao processar os dados de uma fase do jogo.
 */

public class DadosFaseException extends Exception {
    private final int numeroFase;

    /**
     * Construtor que cria uma exceção com uma mensagem detalhada e o número da fase com problema.
     *
     * @param mensagem Descrição do erro ocorrido nos dados da fase.
     * @param numeroFase Número da fase onde ocorreu o erro.
     */
    public DadosFaseException(String mensagem, int numeroFase) {
        super("Erro nos dados da fase: " + mensagem + " - Fase: " + numeroFase);
        this.numeroFase = numeroFase;
    }

    /**
     * @return Número da fase que ocorreu erro.
     */
    public int getNumeroFase() {
        return numeroFase;
    }
}
