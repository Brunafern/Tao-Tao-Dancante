package jogo.personagens;

import javafx.scene.image.ImageView;
import jogo.excecoes.PersonagemInvalidoException;


public abstract class Personagem extends ImageView {

    private static final int VIDAS_INICIAIS = 3;
    private static final double MULTIPLICADOR_INICIAL = 1.0;

    protected final String nome;
    protected int pontuacao;
    protected int vidas;
    protected boolean vivo;
    protected double multiplicadorPontos;
    /**
     * @param width  largura do personagem (pixels)
     * @param height altura do personagem (pixels)
     */
    public Personagem(String nome, double width, double height) throws PersonagemInvalidoException {
        this.nome = nome;
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.pontuacao = 0;
        this.vidas = VIDAS_INICIAIS;
        this.vivo = true;
        this.multiplicadorPontos = MULTIPLICADOR_INICIAL;
    }

}
