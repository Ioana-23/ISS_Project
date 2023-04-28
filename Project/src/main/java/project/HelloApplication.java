package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.controllers.LoginController;
import project.domain.Genre;
import project.domain.Seat;
import project.domain.Status;
import project.domain.Tip;
import project.repo.*;
import project.service.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static javafx.application.Application.launch;

public class HelloApplication extends Application {
    private Service service;
    private DatabaseRepositoryUser repoUser;
    private DatabaseRepositoryMovie repoMovie;
    private DatabaseRepositoryMovieHall repoMovieHall;
    private DatabaseRepositoryReservation repoReservation;
    private DatabaseRepositorySeat repoSeat;
    private DatabaseRepositoryMovieHallSeats repoMovieHallSeats;
    private DatabaseRepositoryReservationSeats repoReservationSeats;
    private DatabaseRepositoryMovieReview repoMovieReview;
    private DatabaseRepositoryMovieScreening repoMovieScreening;
    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        repoMovie = new DatabaseRepositoryMovie();
        repoMovieReview = new DatabaseRepositoryMovieReview();
        repoReservationSeats = new DatabaseRepositoryReservationSeats();
        repoReservation = new DatabaseRepositoryReservation();
        repoSeat = new DatabaseRepositorySeat();
        repoMovieHall = new DatabaseRepositoryMovieHall();
        repoUser = new DatabaseRepositoryUser();
        repoMovieScreening = new DatabaseRepositoryMovieScreening();
        service = new Service(repoUser, repoMovie, repoMovieHall, repoReservation, repoSeat, repoMovieHallSeats, repoReservationSeats, repoMovieReview, repoMovieScreening);
        iniView(primaryStage);
        primaryStage.setWidth(418);
        primaryStage.setHeight(344);
        primaryStage.show();
    }
    public void iniView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/login-view.fxml"));
        AnchorPane userLayout = (AnchorPane) fxmlLoader.load();
        stage.setScene(new Scene(userLayout, 418,344));
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
    }
}
