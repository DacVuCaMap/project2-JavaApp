package com.example.app.Controller.items;

import com.example.app.Controller.EditClientController;
import com.example.app.Controller.EditHostController;
import com.example.app.Controller.LoginScene;
import com.example.app.DB.GetRootLink;
import com.example.app.Entity.Host;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HostItem implements Initializable {
    @FXML
    private Circle circleImage;

    @FXML
    private Label labelAddress;

    @FXML
    private Button labelDetail;

    @FXML
    private Label labelId;

    @FXML
    private Label labelMail;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPhone;
    private Host host;

    public void setData(Host host){
        this.host=host;
        labelId.setText(host.getHostId());
        Image image = new Image(GetRootLink.getRootPath(host.getHostImage()).toString());
        circleImage.setFill(new ImagePattern(image));
        labelName.setText(host.getHostName());
        labelMail.setText(host.getHostEmail());
        labelPhone.setText(host.getHostPhone());
        labelAddress.setText(host.getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelDetail.setOnAction(e->{
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(LoginScene.getStage);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/app/sceneView/Edit/EditHost.fxml"));
                //load scene
                AnchorPane sceneRoot = fxmlLoader.load();
                //get instance controller
                EditHostController item = fxmlLoader.getController();
                item.setDetail(host);
                //load scene
                Scene dialogScene = new Scene(sceneRoot);
                dialogStage.setResizable(false);
                dialogStage.setScene(dialogScene);
                dialogStage.setTitle("Host details");
                dialogStage.showAndWait();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
