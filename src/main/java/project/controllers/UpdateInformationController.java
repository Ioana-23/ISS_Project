package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import project.domain.User;
import project.service.Service;

import java.util.ArrayList;
import java.util.List;

public class UpdateInformationController {
    private Service service;
    private User userCurent;
    private MainPageController mainPageController;
    @FXML
    private TextField emailField, parolaField, numeField, prenumeField, telefonField;

    public void setService(Service service) {
        this.service = service;
        initialize();
    }
    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }
    public void setUserCurent(User userCurent) {
        this.userCurent = userCurent;
    }
    private void initialize()
    {
        numeField.setText(userCurent.getNume().split(" ")[1]);
        prenumeField.setText(userCurent.getNume().split(" ")[0]);
        emailField.setText(userCurent.getEmail());
        parolaField.setText(userCurent.getParola());
        StringBuilder telefon = new StringBuilder();
        for(int i = 0; i < userCurent.getNumarTelefon().size(); i++)
        {
            telefon.append(userCurent.getNumarTelefon().get(i));
        }
        telefonField.setText(telefon.toString());
    }
    @FXML
    private void handleConfirm(ActionEvent actionEvent){
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        String email = emailField.getText();
        String password = parolaField.getText();
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
            service.updateUser(userCurent, user);
            mainPageController.setUserCurent(user);
            mainPageController.initiateLabel();
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
