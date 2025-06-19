module org.example.onlinestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;
    requires java.sql;
    requires jdk.compiler;


    opens org.example.forfinalexam to javafx.fxml;
    exports org.example.forfinalexam;
}