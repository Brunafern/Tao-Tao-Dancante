package jogo.interfaces;

import javafx.event.ActionEvent;
import jogo.excecoes.FluxoException;
import jogo.excecoes.RecursoException;

public interface PauseInterface {

    void pause() throws RecursoException;

    void voltar();

    /**
     * @return true se o jogo está pausado; false caso contrário
     */
    boolean estaPausado();

    /**
     * @param event evento do JavaFX que disparou essa ação (ex: clique no botão "Menu")
     * @throws RecursoException se houver falha ao carregar recursos do menu
     * @throws FluxoException se houver erro de fluxo ou lógica ao tentar voltar ao menu
     */
    void voltarParaMenu(ActionEvent event) throws RecursoException, FluxoException;

    /**
     * @throws RecursoException se houver erro ao liberar recursos ao sair
     * @throws FluxoException se ocorrer falha de lógica ao tentar sair do jogo
     */
    void sairDoJogo() throws RecursoException, FluxoException;
}
