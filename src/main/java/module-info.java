module UML_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Interface to javafx.fxml;

    exports communication;
    exports Database;
    exports Interface ;
    //exports Test;
    exports UDP;


}