package jogo.interfaces;

import jogo.excecoes.PersistenciaDadosException;

public interface GerenciadorPersistenciaVolumeInterface {
    void salvarVolume(double volume) throws PersistenciaDadosException;
    double carregarVolume();
}
