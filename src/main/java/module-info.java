module UML_java {
    requires javafx.controls;
    requires javafx.fxml;

    opens Interface to javafx.fxml;

    exports History;
    exports Interface ;
    exports Test;
    exports TCP;
    exports UDP;

}