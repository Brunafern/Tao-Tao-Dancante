package jogo.interfaces;

import jogo.excecoes.PersistenciaDadosException;

public interface GerenciadorPersistenciaVolumeInterface {

    /**
     * @param volume valor do volume a ser salvo, normalmente entre 0.0 (mudo) e 1.0 (máximo)
     */
    void salvarVolume(double volume) throws PersistenciaDadosException;

    /**
     * @return o valor do volume salvo; se não encontrado ou inválido, pode retornar um valor padrão (ex: 0.5)
     */
    double carregarVolume();
}
