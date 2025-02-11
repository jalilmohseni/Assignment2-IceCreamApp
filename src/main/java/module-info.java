module org.example.icecreamorderapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.icecreamorderapp to javafx.fxml;
    exports org.example.icecreamorderapp;
}