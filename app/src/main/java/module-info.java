 module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;

    //mysql
     requires mysql.connector.j;
     requires java.sql;
     //jBcrypt
     requires jBCrypt;
     requires java.prefs;
     opens com.example.app to javafx.fxml;
    exports com.example.app;
    opens com.example.app.Controller to javafx.fxml;
    opens com.example.app.Controller.items to javafx.fxml;
    opens com.example.app.Controller.Add to javafx.fxml;
    opens com.example.app.Controller.Contract to javafx.fxml;
    opens com.example.app.Controller.Contract.ContractClick to javafx.fxml;
}