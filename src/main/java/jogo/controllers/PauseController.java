package jogo.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import jogo.componentes.ControleVolume;
import jogo.interfaces.PauseInterface;

import java.net.URL;
import java.util.ResourceBundle;

public class PauseController implements Initializable {

    private static final double POSICAO_ESQUERDA_CONTROLE_VOLUME = 20.0;
    private static final double POSICAO_INFERIOR_CONTROLE_VOLUME = 20.0;

    private PauseInterface gestorDePause;
    private ControleVolume controleVolume;
    private MediaPlayer reprodutorMidia;

    @FXML
    private AnchorPane telaPause;

    /**
     * @param gestorDePause Instância do GestorDePause.
     */
    public void setGestorDePause(PauseInterface gestorDePause) {
        this.gestorDePause = gestorDePause;
    }

    /**
     * @param reprodutorMidia MediaPlayer do JavaFX.
     */
    public void setReprodutorMidia(MediaPlayer reprodutorMidia) {
        this.reprodutorMidia = reprodutorMidia;

        if (controleVolume != null) {
            controleVolume.setReprodutorMidia(reprodutorMidia);
        }
    }

    /**
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
