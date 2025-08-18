package jogo.fases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import jogo.excecoes.ArquivoException;
import jogo.personagens.Bardo;
import jogo.personagens.Lorde;
import jogo.servicos.LeitorJSONSimples;

public class Fase1 extends FaseBase {

    private static final double BARDO_POSICAO_X = 890.0;
    private static final double BARDO_POSICAO_Y = 335.0;
    private static final double LORDE_POSICAO_X = 120.0;
    private static final double LORDE_POSICAO_Y = 370.0;
    private static final double BARDO_LARGURA = 282.0;
    private static final double BARDO_ALTURA = 415.0;
    private static final double LORDE_LARGURA = 210.0;
    private static final double LORDE_ALTURA = 380.0;

    private static final double DURACAO_SPAWN_SETA_MILLIS = 1200.0;
    private static final double DURACAO_SUBIDA_INICIAL_BACKUP = 6000.0;
    private static final double DURACAO_SUBIDA_FINAL_BACKUP = 3000.0;
    private static final double TEMPO_ACELERACAO_BACKUP = 28000.0;
    private static final double DURACAO_APOS_ACELERACAO_BACKUP = 2500.0;
    private static final double TEMPO_ACELERACAO_MAXIMA_BACKUP = 74000.0;

    // Dados carregados do JSON
    private String caminhoMusica;
    private String imagemBackground;
    private LeitorJSONSimples.ConfiguracoesTempo configuracoesTempo;

    private Timeline timelineSpawn;

    /**
     * @throws jogo.excecoes.FluxoException se ocorrer erro ao carregar dados da fase.
     */
    public Fase1() throws jogo.excecoes.FluxoException {
        try {
            this.caminhoMusica = LeitorJSONSimples.carregarCaminhoMusica(1);
            this.configuracoesTempo = LeitorJSONSimples.carregarConfiguracoesTempo(1);
        } catch (ArquivoException e) {
            throw new jogo.excecoes.FluxoException("Erro ao carregar dados da fase 1: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new jogo.excecoes.FluxoException("Erro ao inicializar Fase 1", e);
        }
        this.imagemBackground = "/assets/imagens/fase1.png";
    }

    @Override
    protected void inicializarBackground() {
        definirBackground(imagemBackground);
    }

    @Override
    protected void inicializarPersonagens() {
        try {
            bardo = new Bardo(BARDO_LARGURA, BARDO_ALTURA);
            bardo.setLayoutX(BARDO_POSICAO_X);
            bardo.setLayoutY(BARDO_POSICAO_Y);
            telaFase.getChildren().add(bardo);

            lorde = new Lorde(LORDE_LARGURA, LORDE_ALTURA);
            lorde.setLayoutX(LORDE_POSICAO_X);
            lorde.setLayoutY(LORDE_POSICAO_Y);
            telaFase.getChildren().add(lorde);

        } catch (Exception e) {
            System.err.println("Erro ao criar personagens: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void inicializarMusica() {
        var recursoMusica = getClass().getResource(caminhoMusica);
        if (recursoMusica == null) {
            System.err.println("Recurso de música não encontrado: " + caminhoMusica);
            return;
        }

        String musicaPath = recursoMusica.toExternalForm();
        Media media = new Media(musicaPath);
        reprodutorMidia = new MediaPlayer(media);

        jogo.servicos.GerenciadorPersistenciaVolume persistencia = new jogo.servicos.GerenciadorPersistenciaVolume();
        double volumeSalvo = persistencia.carregarVolume();
        reprodutorMidia.setVolume(volumeSalvo);
    }

    @Override
    protected void iniciarFase() {
        pontuacao = 0.5;

        gerenciadorSetas.carregarDadosDaFase(1);
        gerenciadorSetas.setFornecedorDuracaoAnimacao(this::calcularDuracaoSeta);
        gerenciadorSetas.setAtualizadorPontuacao(this::atualizarPontuacao);
        gerenciadorSetas.setAcaoAoIniciarSetas(this::iniciarSpawnSetas);

        try {
            gerenciadorSetas.iniciar();
        } catch (jogo.excecoes.FluxoException e) {
            System.err.println("Erro ao iniciar gerenciador de setas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void iniciarSpawnSetas() {
        gerenciadorSetas.pararSetas();

        if (timelineSpawn != null) {
            timelineSpawn.stop();
        }

        timelineSpawn = new Timeline(new KeyFrame(Duration.millis(DURACAO_SPAWN_SETA_MILLIS), e -> {
            if (reprodutorMidia != null) {
                ajustarVelocidadeSpawn();
            }
            gerenciadorSetas.gerarSeta();
        }));

        timelineSpawn.setCycleCount(Timeline.INDEFINITE);
        timelineSpawn.play();
    }

    private void ajustarVelocidadeSpawn() {
        double tempoAtual = reprodutorMidia.getCurrentTime().toMillis();
        timelineSpawn.setRate(
                tempoAtual >= TEMPO_ACELERACAO_MAXIMA_BACKUP ? 2.5 :
                        tempoAtual >= TEMPO_ACELERACAO_BACKUP ? 1.9 : 1.0
        );
    }

    /**
     * @return duração em milissegundos
     */
    private double calcularDuracaoSeta() {
        if (reprodutorMidia == null) {
            return DURACAO_SUBIDA_INICIAL_BACKUP;
        }

        double tempoAtual = reprodutorMidia.getCurrentTime().toMillis();

        if (configuracoesTempo != null) {
            return configuracoesTempo.calcularDuracao(tempoAtual);
        }

        if (tempoAtual >= TEMPO_ACELERACAO_BACKUP) {
            return DURACAO_APOS_ACELERACAO_BACKUP;
        }

        double progresso = Math.min(1.0, tempoAtual / TEMPO_ACELERACAO_BACKUP);
        return DURACAO_SUBIDA_INICIAL_BACKUP - ((DURACAO_SUBIDA_INICIAL_BACKUP - DURACAO_SUBIDA_FINAL_BACKUP) * progresso);
    }

}
