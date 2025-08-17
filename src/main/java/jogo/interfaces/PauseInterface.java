package jogo.interfaces;

import javafx.event.ActionEvent;

public interface PauseInterface {
    void pause() throws jogo.excecoes.RecursoException;
    void voltar();
    boolean estaPausado();
    void voltarParaMenu(ActionEvent event) throws jogo.excecoes.RecursoException, jogo.excecoes.FluxoException;
    void sairDoJogo() throws jogo.excecoes.RecursoException, jogo.excecoes.FluxoException;
}