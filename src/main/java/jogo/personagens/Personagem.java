package jogo.personagens;

import javafx.scene.image.ImageView;

public abstract class Personagem extends ImageView {

    /**
     * @param width  largura do personagem (pixels)
     * @param height altura do personagem (pixels)
     */
    public Personagem(double width, double height) {
        this.setFitWidth(width);
        this.setFitHeight(height);
    }
}
