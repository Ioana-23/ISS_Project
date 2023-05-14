package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
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

public class ReserveController1 {
    @FXML
    private Button backButton;
    @FXML
    private CheckBox adult;
    @FXML
    private CheckBox copil;
    @FXML
    private CheckBox student;
    @FXML
    private CheckBox pensionar;
    @FXML
    private RadioButton taxaA;
    @FXML
    private RadioButton taxaP;
    @FXML
    private RadioButton taxaC;
    @FXML
    private RadioButton taxaS;
    @FXML
    private ChoiceBox<Integer> nrLocuriC;
    @FXML
    private ChoiceBox<Integer> nrLocuriS;
    @FXML
    private ChoiceBox<Integer> nrLocuriP;
    @FXML
    private ChoiceBox<Integer> nrLocuriA;
    @FXML
    private Button rezerva;
    @FXML
    private RadioButton faraTaxaA;
    @FXML
    private RadioButton faraTaxaS;
    @FXML
    private RadioButton faraTaxaC;
    @FXML
    private RadioButton faraTaxaP;
    private Service service;
    private User userCurent;
    private MovieScreening movieScreening;
    private MainPageController mainPageController;

    public ReserveController1() {
    }

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
        if(adult.isSelected())
        {
            if(taxaA.isSelected())
            {
                properties.add(new Properties(ReservationProperties.Adult, true, nrLocuriA.getValue()));
            }
            else
            {
                properties.add(new Properties(ReservationProperties.Adult, false, nrLocuriA.getValue()));
            }
        }
        if(copil.isSelected())
        {
            if(taxaC.isSelected())
            {
                properties.add( new Properties(ReservationProperties.Copil, true, nrLocuriC.getValue()));
            }
            else
            {
                properties.add(new Properties(ReservationProperties.Copil, false, nrLocuriC.getValue()));
            }
        }
        if(student.isSelected())
        {
            if(taxaS.isSelected())
            {
                properties.add(new Properties(ReservationProperties.Student, true, nrLocuriS.getValue()));
            }
            else
            {
                properties.add(new Properties(ReservationProperties.Student, false, nrLocuriS.getValue()));
            }
        }
        if(pensionar.isSelected())
        {
            if(taxaP.isSelected())
            {
                properties.add(new Properties(ReservationProperties.Pensionar, true, nrLocuriP.getValue()));
            }
            else
            {
                properties.add(new Properties(ReservationProperties.Pensionar, false, nrLocuriP.getValue()));
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 630);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ReserveController2 appController = fxmlLoader.getController();
        appController.setMainPageController(mainPageController);
        appController.setUserCurent(userCurent);
        appController.setMovieScreening(movieScreening);
        appController.setProperties(properties);
        appController.setService(this.service);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void handleBack(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
