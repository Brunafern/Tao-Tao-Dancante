package jogo.personagens;

import javafx.scene.image.Image;
import jogo.excecoes.PlacarDeVidaException;
import jogo.excecoes.PersonagemInvalidoException;

import java.util.Objects;

public class Lorde extends PersonagemJogo {

    private final Image imgPensador;
    private final Image imgRaiva;
    private final Image imgFeliz;

    /**
     * @param width  largura do personagem em pixels
     * @param height altura do personagem em pixels
     * @throws PlacarDeVidaException se algum parâmetro for inválido
     */
    public Lorde(double width, double height) throws PersonagemInvalidoException,PlacarDeVidaException {
    super("Lorde", width, height);

        imgPensador = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/persona/lordThinker.png")).toExternalForm());
        imgRaiva = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/persona/lordRaiva.png")).toExternalForm());
        imgFeliz = new Image(Objects.requireNonNull(
                getClass().getResource("/assets/persona/lordFeliz.png")).toExternalForm());

        this.setImage(imgPensador);
    }

    /**
     * Muda o visual do personagem para pensativo.
     */
    public void ficarPensativo() {
        this.setImage(imgPensador);
    }

    /**
     * Muda o visual do personagem para raiva e altera a postura.
     */
    public void ficarComRaiva() {
        this.setImage(imgRaiva);
    }

    /**
     * Muda o visual do personagem para feliz e mantém postura elegante.
     */
    public void ficarFeliz() {
        this.setImage(imgFeliz);
    }

}
