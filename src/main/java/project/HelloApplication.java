package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.controllers.LoginController;
import project.repo.*;
import project.service.Service;

import java.io.IOException;
public class HelloApplication extends Application {
    private Service service;
    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        DatabaseRepositoryMovieHibernate repoMovie = new DatabaseRepositoryMovieHibernate();
        DatabaseRepositoryMovieReviewHibernate repoMovieReview = new DatabaseRepositoryMovieReviewHibernate();
        DatabaseRepositoryReservationHibernate repoReservation = new DatabaseRepositoryReservationHibernate();
        DatabaseRepositorySeatHibernate repoSeat = new DatabaseRepositorySeatHibernate();
        DatabaseRepositoryMovieHallHibernate repoMovieHall = new DatabaseRepositoryMovieHallHibernate();
        DatabaseRepositoryUserHibernate repoUser = new DatabaseRepositoryUserHibernate();
        DatabaseRepositoryMovieScreeningHibernate repoMovieScreening = new DatabaseRepositoryMovieScreeningHibernate();
        DatabaseRepositoryMovieHallSeatsHibernate repoMovieHallSeats = new DatabaseRepositoryMovieHallSeatsHibernate();
        service = new Service(repoUser, repoMovie, repoMovieHall, repoReservation, repoSeat, repoMovieHallSeats, repoMovieReview, repoMovieScreening);
        iniView(primaryStage);
        primaryStage.setWidth(418);
        primaryStage.setHeight(344);
        primaryStage.show();
    }
    public void iniView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/login-view.fxml"));
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout, 418,344));
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
    }
}
