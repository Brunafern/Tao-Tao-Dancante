package jogo.servicos;

import jogo.excecoes.PersistenciaDadosException;
import jogo.interfaces.GerenciadorPersistenciaVolumeInterface;

import java.util.prefs.Preferences;

/**
 * Utiliza a API {@link Preferences} para salvar, carregar, resetar ou limpar valores,
 * garantindo que a configuração seja mantida entre execuções.
 */
public class GerenciadorPersistenciaVolume implements GerenciadorPersistenciaVolumeInterface {

    private static final String CHAVE_VOLUME = "volume_jogo";
    private static final double VOLUME_PADRAO = 1.0;
    private final Preferences prefs;

    public GerenciadorPersistenciaVolume() {
        this.prefs = Preferences.userNodeForPackage(GerenciadorPersistenciaVolume.class);
    }

    /**
     * @param volume valor do volume entre 0.0 (mudo) e 1.0 (100%).
     */
    public void salvarVolume(double volume) throws PersistenciaDadosException {
        try {
            prefs.putDouble(CHAVE_VOLUME, volume);
            prefs.flush(); // garante persistência imediata
        } catch (Exception e) {
            throw new PersistenciaDadosException("Erro ao salvar volume no SO: " + e.getMessage(), e);
        }
    }

    /**
     * @return volume carregado entre 0.0 e 1.0; retorna 1.0 se não encontrado ou erro.
     */
    public double carregarVolume() {
        try {
            double volume = prefs.getDouble(CHAVE_VOLUME, VOLUME_PADRAO);
            volume = Math.max(0, Math.min(1, volume));
            return volume;
        } catch (Exception e) {
            return VOLUME_PADRAO;
        }
    }

}
