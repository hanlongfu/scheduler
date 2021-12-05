module Udemy {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    exports sample;

    opens sample.controller;
    opens sample.view;
    opens sample.assets;

}