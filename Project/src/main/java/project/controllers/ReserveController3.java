package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.HelloApplication;
import project.domain.*;
import project.service.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReserveController3 {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button reserve;
    @FXML
    private Button buy;
    private Service service;
    private Reservation reservation;
    private MainPageController mainPageController;
    private List<Properties> properties;
    private CreditProperties creditProperties;
    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    public void setMainPageController(MainPageController mainPageController) {this.mainPageController = mainPageController;}
    public ReserveController3() {}
    public void setService(Service service) {this.service = service;}
    public void setReservation(Reservation reservation) {this.reservation = reservation;}
    @FXML
    private void handleReservation(ActionEvent actionEvent)
    {
        anchor.getChildren().clear();
        Label label = new Label("Confirmati informatiile: ");
        label.setLayoutX(15.0);
        label.setLayoutY(anchor.getLayoutY()/2.0);
        label.setStyle("-fx-font-size: 15px; -fx-border-color: BLACK; -fx-border-style: solid");
        String nrTelefon = "";
        for(int i = 0; i < reservation.getUser().getNumarTelefon().size(); i++)
        {
            nrTelefon += reservation.getUser().getNumarTelefon().get(i);
        }
        Label labelInformatiiUser = new Label("Email user: " + reservation.getUser().getEmail() + "\nNumar telefon: " + nrTelefon);
        labelInformatiiUser.setStyle("-fx-font-size: 15px; -fx-text-fill: PURPLE; -fx-font-weight: bold");
        labelInformatiiUser.setLayoutX(15);
        labelInformatiiUser.setLayoutY(30);
        Label labelInformatiiMovie = new Label("Film: " + reservation.getMovieName() + "\nTip: " + reservation.getMovieScreening().getTip() + "\nGen: " + reservation.getMovieScreening().getMovie().getGenre() + "\nData: " + reservation.getMovieScreening().getDate() + "\nTimp: " + reservation.getMovieScreening().getTime());
        labelInformatiiMovie.setStyle("-fx-font-size: 15px; -fx-text-fill: BLUE; -fx-font-weight: bold");
        labelInformatiiMovie.setLayoutX(15);
        labelInformatiiMovie.setLayoutY(70);
        Label labelInformatiiLocuri = new Label("Sala: " + reservation.getMovieHall() + "\nLocuri: " + reservation.getSeatsReserved());
        labelInformatiiLocuri.setStyle("-fx-font-size: 15px; -fx-text-fill: GREEN; -fx-font-weight: bold");
        labelInformatiiLocuri.setLayoutX(15);
        labelInformatiiLocuri.setLayoutY(175);
        Button button = new Button("Confirma");
        button.setLayoutX(400);
        button.setLayoutY(230);
        button.setOnAction(this::handleSendReservation1);
        Button backButton = new Button("Inapoi");
        backButton.setLayoutX(15);
        backButton.setLayoutY(230);
        backButton.setOnAction(this::handleBack);
        anchor.getChildren().add(label);
        anchor.getChildren().add(labelInformatiiUser);
        anchor.getChildren().add(labelInformatiiMovie);
        anchor.getChildren().add(labelInformatiiLocuri);
        anchor.getChildren().add(button);
        anchor.getChildren().add(backButton);
    }
    @FXML
    private void handleBack(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view2.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 630);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ReserveController2 appController = fxmlLoader.getController();
        appController.setMainPageController(mainPageController);
        appController.setMovieScreening(reservation.getMovieScreening());
        appController.setUserCurent(reservation.getUser());
        appController.setProperties(properties);
        appController.setService(service);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void handleSendReservation1(ActionEvent actionEvent)
    {
        service.addReservation(reservation.getMovieName(), reservation.getMovieScreening().getMovie().getGenre(), reservation.getMovieScreening().getMovie().getReview(), reservation.getMovieScreening().getMovie().getDate(), reservation.getMovieScreening().getDate(), reservation.getMovieScreening().getTime(), reservation.getMovieHall(), reservation.getMovieScreening().getMovieHall().getHallConfiguration(), reservation.getMovieScreening().getTip(), Status.Inregistrata, reservation.getSeatsReserved(), reservation.getUser().getEmail(), reservation.getUser().getParola(), reservation.getUser().getNume(), reservation.getUser().getNumarTelefon());
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        mainPageController.refreshData();
    }
    @FXML
    public void handleBuy(ActionEvent actionEvent) {
        anchor.getChildren().clear();
        Label label = new Label("Introduceti datele cardului:");
        label.setLayoutX(15.0);
        label.setLayoutY(15.0);
        label.setStyle("-fx-font-size: 15px; -fx-border-color: BLACK");
        Label labelNr = new Label("Numarul cardului: ");
        labelNr.setLayoutX(15.0);
        labelNr.setLayoutY(50.0);
        labelNr.setStyle("-fx-text-fill: GREEN; -fx-font-size: 15px");
        TextField textField = new TextField();
        textField.setPromptText("numar");
        textField.setLayoutX(150);
        textField.setLayoutY(45.0);
        Label labelDate = new Label("Data de expirare: ");
        labelDate.setLayoutX(15.0);
        labelDate.setLayoutY(80.0);
        labelDate.setStyle("-fx-text-fill: PURPLE; -fx-font-size: 15px");
        ChoiceBox<String> choiceBoxMonth = new ChoiceBox<>();
        List<String> listaMonth = new ArrayList<>(Arrays.asList("January-01", "February-02", "March-03", "April-04", "May-05", "June-06", "July-07", "August-08", "September-09", "October-10", "November-11", "December-12"));
        ObservableList<String> listaChoiceBox1 = FXCollections.observableArrayList(listaMonth);
        choiceBoxMonth.setItems(listaChoiceBox1);
        choiceBoxMonth.setLayoutX(150);
        choiceBoxMonth.setLayoutY(80.0);
        choiceBoxMonth.setPrefSize(100, 10);
        choiceBoxMonth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate month = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(choiceBoxMonth.getValue().split("-")[1]), 1);
                int days  = 0;
                if(LocalDate.now().isLeapYear() && month.getMonth().toString().equals("FEBRUARY"))
                {
                    days = 29;
                }
                else if(month.toString().equals("FEBRUARY"))
                {
                    days = 28;
                }
                if(!month.toString().equals("FEBRUARY"))
                {
                    days = month.lengthOfMonth();
                }
                ObservableList<Integer> listaDays = FXCollections.observableArrayList();
                for(int i = 1;  i <= days; i++)
                {
                    listaDays.add(i);
                }
                ChoiceBox<Integer> choiceBox = (ChoiceBox<Integer>) anchor.getChildren().get(4);
                choiceBox.setItems(listaDays);
            }
        });
        ChoiceBox<Integer> choiceBoxDay = new ChoiceBox<>();
        choiceBoxDay.setLayoutX(260);
        choiceBoxDay.setLayoutY(80.0);
        choiceBoxDay.setPrefSize(5, 5);
        ChoiceBox<Integer> choiceBoxYear = new ChoiceBox<>();
        choiceBoxYear.setLayoutX(300);
        choiceBoxYear.setLayoutY(80.0);
        ObservableList<Integer> lista = FXCollections.observableArrayList();
        for(int i = LocalDate.now().getYear(); i<=2050; i++)
        {
            lista.add(i);
        }
        choiceBoxYear.setItems(lista);
        Label labelCVV = new Label("Cifrele de securitate");
        labelCVV.setLayoutX(15.0);
        labelCVV.setLayoutY(125);
        labelCVV.setStyle("-fx-text-fill: BLUE; -fx-font-size: 15px");
        TextField textField1 = new TextField();
        textField1.setPromptText("cvv");
        textField1.setLayoutX(150);
        textField1.setPrefSize(50,15);
        textField1.setLayoutY(125);
        Button buttonConfirm = new Button("Confirma");
        buttonConfirm.setLayoutY(225);
        buttonConfirm.setLayoutX(15.0);
        buttonConfirm.setPrefSize(100,50);
        buttonConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String numar = textField.getText();
                LocalDate localDate = LocalDate.of(choiceBoxYear.getValue(), Integer.parseInt(choiceBoxMonth.getValue().split("-")[1]), choiceBoxDay.getValue());
                int cvv = Integer.parseInt(textField1.getText());
                creditProperties = new CreditProperties(numar, localDate, cvv);
                anchor.getChildren().clear();
                Label label = new Label("Confirmati informatiile: ");
                label.setLayoutX(15.0);
                label.setLayoutY(anchor.getLayoutY()/2.0);
                label.setStyle("-fx-font-size: 15px; -fx-border-color: BLACK; -fx-border-style: solid");
                String nrTelefon = "";
                for(int i = 0; i < reservation.getUser().getNumarTelefon().size(); i++)
                {
                    nrTelefon += reservation.getUser().getNumarTelefon().get(i);
                }
                Label labelInformatiiUser = new Label("Email user: " + reservation.getUser().getEmail() + "\nNumar telefon: " + nrTelefon);
                labelInformatiiUser.setStyle("-fx-font-size: 15px; -fx-text-fill: PURPLE; -fx-font-weight: bold");
                labelInformatiiUser.setLayoutX(15);
                labelInformatiiUser.setLayoutY(30);
                Label labelInformatiiMovie = new Label("Film: " + reservation.getMovieName() + "\nTip: " + reservation.getMovieScreening().getTip() + "\nGen: " + reservation.getMovieScreening().getMovie().getGenre() + "\nData: " + reservation.getMovieScreening().getDate() + "\nTimp: " + reservation.getMovieScreening().getTime());
                labelInformatiiMovie.setStyle("-fx-font-size: 15px; -fx-text-fill: BLUE; -fx-font-weight: bold");
                labelInformatiiMovie.setLayoutX(15);
                labelInformatiiMovie.setLayoutY(70);
                Label labelInformatiiLocuri = new Label("Sala: " + reservation.getMovieHall() + "\nLocuri: " + reservation.getSeatsReserved());
                labelInformatiiLocuri.setStyle("-fx-font-size: 15px; -fx-text-fill: GREEN; -fx-font-weight: bold");
                labelInformatiiLocuri.setLayoutX(15);
                labelInformatiiLocuri.setLayoutY(175);
                Label labelInformatiiCredit = new Label("Numar card: " + creditProperties.getNumar() + "\nData expirare: " + creditProperties.getDateExpirare() + "\nCVV: " + creditProperties.getCvv());
                labelInformatiiCredit.setStyle("-fx-font-size: 15px; -fx-text-fill: RED; -fx-font-weight: bold");
                labelInformatiiCredit.setLayoutX(15);
                labelInformatiiCredit.setLayoutY(225);
                Button button = new Button("Confirma");
                button.setLayoutX(400);
                button.setLayoutY(295);
                button.setOnAction(this::handleSendReservation1);
                Button backButton = new Button("Inapoi");
                backButton.setLayoutX(15);
                backButton.setLayoutY(295);
                backButton.setOnAction(this::handleBack);
                anchor.getChildren().add(label);
                anchor.getChildren().add(labelInformatiiUser);
                anchor.getChildren().add(labelInformatiiMovie);
                anchor.getChildren().add(labelInformatiiLocuri);
                anchor.getChildren().add(button);
                anchor.getChildren().add(backButton);
                anchor.getChildren().add(labelInformatiiCredit);
            }

            private void handleBack(ActionEvent actionEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view2.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 600, 630);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                ReserveController2 appController = fxmlLoader.getController();
                appController.setMainPageController(mainPageController);
                appController.setMovieScreening(reservation.getMovieScreening());
                appController.setUserCurent(reservation.getUser());
                appController.setProperties(properties);
                appController.setService(service);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }

            private void handleSendReservation1(ActionEvent actionEvent) {
                service.addReservation(reservation.getMovieName(), reservation.getMovieScreening().getMovie().getGenre(), reservation.getMovieScreening().getMovie().getReview(), reservation.getMovieScreening().getMovie().getDate(), reservation.getMovieScreening().getDate(), reservation.getMovieScreening().getTime(), reservation.getMovieHall(), reservation.getMovieScreening().getMovieHall().getHallConfiguration(), reservation.getMovieScreening().getTip(), Status.Confirmata, reservation.getSeatsReserved(), reservation.getUser().getEmail(), reservation.getUser().getParola(), reservation.getUser().getNume(), reservation.getUser().getNumarTelefon());
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                mainPageController.refreshData();
            }

        });
        anchor.getChildren().add(label);
        anchor.getChildren().add(labelNr);
        anchor.getChildren().add(textField);
        anchor.getChildren().add(labelDate);
        anchor.getChildren().add(choiceBoxDay);
        anchor.getChildren().add(choiceBoxMonth);
        anchor.getChildren().add(choiceBoxYear);
        anchor.getChildren().add(labelCVV);
        anchor.getChildren().add(textField1);
        anchor.getChildren().add(buttonConfirm);
    }
}
