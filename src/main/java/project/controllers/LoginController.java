package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.HelloApplication;
import project.domain.User;
import project.service.Service;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passInput;
    private Service service;
    public LoginController() {}
    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) throws IOException {
        String emailText = emailInput.getText();
        String passText = passInput.getText();
        User user = new User(emailText, passText, "", new ArrayList<>());
        if(service.findUser(user)!=-1)
        {
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
        else {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setContentText("The email or password are not correct");
            message.initOwner(null);
            message.showAndWait();
            emailInput.clear();
            passInput.clear();
        }
    }
    @FXML
    private void handleSignUp(ActionEvent actionEvent) throws IOException {
        String emailText = emailInput.getText();
        String passText = passInput.getText();
        User user = new User(emailText, passText, "", new ArrayList<>());
        if(service.findUser(user)==-1)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/complete-information.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            CompleteInformationController appController = fxmlLoader.getController();
            appController.setEmail(emailText);
            appController.setPassword(passText);
            appController.setService(this.service);
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        else {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setContentText("The user already exists");
            message.initOwner(null);
            message.showAndWait();
            emailInput.clear();
            passInput.clear();
        }
    }
}
