module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.example.ProducerFXForm to javafx.fxml;
    exports org.example.ProducerFXForm;

    opens org.example.ConsumerFXForm to javafx.fxml;
    exports org.example.ConsumerFXForm;

    opens org.example.ServerFXForm to javafx.fxml;
    exports org.example.ServerFXForm;

    opens org.example.launchAll to javafx.fxml;
    exports org.example.launchAll;
}