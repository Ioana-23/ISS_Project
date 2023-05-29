package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.HelloApplication;
import project.domain.MovieScreening;
import project.domain.Properties;
import project.domain.ReservationProperties;
import project.domain.User;
import project.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketsSelectionController {
    @FXML
    private CheckBox adult, copil, student, pensionar;
    @FXML
    private RadioButton taxaA, taxaP, taxaC, taxaS;
    @FXML
    private ChoiceBox<Integer> nrLocuriC, nrLocuriS, nrLocuriP, nrLocuriA;
    @FXML
    private RadioButton faraTaxaA, faraTaxaS, faraTaxaC, faraTaxaP;
    private Service service;
    private User userCurent;
    private MovieScreening movieScreening;
    private MainPageController mainPageController;
    public void setMainPageController(MainPageController mainPageController) {this.mainPageController = mainPageController;}

    public void setService(Service service)
    {
        this.service = service;
        initView();
    }
    public void setUserCurent(User userCurent) {this.userCurent = userCurent;}
    public void setMovieScreening(MovieScreening movieScreening) {this.movieScreening = movieScreening;}
    private void initView()
    {
        ObservableList<Integer> listaLocuri = FXCollections.observableArrayList();
        for(int i = 1; i <= 10; i++)
        {
            listaLocuri.add(i);
        }
        nrLocuriA.setItems(listaLocuri);
        nrLocuriC.setItems(listaLocuri);
        nrLocuriS.setItems(listaLocuri);
        nrLocuriP.setItems(listaLocuri);
    }
    @FXML
    private void continueReservation(ActionEvent actionEvent) throws IOException {
        List<Properties> properties = new ArrayList<>();
        boolean selectat = false;
        if(adult.isSelected())
        {
            selectat = true;
            if(taxaA.isSelected())
            {
                if(!nrLocuriA.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Adult, true, nrLocuriA.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else if(faraTaxaA.isSelected())
            {
                if(!nrLocuriA.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Adult, false, nrLocuriA.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati taxa pentru ochelari 3D!");
                alert.showAndWait();
            }
        }
        if(copil.isSelected())
        {
            selectat = true;
            if(taxaC.isSelected())
            {
                if(!nrLocuriC.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Copil, true, nrLocuriC.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else if(faraTaxaC.isSelected())
            {
                if(!nrLocuriC.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Copil, false, nrLocuriC.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati taxa pentru ochelari 3D!");
                alert.showAndWait();
            }
        }
        if(student.isSelected())
        {
            selectat = true;
            if(taxaS.isSelected())
            {
                if(!nrLocuriS.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Student, true, nrLocuriS.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else if(taxaS.isSelected())
            {
                if(!nrLocuriS.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Student, false, nrLocuriS.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati taxa pentru ochelari 3D!");
                alert.showAndWait();
            }
        }
        if(pensionar.isSelected())
        {
            selectat = true;
            if(taxaP.isSelected())
            {
                if(!nrLocuriP.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Pensionar, true, nrLocuriP.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else if(taxaP.isSelected())
            {
                if(!nrLocuriP.getSelectionModel().isEmpty())
                {
                    properties.add(new Properties(ReservationProperties.Pensionar, false, nrLocuriP.getValue()));
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati numarul de bilete!");
                    alert.showAndWait();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati taxa pentru ochelari 3D!");
                alert.showAndWait();
            }
        }
        if(properties.size()!=0)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/seats-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 630);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            SeatsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(mainPageController);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setProperties(properties);
            appController.setService(this.service);
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        if(!selectat)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati cel putin un tip de bilete!");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleBack(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void handleDeactivateAdult1() {faraTaxaA.setSelected(false);}
    @FXML
    private void handleDeactivateCopil1() {faraTaxaC.setSelected(false);}
    @FXML
    private void handleDeactivateStudent1() {faraTaxaS.setSelected(false);}
    @FXML
    private void handleDeactivatePensionar1() {faraTaxaP.setSelected(false);}
    @FXML
    private void handleDeactivateAdult2() {taxaA.setSelected(false);}
    @FXML
    private void handleDeactivateCopil2() {taxaC.setSelected(false);}
    @FXML
    private void handleDeactivateStudent2() {taxaS.setSelected(false);}
    @FXML
    private void handleDeactivatePensionar2() {taxaP.setSelected(false);}
}
