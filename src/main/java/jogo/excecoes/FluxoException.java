package jogo.excecoes;

/**
 * Exceção genérica para erros de lógica ou fluxo do jogo que não se encaixam
 * em outras exceções específicas (ex: arquivo, recurso, persistência).
 * Pode armazenar a fase atual e o tipo do erro para facilitar o diagnóstico.
 */

public class FluxoException extends Exception {

    private final String faseAtual;
    private final String tipoErro;

    /**
     * Construtor padrão com mensagem de erro.
     *
     * @param mensagem Descrição do erro ocorrido.
     */
    public FluxoException(String mensagem) {
        super("Erro de fluxo: " + mensagem);
        this.faseAtual = null;
        this.tipoErro = null;
    }

    /**
     * Construtor com mensagem, fase atual e tipo do erro.
     *
     * @param mensagem Descrição do erro ocorrido.
     * @param faseAtual Identificação da fase do jogo onde o erro ocorreu.
     * @param tipoErro Tipo específico do erro de fluxo.
     */
    public FluxoException(String mensagem, String faseAtual, String tipoErro) {
        super("Erro de fluxo: " + mensagem);
        this.faseAtual = faseAtual;
        this.tipoErro = tipoErro;
    }

    /**
     * Construtor que encapsula uma exceção subjacente (causa).
     *
     * @param mensagem Descrição do erro ocorrido.
     * @param causa Exceção original que causou esse erro.
     */
    public FluxoException(String mensagem, Throwable causa) {
        super("Erro de fluxo: " + mensagem, causa);
        this.faseAtual = null;
        this.tipoErro = null;
    }

    /**
     * @return Fase do jogo onde ocorreu o erro, ou null se não informado.
     */
    public String getFaseAtual() {
        return faseAtual;
    }

    /**
     * @return Tipo do erro de fluxo, ou null se não informado.
     */
    public String getTipoErro() {
        return tipoErro;
    }

    @Override
    public String getMessage() {
        StringBuilder mensagemCompleta = new StringBuilder(super.getMessage());
        if (faseAtual != null) {
            mensagemCompleta.append(" - Fase: ").append(faseAtual);
        }
        if (tipoErro != null) {
            mensagemCompleta.append(" - Tipo: ").append(tipoErro);
        }
        return mensagemCompleta.toString();
    }
}
