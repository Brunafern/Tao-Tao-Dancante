package jogo.personagens;

import jogo.modelo.enume.TipoPersonagem;
import jogo.excecoes.VidaInsuficienteException;
import jogo.excecoes.PersonagemInvalidoException;

public abstract class PersonagemJogo extends Personagem {

    private static final int VIDAS_INICIAIS = 3;
    private static final double MULTIPLICADOR_INICIAL = 1.0;

    protected final String nome;
    protected final TipoPersonagem tipo;
    protected int pontuacao;
    protected int vidas;
    protected boolean estaAtivo;
    protected double multiplicadorPontos;

    /**
     * @param nome   nome do personagem
     * @param tipo   tipo do personagem
     * @param largura largura do personagem
     * @param altura  altura do personagem
     * @throws PersonagemInvalidoException se algum parâmetro for inválido
     */
    public PersonagemJogo(String nome, TipoPersonagem tipo, double largura, double altura) throws PersonagemInvalidoException {
        super(largura, altura);
        validarParametros(nome, tipo, largura, altura);

        this.nome = nome;
        this.tipo = tipo;
        this.pontuacao = 0;
        this.vidas = VIDAS_INICIAIS;
        this.estaAtivo = true;
        this.multiplicadorPontos = MULTIPLICADOR_INICIAL;
    }

    /**
     * @param nome    nome do personagem
     * @param tipo    tipo do personagem
     * @param largura largura do personagem
     * @param altura  altura do personagem
     * @throws PersonagemInvalidoException se algum parâmetro for inválido
     */
    private void validarParametros(String nome, TipoPersonagem tipo, double largura, double altura) throws PersonagemInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new PersonagemInvalidoException(
                    "Nome não pode ser vazio.",
                    tipo != null ? tipo.toString() : "DESCONHECIDO",
                    "Nome inválido"
            );
        }
        if (tipo == null) {
            throw new PersonagemInvalidoException("Tipo não pode ser nulo.", nome, "Tipo nulo");
        }
        if (largura <= 0 || altura <= 0) {
            throw new PersonagemInvalidoException(
                    "Dimensões (largura e altura) devem ser positivas.",
                    tipo.toString(),
                    "Dimensões inválidas"
            );
        }
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
     * @throws VidaInsuficienteException se já não houver vidas
     */
    public boolean perderVida() throws VidaInsuficienteException {
        if (this.vidas <= 0) {
            this.estaAtivo = false;
            throw new VidaInsuficienteException("Personagem já morreu.", 0, "perder vida");
        }

        this.vidas--;
        if (this.vidas <= 0) {
            this.estaAtivo = false;
            return false;
        }
        return true;
    }

    /**
     * @param habilidade nome da habilidade a ser usada
     * @throws VidaInsuficienteException se o personagem não tiver vidas suficientes
     */
    public void usarHabilidadeComVida(String habilidade) throws VidaInsuficienteException {
        if (this.vidas <= 1) {
            throw new VidaInsuficienteException("Vida insuficiente para usar habilidade.", this.vidas, habilidade);
        }
        this.vidas--;
    }

    /** @return nome do personagem */
    public String getNome() { return nome; }

    /** @return tipo do personagem */
    public TipoPersonagem getTipo() { return tipo; }

    /** @return pontuação atual do personagem */
    public int getPontuacao() { return pontuacao; }

    /** @return número de vidas restantes */
    public int getVidas() { return vidas; }

    /** @return true se personagem ainda está ativo */
    public boolean isAtivo() { return estaAtivo; }

    /** @return multiplicador atual de pontos */
    public double getMultiplicadorPontos() { return multiplicadorPontos; }

    /**
     * @param multiplicador valor mínimo 1.0
     */
    protected void setMultiplicadorPontos(double multiplicador) {
        this.multiplicadorPontos = Math.max(MULTIPLICADOR_INICIAL, multiplicador);
    }
}
