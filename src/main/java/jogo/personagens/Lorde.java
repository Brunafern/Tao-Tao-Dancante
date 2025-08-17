package jogo.personagens;

import jogo.modelo.enume.TipoPersonagem;
import jogo.excecoes.PersonagemInvalidoException;
import javafx.scene.image.Image;
import java.util.Objects;

public class Lorde extends PersonagemJogo {

    private int pontosPrecisao;
    private boolean posturaElegante;

    private final Image imgPensador;
    private final Image imgRaiva;
    private final Image imgFeliz;

    /**
     * @param width  largura do personagem em pixels
     * @param height altura do personagem em pixels
     * @throws PersonagemInvalidoException se algum parâmetro for inválido
     */
    public Lorde(double width, double height) throws PersonagemInvalidoException {
        super("Lorde", TipoPersonagem.LORDE, width, height);

        this.pontosPrecisao = 15;
        this.posturaElegante = true;

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
        this.posturaElegante = false;
    }

    /**
     * Muda o visual do personagem para feliz e mantém postura elegante.
     */
    public void ficarFeliz() {
        this.setImage(imgFeliz);
        this.posturaElegante = true;
    }

}
