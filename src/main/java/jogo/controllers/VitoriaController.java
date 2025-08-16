package jogo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class VitoriaController {

    private static final String CAMINHO_MENU_PRINCIPAL = "/menu/menu-principal-view.fxml";

    /**
     * @param evento Evento de clique do JavaFX
     */
    @FXML
    private void aoPressionarVoltarMenu(ActionEvent evento) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CAMINHO_MENU_PRINCIPAL));
            Parent root = fxmlLoader.load();

            Stage palco = (Stage) ((Node) evento.getSource()).getScene().getWindow();

            palco.setScene(new Scene(root));
            palco.show();
            root.requestFocus(); // Garante foco na nova cena
        } catch (IOException erro) {
            System.err.println("Erro ao carregar a tela do menu principal: " + erro.getMessage());
            erro.printStackTrace();
        }
    }
}
