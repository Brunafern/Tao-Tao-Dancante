package jogo.interfaces;

import javafx.stage.Stage;
import jogo.excecoes.FluxoException;
import jogo.excecoes.RecursoException;

public interface FinalizarFaseInterface {
    void finalizarFase(Stage stage, boolean vitoria) throws RecursoException, FluxoException;

}
