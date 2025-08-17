package jogo.personagens;

import jogo.excecoes.PlacarDeVidaException;
import jogo.excecoes.PersonagemInvalidoException;

public abstract class PersonagemJogo extends Personagem {

    private static final int VIDAS_INICIAIS = 3;
    private static final double MULTIPLICADOR_INICIAL = 1.0;

    protected final String nome;
    protected int pontuacao;
    protected int vidas;
    protected boolean vivo;
    protected double multiplicadorPontos;

    /**
     * @param nome   nome do personagem
     * @param tipo   tipo do personagem
     * @param largura largura do personagem
     * @param altura  altura do personagem
     * @throws PersonagemInvalidoException se algum parâmetro for inválido
     */
    public PersonagemJogo(String nome, double largura, double altura) throws PersonagemInvalidoException {
    super(largura, altura);
    this.nome = nome;
    this.pontuacao = 0;
    this.vidas = VIDAS_INICIAIS;
    this.vivo = true;
    this.multiplicadorPontos = MULTIPLICADOR_INICIAL;
    }
    /**
     * @param pontos quantidade de pontos a adicionar
     */
    public void adicionarPontos(int pontos) {
        if (pontos > 0) {
            this.pontuacao += (int) (pontos * multiplicadorPontos);
        }
    }

    /**
     * @return true se ainda possui vidas; false se morreu
     * @throws PlacarDeVidaException se já não houver vidas
     */
    public boolean perderVida() throws PlacarDeVidaException {
        if (this.vidas <= 0) {
            this.vivo = false;
            throw new PlacarDeVidaException("Personagem já morreu.", 0, "perder vida");
        }

        this.vidas = this.vidas - 1;
        if (this.vidas <= 0) {
            this.vivo = false;
            return false;
        }
        return true;
    }


    /** @return nome do personagem */
    public String getNome() { return nome; }


    /** @return pontuação atual do personagem */
    public int getPontuacao() { return pontuacao; }

    /** @return número de vidas restantes */
    public int getVidas() { return vidas; }

    /** @return true se personagem ainda está vivo */
    public boolean isVivo() { return vivo; }

    /** @return multiplicador atual de pontos */
    public double getMultiplicadorPontos() { return multiplicadorPontos; }

    /**
     * @param multiplicador valor mínimo 1.0
     */
    protected void setMultiplicadorPontos(double multiplicador) {
        this.multiplicadorPontos = Math.max(MULTIPLICADOR_INICIAL, multiplicador);
    }
}
