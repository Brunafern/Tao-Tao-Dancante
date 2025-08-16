package jogo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import principal.MainApplication;

public class MenuController {

    /**
     * @param evento Evento de ação começar do JavaFX.
     */
    @FXML
    private void aoPressionarComecar(ActionEvent evento) {
        System.out.println("Botão Começar pressionado!");
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
