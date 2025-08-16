package jogo.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jogo.fases.FaseGenerica;

public class FaseController {

    private static final int NUMERO_MAXIMO_FASES = 3;
    private static final String TITULO_JANELA_BASE = "Tao Tao Dancante - Fase ";
    private static final String CAMINHO_VIEW_FASES = "/fases/fase-view.fxml";

    /**
     * @param stage Janela principal do JavaFX.
     * @param numeroFase Número da fase a ser carregada.
     */
    public void carregarFase(Stage stage, int numeroFase) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CAMINHO_VIEW_FASES));
            loader.setController(new FaseGenerica(numeroFase));

            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setTitle(TITULO_JANELA_BASE + numeroFase);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Erro ao carregar fase " + numeroFase + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param stage Janela principal do JavaFX.
     * @param faseAtual Número da fase atualmente ativa.
     */
    public void carregarProximaFase(Stage stage, int faseAtual) {
        int proximaFase = faseAtual + 1;

        if (proximaFase <= NUMERO_MAXIMO_FASES) {
            carregarFase(stage, proximaFase);
        } else {
            System.out.println("🏆 Todas as fases foram completadas!");
        }
    }

}
