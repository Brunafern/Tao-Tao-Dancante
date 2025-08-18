package jogo.excecoes;

/**
 * Exceção lançada quando ocorre um erro relacionado a um personagem inválido no jogo.
 */

public class PersonagemInvalidoException extends Exception {

    private String tipoPersonagem;
    private String motivoErro;

    /**
     * @param mensagem Mensagem descritiva do erro.
     */
    public PersonagemInvalidoException(String mensagem) {
        super("Erro de personagem: " + mensagem);
    }

    /**
     * @param mensagem Mensagem descritiva do erro.
     * @param tipoPersonagem Tipo do personagem relacionado ao erro.
     */
    public PersonagemInvalidoException(String mensagem, String tipoPersonagem) {
        super("Erro de personagem: " + mensagem);
        this.tipoPersonagem = tipoPersonagem;
    }

    /**
     * @param mensagem Mensagem descritiva do erro.
     * @param tipoPersonagem Tipo do personagem relacionado ao erro.
     * @param motivoErro Detalhes adicionais sobre o motivo do erro.
     */
    public PersonagemInvalidoException(String mensagem, String tipoPersonagem, String motivoErro) {
        super("Erro de personagem: " + mensagem);
        this.tipoPersonagem = tipoPersonagem;
        this.motivoErro = motivoErro;
    }

    /**
     * Construtor com mensagem e causa da exceção.
     * @param mensagem Mensagem descritiva do erro.
     * @param causa Exceção original que causou este erro.
     */
    public PersonagemInvalidoException(String mensagem, Throwable causa) {
        super("Erro de personagem: " + mensagem, causa);
    }

    /**
     * @return Tipo do personagem relacionado ao erro, ou null se não informado.
     */
    public String getTipoPersonagem() {
        return tipoPersonagem;
    }

    /**
     * @return Motivo detalhado do erro, ou null se não informado.
     */
    public String getMotivoErro() {
        return motivoErro;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder(super.getMessage());
        if (tipoPersonagem != null) {
            msg.append(" - Tipo: ").append(tipoPersonagem);
        }
        if (motivoErro != null) {
            msg.append(" - Motivo: ").append(motivoErro);
        }
        return msg.toString();
    }
}
