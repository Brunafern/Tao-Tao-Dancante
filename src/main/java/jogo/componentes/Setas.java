package jogo.componentes;


import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane; // Import AnchorPane
import javafx.util.Duration;
import java.util.List; // Import List para o método errar
import java.util.Objects;




public class Setas extends ImageView {


   public enum TipoSetas {
       UP("/assets/setas/cima.png"),
       DOWN("/assets/setas/baixo.png"),
       LEFT("/assets/setas/esquerda.png"),
       RIGHT("/assets/setas/direita.png");


       private final String imagePath;


       TipoSetas(String imagePath) {
           this.imagePath = imagePath;
       }


       public String getImagePath() {
           return imagePath;
       }
   }


   private TipoSetas type;
   private ParallelTransition riseAnimation;


   public Setas(TipoSetas tipo, double largura, double altura) {
       this.type = tipo;
       setFitWidth(largura);
       setFitHeight(altura);
       setImage(new Image(Objects.requireNonNull(getClass().getResource(tipo.getImagePath())).toExternalForm()));
       setOpacity(0);
   }


   public TipoSetas getType() {
       return type;
   }


   public void setType(TipoSetas tipo) {
       this.type = tipo;
       setImage(new Image(getClass().getResource(type.getImagePath()).toExternalForm()));
   }


   public ParallelTransition subirSetas(double duracao, double diatancia) {
       FadeTransition subindoSetas = new FadeTransition(Duration.millis(duracao), this);
       subindoSetas.setFromValue(0);
       subindoSetas.setToValue(1);


       TranslateTransition subir = new TranslateTransition(Duration.millis(duracao), this);
       subir.setByY(-diatancia);


       riseAnimation = new ParallelTransition(subindoSetas, subir);
       riseAnimation.play();
       return riseAnimation;
   }



   public void esconder() {
       this.setVisible(false);
       if (riseAnimation != null && riseAnimation.getStatus() == javafx.animation.Animation.Status.RUNNING) {
           riseAnimation.stop();
       }
   }



   public void errar(AnchorPane parentPane, List<Setas> activeArrows) {
       if (parentPane.getChildren().contains(this)) {
           parentPane.getChildren().remove(this);
       }
       if (activeArrows.contains(this)) {
           activeArrows.remove(this);
       }
   }



    public void mostar() {
       this.setVisible(true);
       this.setOpacity(1);
   }


   public ParallelTransition getRiseAnimation() {
       return riseAnimation;
   }
}
