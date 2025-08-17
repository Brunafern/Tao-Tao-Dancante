module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.prefs; 

    exports jogo.controllers;
    exports jogo.fases;
    exports jogo.personagens;
    exports jogo.componentes;
    exports jogo.servicos;
    exports jogo.excecoes;
    exports principal;

    opens jogo.controllers to javafx.fxml;
    opens jogo.fases to javafx.fxml;
    opens jogo.personagens to javafx.fxml;
    opens principal to javafx.fxml;
}