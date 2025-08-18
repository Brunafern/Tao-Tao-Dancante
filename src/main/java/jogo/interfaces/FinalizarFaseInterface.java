package jogo.interfaces;

import javafx.stage.Stage;
import jogo.excecoes.FluxoException;
import jogo.excecoes.RecursoException;


public interface FinalizarFaseInterface {

    /**
     * @param stage   o palco (janela) atual do JavaFX onde a fase está sendo exibida
     * @param vitoria true se o jogador venceu a fase, false se perdeu
     * @throws RecursoException se houver erro ao carregar recursos visuais ou sonoros
     * @throws FluxoException   se houver erro de fluxo na transição de cenas
     */
    void finalizarFase(Stage stage, boolean vitoria) throws RecursoException, FluxoException;

}
