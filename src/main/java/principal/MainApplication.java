package principal;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import jogo.excecoes.RecursoException;

public class MainApplication extends Application {

    private static Stage mainStage;

    /**
     * @param stage stage principal fornecido pelo JavaFX
     * @throws IOException caso ocorra erro ao carregar o FXML do menu principal
     */

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        try {
            FXMLLoader menu = new FXMLLoader(getClass().getResource("/menu/menu-principal-view.fxml"));
            Scene scene = new Scene(menu.load(), 1236, 804);

            // Ícone da aplicação
            Image icon = new Image(getClass().getResourceAsStream("/assets/icones/icone2.png"));
            mainStage.getIcons().add(icon);

            mainStage.setTitle("Tão Tão Dançante");
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.show();
        } catch (Exception e) {
            RecursoException excecao = new RecursoException("Erro ao carregar recursos iniciais: " + e.getMessage(), e);
            System.err.println(excecao.getMessage());
            excecao.printStackTrace();
            // Aqui pode-se exibir um alerta para o usuário, se desejar
        }
    }

    /**
     * @param fxmlFile caminho do arquivo FXML da nova tela (ex: "/menu/tela2.fxml")
     */
    public static void trocarTela(String fxmlFile) {
        try {
            Parent currentRoot = mainStage.getScene().getRoot();

            // Fade out da tela atual
            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), currentRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
                    Parent newRoot = loader.load();
                    Scene newScene = new Scene(newRoot, 1236, 804);

                    newRoot.setOpacity(0.0); // Prepara fade in
                    mainStage.setScene(newScene);

                    // Fade in da nova tela
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(400), newRoot);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();

                } catch (Exception e) {
                    RecursoException excecao = new RecursoException("Erro ao trocar tela: " + e.getMessage(), e);
                    System.err.println(excecao.getMessage());
                    excecao.printStackTrace();
                }
            });

            fadeOut.play();

        } catch (Exception e) {
            RecursoException excecao = new RecursoException("Erro ao iniciar troca de tela: " + e.getMessage(), e);
            System.err.println(excecao.getMessage());
            excecao.printStackTrace();
        }
    }

    /**
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            try {
                throw new jogo.excecoes.FluxoException("Erro inesperado na aplicação principal", e);
            } catch (jogo.excecoes.FluxoException ex) {
                System.err.println("Erro inesperado na aplicação principal: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
