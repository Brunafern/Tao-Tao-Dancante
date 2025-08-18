package jogo.componentes;


import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;


public class Setas extends ImageView {


    /**
     * @param tipo      Tipo da seta (UP, DOWN, LEFT, RIGHT).
     * @param largura   Largura visual da seta em pixels.
     * @param altura    Altura visual da seta em pixels.
     * @param acaoErro  Runnable que será executado quando o jogador errar a seta.
     */
    public Setas(TipoSetas tipo, double largura, double altura, Runnable acaoErro) {
        this.tipo = tipo;
        this.acaoErro = acaoErro;
        setFitWidth(largura);
        setFitHeight(altura);
        setImage(new Image(Objects.requireNonNull(getClass().getResource(tipo.getCaminhoImagem())).toExternalForm()));
        setOpacity(OPACIDADE_INICIAL_ANIMACAO);
    }


    private static final double OPACIDADE_INICIAL_ANIMACAO = 0.0;
    private static final double OPACIDADE_FINAL_ANIMACAO = 1.0;
    private static final double OPACIDADE_BRILHO_FINAL = 1.0;
    private static final double DURACAO_FLASH_SEGUNDOS = 0.15;
    private static final String ESTILO_BRILHO = "-fx-effect: dropshadow(gaussian, gold, 5, 0.4, 0, 0);";


    private final TipoSetas tipo;
    private ParallelTransition animacaoSubida;
    private final Runnable acaoErro;

    /**
     * @return Tipo da seta.
     */
    public TipoSetas getTipo() {
        return tipo;
    }

    /**
     * @param duracao   Duração total da animação em milissegundos.
     * @param distancia Distância vertical que a seta deve percorrer para cima.
     * @return ParallelTransition contendo ambas as animações (fade + translate).
     */
    public ParallelTransition iniciarAnimacaoSubida(double duracao, double distancia) {
        FadeTransition transicaoFade = new FadeTransition(Duration.millis(duracao), this);
        transicaoFade.setFromValue(OPACIDADE_INICIAL_ANIMACAO);
        transicaoFade.setToValue(OPACIDADE_FINAL_ANIMACAO);


        TranslateTransition transicaoTranslacao = new TranslateTransition(Duration.millis(duracao), this);
        transicaoTranslacao.setByY(-distancia);


        animacaoSubida = new ParallelTransition(transicaoFade, transicaoTranslacao);
        animacaoSubida.play();
        return animacaoSubida;
    }

    /**
     * @param painelPai    Painel onde a seta está sendo exibida.
     * @param setasAtivas  Lista de setas atualmente ativas no jogo.
     */
    public void lidarComErro(AnchorPane painelPai, List<Setas> setasAtivas) {
        if (painelPai.getChildren().contains(this)) {
            painelPai.getChildren().remove(this);
        }
        setasAtivas.remove(this);


        if (acaoErro != null) {
            acaoErro.run();
        }
    }


    public void esconder() {
        this.setVisible(false);
        if (animacaoSubida != null && animacaoSubida.getStatus() == javafx.animation.Animation.Status.RUNNING) {
            animacaoSubida.stop();
        }
    }

    /**
     * @return Instância de ParallelTransition contendo a animação atual.
     */
    public ParallelTransition getAnimacaoSubida() {
        return animacaoSubida;
    }

    public void aplicarEfeitoBrilho() {
        this.setStyle(ESTILO_BRILHO);


        FadeTransition flash = new FadeTransition();
        flash.setNode(this);
        flash.setDuration(Duration.seconds(DURACAO_FLASH_SEGUNDOS));
        flash.setFromValue(OPACIDADE_FINAL_ANIMACAO);
        flash.setToValue(OPACIDADE_BRILHO_FINAL);
        flash.setCycleCount(1);
        flash.setAutoReverse(false);
        flash.play();
    }


    public enum TipoSetas {
        UP("/assets/setas/cima.png"),
        DOWN("/assets/setas/baixo.png"),
        LEFT("/assets/setas/esquerda.png"),
        RIGHT("/assets/setas/direita.png");

        private final String caminhoImagem;

        TipoSetas(String caminhoImagem) {
            this.caminhoImagem = caminhoImagem;
        }

        /**
         * @return Caminho da imagem.
         */
        public String getCaminhoImagem() {
            return caminhoImagem;
        }
    }
}
