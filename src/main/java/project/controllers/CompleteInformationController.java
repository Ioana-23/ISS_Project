package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.HelloApplication;
import project.domain.User;
import project.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompleteInformationController {
    private String email, password;
    @FXML
    private TextField telefonField, numeField, prenumeField;
    private Service service;
    public void setService(Service service)
    {
        this.service = service;
    }
    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    @FXML
    private void handleAddUser(ActionEvent actionEvent) throws IOException {
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        String telefon = telefonField.getText();
        List<Integer> numarTelefon = new ArrayList<>();
        for(int i = 0; i < telefon.length(); i++)
        {
            char character = telefon.charAt(i);
            try {
                int value = Integer.parseInt(String.valueOf(character));
                numarTelefon.add(value);
            } catch (NumberFormatException e) {
                break;
            }
        }
        if(numarTelefon.size()==10)
        {
            User user = new User(email, password, prenume + " " + nume, numarTelefon);
            service.addUser(user);
            user = service.returnUser(service.findUser(user));
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/mainPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 807, 557);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            MainPageController appController = fxmlLoader.getController();
            appController.setUserCurent(user);
            appController.setService(this.service);
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Numarul de telefon nu este valid");
            telefonField.clear();
            alert.showAndWait();
        }
    }
}
