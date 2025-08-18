package jogo.excecoes;

/**
 * Exceção lançada quando uma ação não pode ser realizada devido a número insuficiente de vidas.
 */

public class PlacarDeVidaException extends RuntimeException {

    private static final int VIDAS_NAO_INFORMADAS = -1;

    private final int vidasRestantes;
    private final String acaoTentada;

    /**
     * @param mensagem Mensagem descritiva do erro.
     */
    public PlacarDeVidaException(String mensagem) {
        super("Vidas insuficientes: " + mensagem);
        this.vidasRestantes = VIDAS_NAO_INFORMADAS;
        this.acaoTentada = null;
    }

    /**
     * Construtor com mensagem e vidas restantes.
     * @param mensagem Mensagem descritiva do erro.
     * @param vidasRestantes Número de vidas restantes no placar.
     */
    public PlacarDeVidaException(String mensagem, int vidasRestantes) {
        super("Vidas insuficientes: " + mensagem);
        this.vidasRestantes = vidasRestantes;
        this.acaoTentada = null;
    }

    /**
     * Construtor completo com mensagem, vidas restantes e ação tentada.
     * @param mensagem Mensagem descritiva do erro.
     * @param vidasRestantes Número de vidas restantes no placar.
     * @param acaoTentada Ação que foi tentada e causou a exceção.
     */
    public PlacarDeVidaException(String mensagem, int vidasRestantes, String acaoTentada) {
        super("Vidas insuficientes: " + mensagem);
        this.vidasRestantes = vidasRestantes;
        this.acaoTentada = acaoTentada;
    }

    /**
     * @return vidas restantes, ou -1 se não informado.
     */
    public int getVidasRestantes() {
        return vidasRestantes;
    }

    /**
     * @return ação tentada, ou null se não informado.
     */
    public String getAcaoTentada() {
        return acaoTentada;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder(super.getMessage());
        if (vidasRestantes != VIDAS_NAO_INFORMADAS) {
            msg.append(" - Vidas restantes: ").append(vidasRestantes);
        }
        if (acaoTentada != null && !acaoTentada.isBlank()) {
            msg.append(" - Ação tentada: ").append(acaoTentada);
        }
        return msg.toString();
    }
}
