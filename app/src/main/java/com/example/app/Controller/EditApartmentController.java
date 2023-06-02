package com.example.app.Controller;

import com.example.app.DB.ApartmentDAO;
import com.example.app.DB.ClientDAO;
import com.example.app.DB.GetRootLink;
import com.example.app.DB.RoomDAO;
import com.example.app.Entity.Apartment;
import com.example.app.Entity.Client;
import com.example.app.FormatDate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditApartmentController implements Initializable {
    @FXML TextField hostName;
    @FXML TextField apartmentName;
    @FXML TextField address;
    @FXML TextField apId;
    @FXML private ImageView imageAp;
    @FXML private Button btnDelete;
    @FXML private Button btnEdit;
    @FXML private Button btnImg;
    @FXML private Button btnRefresh;
    @FXML private Button btnSave;
    @FXML private Label tmpURL;
    Image img =null;
    public void setDetail(Apartment ap){
        img = new Image(GetRootLink.getRootPathForApartment(ap.getApartmentImage()).toString());
        apId.setText(ap.getApartmentId());
        apartmentName.setText(ap.getApartmentName());
        address.setText(ap.getAddress());
        imageAp.setImage(img);
        hostName.setText(ap.getHost().getHostName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextField textFields[] = {apartmentName, address};
        ApartmentDAO apartmentDAO = new ApartmentDAO();
//        Sự kiện khi click vào nút Edit sẽ cho phép sửa các ô input
        btnEdit.setOnMouseClicked(e->{
            for(TextField i : textFields){
                i.setEditable(true);
            }
            btnImg.setDisable(false);

//            Sự kiện click vào nút chọn ảnh
            btnImg.setOnMouseClicked(event1->{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chọn tệp tin hình ảnh");
                FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
                fileChooser.getExtensionFilters().add(imageFilter);
                // Lấy tệp tin được chọn
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    // Tải ảnh từ tệp tin được chọn lên ImageView
                    Image image = new Image(selectedFile.toURI().toString());
                    imageAp.setImage(image);
                    tmpURL.setText(selectedFile.toString());
                    System.out.println(selectedFile.toString());
                }});

//            Sự kiện khi chọn nút Save
            btnSave.setOnMouseClicked(event->{
                if(tmpURL.getText()==""){
                    Apartment apartmentUpdate = new Apartment(
                            apartmentName.getText(),
                            address.getText()
                    );
                    apartmentDAO.updateNoImg(apartmentUpdate, apId.getText());
                }else {
                    String absolute = tmpURL.getText();
                    Path absolutePath = Path.of(absolute);
                    Path destination = Path.of(System.getProperty("user.dir"), "src/main/resources/imageData/objectData/apartmentIMG");
                    try {
                        Files.copy(absolutePath, destination.resolve(absolutePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Path path = Paths.get(absolute);
                    String fileName = path.getFileName().toString();

                    Apartment newApartment = new Apartment(
                            apartmentName.getText(),
                            address.getText(),
                            fileName
                    );

                    apartmentDAO.update(newApartment, apId.getText());
                }
                tmpURL.setText("");
                for(TextField i: textFields){
                    i.setEditable(false);
                }
                address.setEditable(false);
                btnImg.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit success");
                alert.setHeaderText(null);
                alert.setContentText("Your update is complete!");
                alert.showAndWait();
            });
        });

//        Sự kiện khi click vào nút nạp lại
        btnRefresh.setOnMouseClicked(event->{
            Apartment apartment = new Apartment();
            apartment = apartmentDAO.getApById(apId.getText());
            img = new Image(GetRootLink.getRootPathForClient(apartment.getApartmentImage()).toString());
            imageAp.setImage(img);
            apartmentName.setText(apartment.getApartmentName());
            address.setText(apartment.getAddress());
            for(TextField i: textFields){
                i.setEditable(false);
            }
            btnImg.setDisable(true);
        });

        btnDelete.setOnMouseClicked(event->{
            apartmentDAO.delete(apId.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Xóa thành công");
            alert.setHeaderText(null);
            alert.setContentText("Bản ghi đã được xóa thành công.");
            alert.showAndWait();
            // Đóng cửa sổ hiện tại của bản ghi đó
            Stage stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();
        });
    }
}
