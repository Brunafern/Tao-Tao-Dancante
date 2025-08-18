package jogo.personagens;

import javafx.scene.image.Image;
import jogo.excecoes.PersonagemInvalidoException;
import jogo.excecoes.PlacarDeVidaException;

import java.util.Objects;

public class Lorde extends Personagem {

    private final Image imgPensador;
    private final Image imgRaiva;
    private final Image imgFeliz;

    /**
     * @param largura  Largura do personagem em pixels.
     * @param altura Altura do personagem em pixels.
     */
    public Lorde(double largura, double altura) throws PersonagemInvalidoException, PlacarDeVidaException {
        super("Lorde", largura, altura);

        imgPensador = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/persona/lordThinker.png")).toExternalForm());
        imgRaiva = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/persona/lordRaiva.png")).toExternalForm());
        imgFeliz = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/persona/lordFeliz.png")).toExternalForm());

        this.setImage(imgPensador); // Estado inicial do personagem
    }

    public void ficarPensativo() {
        this.setImage(imgPensador);
    }

    public void ficarComRaiva() {
        this.setImage(imgRaiva);
    }

    public void ficarFeliz() {
        this.setImage(imgFeliz);
    }
}
