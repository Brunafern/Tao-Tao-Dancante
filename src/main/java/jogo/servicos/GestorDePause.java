package jogo.servicos;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import jogo.controllers.PauseController;
import jogo.excecoes.RecursoException;
import jogo.excecoes.FluxoException;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import jogo.componentes.Setas;
import jogo.interfaces.Pause;

public class GestorDePause  implements Pause{

    private static GestorDePause instancia;

    private static final String CAMINHO_TELA_PAUSE = "/pause/pause-view.fxml";
    private static final String CAMINHO_MENU_PRINCIPAL = "/menu/menu-principal-view.fxml";

    private final Pane painelPrincipal;
    private AnchorPane telaPause;
    private boolean jogoPausado = false;
    private final Timeline animacoes;
    private final MediaPlayer reprodutorMidia;
    private final Runnable acaoRetornarJogo;
    private final GerenciadorSetas gerenciadorSetas;

    /**
     * @param painelPrincipal painel principal do jogo
     * @param animacoes animações em execução
     * @param reprodutorMidia reprodutor de música
     * @param setasAtivas lista de setas ativas
     * @param acaoRetornarJogo ação a ser executada ao retornar ao jogo
     * @param gerenciadorSetas gerenciador de setas
     */
    public GestorDePause(Pane painelPrincipal, Timeline animacoes, MediaPlayer reprodutorMidia,
            List<Setas> setasAtivas, Runnable acaoRetornarJogo,
            GerenciadorSetas gerenciadorSetas) {
        this.painelPrincipal = painelPrincipal;
        this.animacoes = animacoes;
        this.reprodutorMidia = reprodutorMidia;
        this.acaoRetornarJogo = acaoRetornarJogo;
        this.gerenciadorSetas = gerenciadorSetas;
    }

    /**
     * @param painelPrincipal  painel principal do jogo
     * @param animacoes        animações em execução
     * @param reprodutorMidia  reprodutor de música
     * @param setasAtivas      lista de setas ativas
     * @param acaoRetornarJogo ação ao retornar ao jogo
     * @param gerenciadorSetas gerenciador de setas
     * @return instância única de {@link GestorDePause}
     */
    public static GestorDePause getInstance(Pane painelPrincipal, Timeline animacoes, MediaPlayer reprodutorMidia,
            List<Setas> setasAtivas, Runnable acaoRetornarJogo,
            GerenciadorSetas gerenciadorSetas) {
        if (instancia == null) {
            instancia = new GestorDePause(painelPrincipal, animacoes, reprodutorMidia, setasAtivas, acaoRetornarJogo,
                    gerenciadorSetas);
        }
        return instancia;
    }

    /**
     * Pausa o jogo, incluindo animações, áudio e spawn de setas,
     * além de exibir a tela de pausa.
     */
    public void pause() throws RecursoException {
        if (!jogoPausado) {
            pausarAnimacoesEAudio();
            pausarGerenciadorSetas();
            pausarAnimacoesDasSetasAtivas();
            exibirTelaDePause();
            jogoPausado = true;
        }
    }

    private void pausarAnimacoesEAudio() {
        if (animacoes != null) {
            animacoes.pause();
        }
        if (reprodutorMidia != null) {
            reprodutorMidia.pause();
        }
    }

    private void pausarGerenciadorSetas() {
        if (gerenciadorSetas != null) {
            gerenciadorSetas.pausaSpawn();
            gerenciadorSetas.setJogoPausado(true);
        }
    }

    private void pausarAnimacoesDasSetasAtivas() {
        if (gerenciadorSetas != null && gerenciadorSetas.getSetasAtivas() != null) {
            for (Setas seta : gerenciadorSetas.getSetasAtivas()) {
                ParallelTransition animacaoSubida = seta.getAnimacaoSubida();
                if (animacaoSubida != null && animacaoSubida.getStatus() == Animation.Status.RUNNING) {
                    animacaoSubida.pause();
                }
            }
        }
    }

    private void exibirTelaDePause() throws RecursoException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CAMINHO_TELA_PAUSE));
            telaPause = fxmlLoader.load();

            PauseController pauseController = fxmlLoader.getController();
            pauseController.setGestorDePause(this);
            pauseController.setReprodutorMidia(reprodutorMidia);

            painelPrincipal.getChildren().add(telaPause);
        } catch (IOException e) {
            throw new RecursoException("Erro ao carregar a tela de pause: " + e.getMessage(), e);
        }
    }

    public void voltar() {
        if (!jogoPausado)
            return;

        retomarAnimacoesEAudio();
        retomarGerenciadorSetas();
        retomarAnimacoesDasSetasAtivas();
        removerTelaDePause();

        jogoPausado = false;
        painelPrincipal.requestFocus();
    }

    private void retomarAnimacoesEAudio() {
        if (animacoes != null) {
            animacoes.play();
        }
        if (reprodutorMidia != null) {
            reprodutorMidia.play();
        }
    }

    private void retomarGerenciadorSetas() {
        if (gerenciadorSetas != null) {
            gerenciadorSetas.retomaSpawn();
            gerenciadorSetas.setJogoPausado(false);
        }
    }

    private void retomarAnimacoesDasSetasAtivas() {
        if (gerenciadorSetas != null && gerenciadorSetas.getSetasAtivas() != null) {
            for (Setas seta : gerenciadorSetas.getSetasAtivas()) {
                ParallelTransition animacaoSubida = seta.getAnimacaoSubida();
                if (animacaoSubida != null && animacaoSubida.getStatus() == Animation.Status.PAUSED) {
                    animacaoSubida.play();
                }
            }
        }
    }

    private void removerTelaDePause() {
        if (telaPause != null) {
            painelPrincipal.getChildren().remove(telaPause);
        }
    }

    /**
     * @return {true} se o jogo estiver pausado, {false} caso contrário.
     */
    public boolean estaPausado() {
        return jogoPausado;
    }

    /**
     * @param evento evento disparado pelo botão de voltar
     */
    public void voltarParaMenu(ActionEvent evento) throws RecursoException, FluxoException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CAMINHO_MENU_PRINCIPAL));
            Parent root = fxmlLoader.load();

            Stage palco = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            palco.setScene(new Scene(root));
            palco.show();
        } catch (IOException erro) {
            throw new RecursoException("Erro ao carregar a tela do menu principal: " + erro.getMessage(), erro);
        } catch (Exception e) {
            throw new FluxoException("Erro inesperado ao tentar voltar para o menu: " + e.getMessage(), e);
        }
    }

    public void sairDoJogo() throws RecursoException, FluxoException {
        try {
            System.exit(0);
        } catch (SecurityException e) {
            throw new RecursoException("Erro ao tentar sair do jogo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new FluxoException("Erro inesperado ao tentar sair do jogo: " + e.getMessage(), e);
        }
    }
}
