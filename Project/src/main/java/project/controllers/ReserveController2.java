package project.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.HelloApplication;
import project.domain.*;
import project.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReserveController2 {
    @FXML
    private Button backButton;
    @FXML
    private Label hallNumber;
    @FXML
    private AnchorPane anchor;
    private Service service;
    private User userCurent;
    private MovieScreening movieScreening;
    private int nrSeatsSelected = 0;
    private List<Seat> seatsSelected = new ArrayList<>();
    private List<Properties> properties;
    private List<List<Button>> configuration = new ArrayList<>();
    private MainPageController mainPageController;
    private int nrLocuri = 0;

    public ReserveController2() {}

    public void setMainPageController(MainPageController mainPageController) {this.mainPageController = mainPageController;}
    public void setService(Service service) {
        this.service = service;
        initView();
    }
    public void setUserCurent(User userCurent) {
        this.userCurent = userCurent;
    }

    public void setMovieScreening(MovieScreening movieScreening) {
        this.movieScreening = movieScreening;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }
    public void initView()
    {
        for(int i = 0; i < properties.size(); i++)
        {
            nrLocuri+=properties.get(i).getNrLocuri();
        }
        List<Integer> lines = new ArrayList<>();
        for(int i = 0; i < movieScreening.getMovieHall().getHallConfiguration().size(); i++)
        {
            if(!lines.contains(movieScreening.getMovieHall().getHallConfiguration().get(i).getLineNumber()))
            {
                lines.add(movieScreening.getMovieHall().getHallConfiguration().get(i).getLineNumber());
            }
        }
        List<Seat> list = service.getOccupiedSeats(movieScreening);
        hallNumber.setText("Sala " + movieScreening.getMovieHall().getNumber());
        for(int i = 0; i < lines.size(); i++)
        {
            configuration.add(new ArrayList<>());
            AnchorPane anchorPane = new AnchorPane();
            int finalI = i+1;
            List<Seat> seats = movieScreening.getMovieHall().getHallConfiguration().stream().filter(x->x.getLineNumber()== finalI).toList();
            anchorPane.getChildren().clear();
            anchorPane.setLayoutX(35.0);
            for(int j = 0; j < seats.size(); j++)
            {
                anchorPane.setLayoutY(30.0 + 36 * (seats.get(j).getLineNumber()-1));
                Button button = new Button();
                char lineLetter = (char) ('A' + (seats.get(j).getLineNumber()-1));
                button.setText(lineLetter + "/" + seats.get(j).getSeatnumber());
                button.setPrefHeight(35);
                button.setPrefWidth(35);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(nrSeatsSelected!=nrLocuri)
                        {
                            if(button.getStyle().equals("-fx-background-color: ORANGE; -fx-border-color: WHITE;-fx-text-fill: BLACK; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0"))
                            {
                                button.setStyle("-fx-background-color: GREEN; -fx-border-color: BLACK;-fx-text-fill: WHITE; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
                                nrSeatsSelected--;
                                String buttonText = button.getText();
                                int lineNumber = 'A' - buttonText.split("/")[0].toCharArray()[0];
                                lineNumber++;
                                int seatNumber = Integer.parseInt(buttonText.split("/")[1]);
                                seatsSelected.remove(new Seat(lineNumber, seatNumber));
                            }
                            else
                            {
                                button.setStyle("-fx-background-color: ORANGE; -fx-border-color: WHITE;-fx-text-fill: BLACK; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
                                nrSeatsSelected++;
                                String buttonText = button.getText();
                                int lineNumber = buttonText.split("/")[0].toCharArray()[0] - 'A';
                                lineNumber++;
                                int seatNumber = Integer.parseInt(buttonText.split("/")[1]);
                                seatsSelected.add(new Seat(lineNumber, seatNumber));
                            }
                        }
                        else
                        {
                            if(button.getStyle().equals("-fx-background-color: ORANGE; -fx-border-color: WHITE;-fx-text-fill: BLACK; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0"))
                            {
                                button.setStyle("-fx-background-color: GREEN; -fx-border-color: BLACK;-fx-text-fill: WHITE; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
                                nrSeatsSelected--;
                                String buttonText = button.getText();
                                int lineNumber = buttonText.split("/")[0].toCharArray()[0] - 'A';
                                lineNumber++;
                                int seatNumber = Integer.parseInt(buttonText.split("/")[1]);
                                seatsSelected.remove(new Seat(lineNumber, seatNumber));
                            }
                        }
                    }
                });
                button.setPrefSize(35, 35);
                button.setLayoutX(20.0+36*(seats.get(j).getSeatnumber()-1));
                button.setLayoutY(/*2.0 + 40 * (seats.get(j).getLineNumber()-1)*/0);
                button.setMnemonicParsing(false);
                button.setStyle("-fx-background-color: GREEN; -fx-border-color: BLACK;-fx-text-fill: WHITE; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
                int k = 0;
                while(k < list.size())
                {
                    if(list.get(k).getLineNumber()==seats.get(j).getLineNumber() && list.get(k).getSeatnumber()==seats.get(j).getSeatnumber())
                    {
                        button.setStyle("-fx-background-color: GRAY; -fx-border-color: BLACK;-fx-text-fill: WHITE; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
                        button.setDisable(true);
                        break;
                    }
                    k++;
                }
                configuration.get(i).add(button);
                anchorPane.getChildren().addAll((configuration.get(i).get(j)));
            }
            anchor.getChildren().add(anchorPane);
        }
    }
    @FXML
    private void handleContinueReserve(ActionEvent actionEvent) throws IOException {
        if(nrSeatsSelected==nrLocuri)
        {
        Reservation reservation = new Reservation(movieScreening, Status.Inregistrata, userCurent, seatsSelected);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view3.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 523, 325);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ReserveController3 appController = fxmlLoader.getController();
        appController.setProperties(properties);
        appController.setMainPageController(mainPageController);
        appController.setReservation(reservation);
        appController.setService(this.service);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati toate locurile dorite!(" + nrLocuri + ")");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 601, 333);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ReserveController1 appController = fxmlLoader.getController();
        appController.setMainPageController(mainPageController);
        appController.setMovieScreening(movieScreening);
        appController.setUserCurent(userCurent);
        appController.setService(this.service);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
