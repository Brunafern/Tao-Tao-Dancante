package jogo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import jogo.excecoes.RecursoException;

public class Transicao1Controller {

    private static final String CAMINHO_MENU_PRINCIPAL = "/menu/menu-principal-view.fxml";
    private static final int NUMERO_FASE_INICIAL = 1;

    /**
     * @param evento Evento de clique do JavaFX
     */
    @FXML
    private void aoPressionarVoltarMenu(ActionEvent evento) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CAMINHO_MENU_PRINCIPAL));
            Parent root = fxmlLoader.load();

            Scene cena = new Scene(root);
            Stage palco = (Stage) ((Node) evento.getSource()).getScene().getWindow();

            palco.setScene(cena);
            palco.show();
        } catch (Exception erro) {
            RecursoException excecao = new RecursoException("Erro ao carregar a tela do menu principal: " + erro.getMessage(), erro);
            System.err.println(excecao.getMessage());
            excecao.printStackTrace();
            // Aqui você pode exibir um alerta para o usuário, se desejar
        }
    }

    /**
     * @param evento Evento de clique do JavaFX
     */
    @FXML
    private void aoPressionarComecarJogo(ActionEvent evento) {
        try {
            Stage palco = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            FaseController faseController = new FaseController();
            faseController.carregarFase(palco, NUMERO_FASE_INICIAL);
        } catch (Exception erro) {
            RecursoException excecao = new RecursoException("Erro ao carregar a fase " + NUMERO_FASE_INICIAL + ": " + erro.getMessage(), erro);
            System.err.println(excecao.getMessage());
            excecao.printStackTrace();
        }
    }
}
