package jogo.servicos;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jogo.componentes.Setas;
import jogo.excecoes.ArquivoException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GerenciadorSetas {

    private static final double START_X = 300.0;
    private static final double ESPACAMENTO = 165.0;
    private static final double LARGURA_SETA = 190.0;
    private static final double ALTURA_SETA = 180.0;
    private static final double POSICAO_Y_INICIAL = 900.0;
    private static final double DISTANCIA_SUBIDA = POSICAO_Y_INICIAL + ALTURA_SETA;
    private static final Duration DURACAO_ESCONDER_SETA_APOS_BRILHO = Duration.seconds(0.15);

    private final AnchorPane painelPrincipal;
    private final Rectangle zonaAcerto;
    private final MediaPlayer audio;
    private final Runnable acaoFimDeFase;

    private final List<Setas> setasAtivas = new ArrayList<>();
    private Timeline timelineSpawn;
    private Runnable acaoErro;
    private Runnable acaoMiss;

    private boolean jogoTerminou = false;
    private boolean jogoPausado = false;

    private int[] sequenciaSetasFase;
    private int indiceSequencia = 0;

    private Supplier<Double> fornecedorDuracaoAnimacao;
    private Consumer<Boolean> atualizadorPontuacao;
    private Runnable acaoAoIniciarSetas;

    /**
     * @param painelPrincipal painel onde as setas serão adicionadas
     * @param zonaAcerto área da tela onde o jogador deve acertar as setas
     * @param audio player da música da fase
     * @param acaoFimDeFase ação a executar ao término da fase
     */
    public GerenciadorSetas(AnchorPane painelPrincipal, Rectangle zonaAcerto,
                            MediaPlayer audio, Runnable acaoFimDeFase) {
        this.painelPrincipal = painelPrincipal;
        this.zonaAcerto = zonaAcerto;
        this.audio = audio;
        this.acaoFimDeFase = acaoFimDeFase;
    }

    /**
     * @param jogoPausado true para pausar, false para continuar
     */
    public void setJogoPausado(boolean jogoPausado) {
        this.jogoPausado = jogoPausado;
    }

    /**
     * @param jogoTerminou true se jogo terminou, false caso contrário
     */
    public void setJogoTerminou(boolean jogoTerminou) {
        this.jogoTerminou = jogoTerminou;
    }

    /**
     * @param numeroFase número da fase a ser carregada
     */
    public void carregarDadosDaFase(int numeroFase) {
        try {
            this.sequenciaSetasFase = LeitorJSONSimples.carregarSequenciaSetas(numeroFase);
            this.indiceSequencia = 0;
        } catch (ArquivoException e) {
            System.err.println("Erro ao carregar dados da fase: " + e.getMessage());
            e.printStackTrace();
            this.sequenciaSetasFase = new int[0];
        }
    }

    /**
     * @param fornecedor função que retorna a duração em milissegundos
     */
    public void setFornecedorDuracaoAnimacao(Supplier<Double> fornecedor) {
        this.fornecedorDuracaoAnimacao = fornecedor;
    }

    /**
     * @param atualizador consumidor da atualização da pontuação
     */
    public void setAtualizadorPontuacao(Consumer<Boolean> atualizador) {
        this.atualizadorPontuacao = atualizador;
    }

    /**
     * @param acao ação a executar no início das setas
     */
    public void setAcaoAoIniciarSetas(Runnable acao) {
        this.acaoAoIniciarSetas = acao;
    }

    /**
     * @param acaoErro ação a executar ao errar
     */
    public void setAcaoErro(Runnable acaoErro) {
        this.acaoErro = acaoErro;
    }

    /**
     * @param acaoMiss ação a executar em caso de miss
     */
    public void setAcaoMiss(Runnable acaoMiss) {
        this.acaoMiss = acaoMiss;
    }

    /**
     * @throws jogo.excecoes.FluxoException caso ocorra erro na inicialização
     */
    public void iniciar() throws jogo.excecoes.FluxoException {
        try {
            audio.setOnReady(() -> {
                audio.play();
                if (acaoAoIniciarSetas != null)
                    acaoAoIniciarSetas.run();
            });
            audio.setOnEndOfMedia(acaoFimDeFase);
        } catch (Exception e) {
            throw new jogo.excecoes.FluxoException("Erro ao iniciar gerenciador de setas", e);
        }
    }

    public void pararSetas() {
        if (timelineSpawn != null)
            timelineSpawn.stop();
        for (Setas seta : new ArrayList<>(setasAtivas)) {
            ParallelTransition animacao = seta.getAnimacaoSubida();
            if (animacao != null && animacao.getStatus() == Animation.Status.RUNNING)
                animacao.stop();
            painelPrincipal.getChildren().remove(seta);
        }
        setasAtivas.clear();
    }

    public void gerarSeta() {
        if (jogoTerminou || jogoPausado)
            return;
        if (sequenciaSetasFase == null || indiceSequencia >= sequenciaSetasFase.length)
            return;

        Setas.TipoSetas tipo = Setas.TipoSetas.values()[sequenciaSetasFase[indiceSequencia++]];
        Setas novaSeta = new Setas(tipo, LARGURA_SETA, ALTURA_SETA, this::lidarComSetaPerdida);

        novaSeta.setLayoutX(calcularPosicaoX(tipo));
        novaSeta.setLayoutY(POSICAO_Y_INICIAL);
        painelPrincipal.getChildren().add(novaSeta);
        setasAtivas.add(novaSeta);

        double duracao = fornecedorDuracaoAnimacao != null ? fornecedorDuracaoAnimacao.get() : 3000;
        novaSeta.iniciarAnimacaoSubida(duracao, DISTANCIA_SUBIDA).setOnFinished(event -> {
            if (novaSeta.isVisible())
                novaSeta.lidarComErro(painelPrincipal, setasAtivas);
        });
    }

    /**
     * @param tipo tipo da seta
     * @return posição X para a seta
     */
    private double calcularPosicaoX(Setas.TipoSetas tipo) {
        return switch (tipo) {
            case LEFT -> START_X - 10;
            case DOWN -> START_X + (2 * ESPACAMENTO) - 63;
            case UP -> START_X + ESPACAMENTO - 30;
            case RIGHT -> START_X + (3 * ESPACAMENTO) - 90;
        };
    }

    /**
     * @param tecla tecla pressionada
     */
    public void processarTecla(KeyCode tecla) {
        if (jogoTerminou)
            return;

        Setas.TipoSetas tipoTecla = obterTipoSetaPorTecla(tecla);
        if (tipoTecla == null)
            return;

        boolean acertou = tentarAcertarSeta(tipoTecla);
        if (!acertou)
            lidarComErroDeAcerto(tipoTecla);
    }

    /**
     * @param tecla tecla pressionada
     * @return tipo de seta correspondente ou null se tecla inválida
     */
    private Setas.TipoSetas obterTipoSetaPorTecla(KeyCode tecla) {
        return switch (tecla) {
            case LEFT -> Setas.TipoSetas.LEFT;
            case DOWN -> Setas.TipoSetas.DOWN;
            case UP -> Setas.TipoSetas.UP;
            case RIGHT -> Setas.TipoSetas.RIGHT;
            default -> null;
        };
    }

    /**
     * @param tipo tipo da seta que o jogador tentou acertar
     * @return true se acertou alguma seta, false caso contrário
     */
    private boolean tentarAcertarSeta(Setas.TipoSetas tipo) {
        Iterator<Setas> iterator = setasAtivas.iterator();
        while (iterator.hasNext()) {
            Setas seta = iterator.next();
            if (seta.getTipo() == tipo && estaNaZonaDeAcerto(seta)) {
                processarAcerto(seta, iterator);
                return true;
            }
        }
        return false;
    }

    /**
     * @param seta seta a verificar
     * @return true se a seta está dentro da zona de acerto
     */
    private boolean estaNaZonaDeAcerto(Setas seta) {
        double y = seta.getLayoutY() + seta.getTranslateY();
        double topoZona = zonaAcerto.getLayoutY();
        double baseZona = topoZona + zonaAcerto.getHeight();
        return y + seta.getFitHeight() >= topoZona && y <= baseZona;
    }

    /**
     * @param seta     seta que foi acertada
     * @param iterator iterator da lista para remoção segura da seta
     */
    private void processarAcerto(Setas seta, Iterator<Setas> iterator) {
        if (atualizadorPontuacao != null)
            atualizadorPontuacao.accept(true);

        seta.aplicarEfeitoBrilho();
        PauseTransition delay = new PauseTransition(DURACAO_ESCONDER_SETA_APOS_BRILHO);
        delay.setOnFinished(event -> {
            seta.esconder();
            painelPrincipal.getChildren().remove(seta);
        });
        delay.play();

        iterator.remove();
    }

    /**
     * @param tipo tipo da seta errada pressionada
     */
    private void lidarComErroDeAcerto(Setas.TipoSetas tipo) {
        if (atualizadorPontuacao != null)
            atualizadorPontuacao.accept(false);
        if (acaoErro != null)
            acaoErro.run();
    }

    private void lidarComSetaPerdida() {
        if (acaoMiss != null)
            acaoMiss.run();
        if (atualizadorPontuacao != null)
            atualizadorPontuacao.accept(false);
    }

    /**
     * @return lista de setas ativas
     */
    public List<Setas> getSetasAtivas() {
        return setasAtivas;
    }

    public void pausaSpawn() {
        if (timelineSpawn != null)
            timelineSpawn.pause();
    }

    public void retomaSpawn() {
        if (timelineSpawn != null)
            timelineSpawn.play();
    }
}
