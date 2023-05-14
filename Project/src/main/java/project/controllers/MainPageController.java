package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.HelloApplication;
import project.domain.*;
import project.service.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageController {
    @FXML
    private Button makeReservationMonday;
    @FXML
    private Button makeReservationTuesday;
    @FXML
    private Button makeReservationWednesday;
    @FXML
    private Button makeReservationThursday;
    @FXML
    private Button makeReservationFriday;
    @FXML
    private Button makeReservationSaturday;
    @FXML
    private Button makeReservationSunday;
    @FXML
    private Label labelWelcome;
    @FXML
    private TableColumn<Reservation, Status> accountTableReservationStatus;
    @FXML
    private TableColumn<Reservation, LocalDate> accountTableReservationData;
    @FXML
    private TableColumn<Reservation, Integer> accountTableReservationMovieHall;
    @FXML
    private TableColumn<Reservation, List<Pair<Integer, Integer>>> accountTableReservationLocuri;
    @FXML
    private TableColumn<Reservation, String> accountTableReservationName;
    @FXML
    private TableColumn<Reservation, LocalTime> accountTableReservationOra;
    @FXML
    private TableView<Reservation> accountTableReservation;
    @FXML
    private ChoiceBox<String> sliderWednesdayProgram;
    @FXML
    private ChoiceBox<String> sliderThursdayProgram;
    @FXML
    private ChoiceBox<String> sliderFridayProgram;
    @FXML
    private ChoiceBox<String> sliderSaturdayProgram;
    @FXML
    private ChoiceBox<String> sliderTuesdayProgram;
    @FXML
    private ChoiceBox<String> sliderMondayProgram;
    @FXML
    private ChoiceBox<String> sliderSundayProgram;
    @FXML
    private TableView<MovieScreening> sundayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> sundayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> sundayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> sundayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> sundayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> sundayTableProgramReview;
    @FXML
    private TableView<MovieScreening> saturdayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> saturdayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> saturdayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> saturdayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> saturdayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> saturdayTableProgramReview;
    @FXML
    private TableView<MovieScreening> fridayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> fridayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> fridayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> fridayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> fridayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> fridayTableProgramReview;
    @FXML
    private TableView<MovieScreening> thursdayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> thursdayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> thursdayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> thursdayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> thursdayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> thursdayTableProgramReview;
    @FXML
    private TableView<MovieScreening> wednesdayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> wednesdayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> wednesdayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> wednesdayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> wednesdayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> wednesdayTableProgramReview;
    @FXML
    private TableView<MovieScreening> tuesdayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> tuesdayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> tuesdayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> tuesdayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> tuesdayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> tuesdayTableProgramReview;
    @FXML
    private TableColumn<MovieScreening, String> mondayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, LocalTime> mondayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, String> mondayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> mondayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, Double> mondayTableProgramReview;
    @FXML
    private TableView<MovieScreening> mondayTableProgram;
    private final ObservableList<MovieScreening> modelMondayProgram = FXCollections.observableArrayList();
    private final ObservableList<MovieScreening> modelTuesdayProgram = FXCollections.observableArrayList();
    private final ObservableList<MovieScreening> modelWednesdayProgram = FXCollections.observableArrayList();
    private final ObservableList<MovieScreening> modelThursdayProgram = FXCollections.observableArrayList();
    private final ObservableList<MovieScreening> modelFridayProgram = FXCollections.observableArrayList();
    private final ObservableList<MovieScreening> modelSaturdayProgram = FXCollections.observableArrayList();
    private final ObservableList<MovieScreening> modelSundayProgram = FXCollections.observableArrayList();
    private final ObservableList<Reservation> modelReservationModel = FXCollections.observableArrayList();
    private Service service;
    private User userCurent;
    private List<LocalDate> times = new ArrayList<>();
    public MainPageController() {}

    public void setService(Service service)
    {
        this.service = service;
        initView();
        initialize();
    }
    public void setUserCurent(User userCurent) {this.userCurent = userCurent;}
    private void initView()
    {
        labelWelcome.setText("Bine ati venit, " + userCurent.getNume().split(" ")[0]);
        LocalDate dateNow = LocalDate.now();
        DayOfWeek dayOfWeek =  dateNow.getDayOfWeek();
        String day = dayOfWeek.toString();
        DayOfWeek dayOfWeek1 = DayOfWeek.MONDAY;
        int dif = dayOfWeek1.getValue() - dayOfWeek.getValue();
        LocalDate date1 = dateNow.plusDays(dif);
        LocalDate date2 = dateNow.plusDays(dif+1);
        LocalDate date3 = dateNow.plusDays(dif+2);
        LocalDate date4 = dateNow.plusDays(dif+3);
        LocalDate date5 = dateNow.plusDays(dif+4);
        LocalDate date6 = dateNow.plusDays(dif+5);
        LocalDate date7 = dateNow.plusDays(dif+6);
        times.add(date1);
        times.add(date2);
        times.add(date3);
        times.add(date4);
        times.add(date5);
        times.add(date6);
        times.add(date7);

        if(day.equals("MONDAY"))
        {
            service.addMovieScreenings(times);
        }

        List<MovieScreening> movieScreeningList1 = service.findMovieScreenFromDay(date1);
        ObservableList<String> numeMovies1 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList1.size(); i++)
        {
            numeMovies1.add(movieScreeningList1.get(i).getMovieName());
        }
        numeMovies1.add("");
        sliderMondayProgram.setItems(numeMovies1);
        modelMondayProgram.setAll(new ArrayList<>());
        modelMondayProgram.addAll(movieScreeningList1);

        List<MovieScreening> movieScreeningList2 = service.findMovieScreenFromDay(date2);
        ObservableList<String> numeMovies2 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList2.size(); i++)
        {
            numeMovies2.add(movieScreeningList2.get(i).getMovieName());
        }
        numeMovies2.add("");
        sliderTuesdayProgram.setItems(numeMovies2);
        modelTuesdayProgram.setAll(new ArrayList<>());
        modelTuesdayProgram.addAll(movieScreeningList2);

        List<MovieScreening> movieScreeningList3 = service.findMovieScreenFromDay(date3);
        ObservableList<String> numeMovies3 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList3.size(); i++)
        {
            numeMovies3.add(movieScreeningList3.get(i).getMovieName());
        }
        numeMovies3.add("");
        sliderWednesdayProgram.setItems(numeMovies3);
        modelWednesdayProgram.setAll(new ArrayList<>());
        modelWednesdayProgram.addAll(movieScreeningList3);

        List<MovieScreening> movieScreeningList4 = service.findMovieScreenFromDay(date4);
        ObservableList<String> numeMovies4 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList4.size(); i++)
        {
            numeMovies4.add(movieScreeningList4.get(i).getMovieName());
        }
        numeMovies4.add("");
        sliderThursdayProgram.setItems(numeMovies4);
        modelThursdayProgram.setAll(new ArrayList<>());
        modelThursdayProgram.addAll(movieScreeningList4);

        List<MovieScreening> movieScreeningList5 = service.findMovieScreenFromDay(date5);
        ObservableList<String> numeMovies5 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList5.size(); i++)
        {
            numeMovies5.add(movieScreeningList5.get(i).getMovieName());
        }
        numeMovies5.add("");
        sliderFridayProgram.setItems(numeMovies5);
        modelFridayProgram.setAll(new ArrayList<>());
        modelFridayProgram.addAll(movieScreeningList5);

        List<MovieScreening> movieScreeningList6 = service.findMovieScreenFromDay(date6);
        ObservableList<String> numeMovies6 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList6.size(); i++)
        {
            numeMovies6.add(movieScreeningList6.get(i).getMovieName());
        }
        numeMovies6.add("");
        sliderSaturdayProgram.setItems(numeMovies6);
        modelSaturdayProgram.setAll(new ArrayList<>());
        modelSaturdayProgram.addAll(movieScreeningList6);

        List<MovieScreening> movieScreeningList7 = service.findMovieScreenFromDay(date7);
        ObservableList<String> numeMovies7 = FXCollections.observableArrayList();
        for(int i = 0; i < movieScreeningList7.size(); i++)
        {
            numeMovies7.add(movieScreeningList7.get(i).getMovieName());
        }
        numeMovies7.add("");
        sliderSundayProgram.setItems(numeMovies7);
        modelSundayProgram.setAll(new ArrayList<>());
        modelSundayProgram.addAll(movieScreeningList7);

        if(dateNow.getDayOfWeek().getValue() > date1.getDayOfWeek().getValue())
        {
            makeReservationMonday.setDisable(true);
        }
        if(dateNow.getDayOfWeek().getValue() > date2.getDayOfWeek().getValue())
        {
            makeReservationTuesday.setDisable(true);
        }
        if(dateNow.getDayOfWeek().getValue() > date3.getDayOfWeek().getValue())
        {
            makeReservationWednesday.setDisable(true);
        }
        if(dateNow.getDayOfWeek().getValue() > date4.getDayOfWeek().getValue())
        {
            makeReservationThursday.setDisable(true);
        }
        if(dateNow.getDayOfWeek().getValue() > date5.getDayOfWeek().getValue())
        {
            makeReservationFriday.setDisable(true);
        }
        if(dateNow.getDayOfWeek().getValue() > date6.getDayOfWeek().getValue())
        {
            makeReservationSaturday.setDisable(true);
        }
        if(dateNow.getDayOfWeek().getValue() > date7.getDayOfWeek().getValue())
        {
            makeReservationSunday.setDisable(true);
        }
        List<Reservation> reservationListUser = service.findAllReservationsFromUser(userCurent);
        modelReservationModel.setAll(new ArrayList<>());
        modelReservationModel.addAll(reservationListUser);
    }
    private void initialize()
    {
        mondayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        mondayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        mondayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        mondayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        mondayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        mondayTableProgram.setItems(modelMondayProgram);

        tuesdayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        tuesdayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        tuesdayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        tuesdayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        tuesdayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        tuesdayTableProgram.setItems(modelTuesdayProgram);

        wednesdayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        wednesdayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        wednesdayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        wednesdayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        wednesdayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        wednesdayTableProgram.setItems(modelWednesdayProgram);

        thursdayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        thursdayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        thursdayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        thursdayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        thursdayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        thursdayTableProgram.setItems(modelThursdayProgram);

        fridayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        fridayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        fridayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        fridayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        fridayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        fridayTableProgram.setItems(modelFridayProgram);

        saturdayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        saturdayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        saturdayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        saturdayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        saturdayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        saturdayTableProgram.setItems(modelSaturdayProgram);

        sundayTableProgramName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        sundayTableProgramOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        sundayTableProgramTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        sundayTableProgramGen.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        sundayTableProgramReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        sundayTableProgram.setItems(modelSundayProgram);

        accountTableReservationName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        accountTableReservationOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        accountTableReservationData.setCellValueFactory(new PropertyValueFactory<>("Data"));
        accountTableReservationLocuri.setCellValueFactory(new PropertyValueFactory<>("Seats"));
        accountTableReservationMovieHall.setCellValueFactory(new PropertyValueFactory<>("MovieHall"));
        accountTableReservationStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        accountTableReservation.setItems(modelReservationModel);
    }
    @FXML
    private void handleFilterByNameMonday(ActionEvent mouseEvent)
    {
        String movieName = sliderMondayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(0));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(0)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelMondayProgram.setAll(new ArrayList<>());
        modelMondayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleFilterByNameFriday(ActionEvent mouseEvent)
    {
        String movieName = sliderFridayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(4));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(4)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelFridayProgram.setAll(new ArrayList<>());
        modelFridayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleFilterByNameTuesday(ActionEvent actionEvent)
    {
        String movieName = sliderTuesdayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(1));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(1)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelTuesdayProgram.setAll(new ArrayList<>());
        modelTuesdayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleFilterByNameWednesday(ActionEvent actionEvent)
    {
        String movieName = sliderWednesdayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(2));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(2)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelWednesdayProgram.setAll(new ArrayList<>());
        modelWednesdayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleFilterByNameThursday(ActionEvent actionEvent)
    {
        String movieName = sliderThursdayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(3));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(3)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelThursdayProgram.setAll(new ArrayList<>());
        modelThursdayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleFilterByNameSaturday(ActionEvent actionEvent)
    {
        String movieName = sliderSaturdayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(5));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(5)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelSaturdayProgram.setAll(new ArrayList<>());
        modelSaturdayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleFilterByNameSunday(ActionEvent actionEvent)
    {
        String movieName = sliderSundayProgram.getValue().toString();
        List<MovieScreening> movieScreeningList;
        if(movieName.equals(""))
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(6));
        }
        else
        {
            movieScreeningList = service.findMovieScreenFromDay(times.get(6)).stream().filter(x->x.getMovieName().equals(movieName)).toList();
        }
        modelSundayProgram.setAll(new ArrayList<>());
        modelSundayProgram.addAll(movieScreeningList);
    }
    @FXML
    private void handleDeleteReservation(ActionEvent actionEvent)
    {
        Reservation reservation = accountTableReservation.getSelectionModel().getSelectedItem();
        service.deleteReservation(reservation);
        refreshData();
    }
    public void refreshData()
    {
        List<Reservation> reservationListUser = service.findAllReservationsFromUser(userCurent);
        modelReservationModel.setAll(new ArrayList<>());
        modelReservationModel.addAll(reservationListUser);
    }
    @FXML
    private void handleUpdateReservation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/update-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 506, 290);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        UpdateController appController = fxmlLoader.getController();
        appController.setRezervareaCurenta(accountTableReservation.getSelectionModel().getSelectedItem());
        appController.setMainPageController(this);
        appController.setService(this.service);
    }
    @FXML
    private void makeReservationMonday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = mondayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationTuesday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = tuesdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationWednesday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = wednesdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationThursday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = thursdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationFriday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = fridayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationSaturday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = saturdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationSunday(ActionEvent actionEvent) throws IOException {
        MovieScreening movieScreening = sundayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening.getTime().isAfter(LocalTime.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/rezerva-view1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ReserveController1 appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
}
