module com.example.projectthree {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.desktop;


    opens com.example.projectthree to javafx.fxml;
    exports com.example.projectthree;
}