module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports project;
    opens project to javafx.fxml;
    exports project.controllers;
    opens project.controllers to javafx.fxml;
    exports project.domain;
    opens project.domain to javafx.fxml;
}