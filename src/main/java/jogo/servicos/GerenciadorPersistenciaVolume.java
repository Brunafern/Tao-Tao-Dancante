package jogo.servicos;

import java.util.prefs.Preferences;

/**
 * Utiliza a API {@link Preferences} para salvar, carregar, resetar ou limpar valores,
 * garantindo que a configuração seja mantida entre execuções.
 */
public class GerenciadorPersistenciaVolume {

    private static final String CHAVE_VOLUME = "volume_jogo";

    private static final double VOLUME_PADRAO = 1.0;

    private final Preferences prefs;

    public GerenciadorPersistenciaVolume() {
        this.prefs = Preferences.userNodeForPackage(GerenciadorPersistenciaVolume.class);
    }

    /**
     * @param volume valor do volume entre 0.0 (mudo) e 1.0 (100%).
     */
    public void salvarVolume(double volume) {
        try {
            prefs.putDouble(CHAVE_VOLUME, volume);
            prefs.flush(); // garante persistência imediata
        } catch (Exception e) {
            System.err.println("❌ Erro ao salvar volume no SO: " + e.getMessage());
        }
    }

    /**
     * @return volume entre 0.0 e 1.0.
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
