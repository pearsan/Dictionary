module Application {
    //requires javafx.controls;
    //requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires freetts;
    requires java.sql;
    //requires javafx.controls;


    opens Application to javafx.fxml;
    exports Application;
}