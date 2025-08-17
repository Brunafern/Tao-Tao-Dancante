package jogo.personagens;

import jogo.excecoes.PlacarDeVidaException;
import jogo.excecoes.PersonagemInvalidoException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Bardo extends PersonagemJogo {

    private static final double DURACAO_KEYFRAME_MILLIS = 400.0;

    // Imagens para animação
    private final Image imagemPadrao;
    private final Image imagemAlternativa;

    // Animação de troca de frames
    private final Timeline animacao;

    /**
     * @param largura largura do personagem em pixels
     * @param altura  altura do personagem em pixels
     * @throws PlacarDeVidaException se algum parâmetro for inválido
     */
    public Bardo(double largura, double altura) throws PersonagemInvalidoException, PlacarDeVidaException {
        super("Bardo", largura, altura);

        this.imagemPadrao = new Image(getClass().getResource("/assets/persona/bardoDance1.png").toExternalForm());
        this.imagemAlternativa = new Image(getClass().getResource("/assets/persona/bardoDance2.png").toExternalForm());

        this.setImage(imagemPadrao);

        // Configura animação contínua alternando frames
        this.animacao = new Timeline(
                new KeyFrame(Duration.millis(DURACAO_KEYFRAME_MILLIS), e -> alternarFrame()));
        this.animacao.setCycleCount(Timeline.INDEFINITE);
        this.animacao.play();
    }

    private void alternarFrame() {
        if (this.getImage() == imagemPadrao) {
            this.setImage(imagemAlternativa);
        } else {
            this.setImage(imagemPadrao);
        }
    }

    /**
     * @return Timeline da animação contínua
     */
    public Timeline getAnimacao() {
        return animacao;
    }

}
