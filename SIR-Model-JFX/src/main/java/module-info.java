module org.mg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens org.mg to javafx.fxml;
    exports org.mg;
}