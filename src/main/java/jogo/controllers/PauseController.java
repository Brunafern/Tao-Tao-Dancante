package jogo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import jogo.servicos.GestorDePause;
import jogo.componentes.ControleVolume;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class PauseController implements Initializable {

    private static final double POSICAO_ESQUERDA_CONTROLE_VOLUME = 20.0;
    private static final double POSICAO_INFERIOR_CONTROLE_VOLUME = 20.0;

    private GestorDePause gestorDePause;
    private ControleVolume controleVolume;
    private MediaPlayer reprodutorMidia;

    @FXML
    private AnchorPane telaPause;

    /**
     * Define o Gestor de Pause responsável pelas ações de voltar, sair ou ir ao menu.
     * @param gestorDePause Instância do GestorDePause.
     */
    public void setGestorDePause(GestorDePause gestorDePause) {

        this.gestorDePause = gestorDePause;
    }

    /**
     * Define o MediaPlayer que será controlado pelo ControleVolume.
     * @param reprodutorMidia MediaPlayer do JavaFX.
     */
    public void setReprodutorMidia(MediaPlayer reprodutorMidia) {
        this.reprodutorMidia = reprodutorMidia;

        if (controleVolume != null) {
            controleVolume.setReprodutorMidia(reprodutorMidia);
        }
    }

    /**
     * Método chamado automaticamente pelo JavaFX após a injeção do FXML.
     * Inicializa a tela de pausa, criando o ControleVolume conectando o MediaPlayer
     * @param location  URL usada para resolver caminhos relativos (geralmente não usado)
     * @param resources Recursos localizados, se houver (geralmente não usado)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (telaPause != null) {
            criarEAdicionarControleVolume();
            conectarReprodutorMidiaSeDisponivel();
        }
    }

    private void criarEAdicionarControleVolume() {
        controleVolume = new ControleVolume();

        AnchorPane.setLeftAnchor(controleVolume, POSICAO_ESQUERDA_CONTROLE_VOLUME);
        AnchorPane.setBottomAnchor(controleVolume, POSICAO_INFERIOR_CONTROLE_VOLUME);

        telaPause.getChildren().add(controleVolume);
    }

    private void conectarReprodutorMidiaSeDisponivel() {
        if (reprodutorMidia != null) {
            controleVolume.setReprodutorMidia(reprodutorMidia);
        }
    }

    @FXML
    private void voltarAoJogo() {

        gestorDePause.voltar();
    }

    /**
     * @param evento Evento de ação do JavaFX.
     */

    @FXML
    private void voltarAoMenu(ActionEvent evento) {
        try {
            gestorDePause.voltarParaMenu(evento);
        } catch (jogo.excecoes.RecursoException | jogo.excecoes.FluxoException e) {
            System.err.println("Erro ao voltar para o menu: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void sairDoJogo() {
        try {
            gestorDePause.sairDoJogo();
        } catch (jogo.excecoes.RecursoException | jogo.excecoes.FluxoException e) {
            System.err.println("Erro ao sair do jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
