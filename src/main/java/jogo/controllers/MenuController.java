package jogo.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import principal.MainApplication;

public class MenuController {

    /**
     * @param evento Evento de ação começar do JavaFX.
     */
    @FXML
    private void aoPressionarComecar(ActionEvent evento) {
        MainApplication.trocarTela("/transicao1/transicao1-view.fxml");
    }

    /**
     * @param evento Evento de ação sair do JavaFX.
     */
    @FXML
    private void aoPressionarSair(ActionEvent evento) {
        Platform.exit();
    }
}
