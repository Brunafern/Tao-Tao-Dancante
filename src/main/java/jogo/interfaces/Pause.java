package jogo.interfaces;

//Não Refatorado

import javafx.event.ActionEvent;

public interface Pause {
    void pause();
    void voltar();
    boolean estaPausado();
    void voltarParaMenu(ActionEvent event);
    void sairDoJogo();
}

