package jogo.excecoes;

/**
 * Exceção genérica para erros de lógica ou fluxo do jogo que não se encaixam
 * em outras exceções específicas (ex: arquivo, recurso, persistência).
 * Pode armazenar a fase e o tipo do erro para facilitar o diagnóstico.
 */


public class FluxoException extends Exception {

    private final String faseAtual;
    private final String tipoErro;



    public FluxoException(String mensagem) {
        super("Erro de fluxo: " + mensagem);
        this.faseAtual = null;
        this.tipoErro = null;
    }

    /**
     * Construtor para ErroJogoException com informações contextuais.
     */

    public FluxoException(String mensagem, String faseAtual, String tipoErro) {
        super("Erro de fluxo: " + mensagem);
        this.faseAtual = faseAtual;
        this.tipoErro = tipoErro;
    }

    /**
     * Construtor para ErroJogoException que encapsula uma exceção subjacente (causa).
     */

    public FluxoException(String mensagem, Throwable causa) {
        super("Erro de fluxo: " + mensagem, causa);
        this.faseAtual = null;
        this.tipoErro = null;
    }


    public String getFaseAtual() {
        return faseAtual;
    }


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