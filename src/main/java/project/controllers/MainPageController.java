package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Comparator;
import java.util.List;
public class MainPageController
{
    @FXML
    private Slider reviewSelector;
    @FXML
    private Label currentValue;
    @FXML
    private Spinner<Double> reviewValue;
    @FXML
    private MenuItem logOut;
    @FXML
    private TextField searchMovie;
    @FXML
    private TableView<Movie> movieTable, tableWatchList;
    @FXML
    private TableColumn<Movie, String> movieColumnName, watchListNameColumn;
    @FXML
    private TableColumn<Movie, Genre> movieColumnGenre, watchListGenColumn;
    @FXML
    private TableColumn<Movie, Double> movieColumnReview, watchListReviewColumn;
    @FXML
    private DatePicker filterAfterDate;
    @FXML
    private ChoiceBox<String> filterGenreWatchList, filterAfterName, filterNameWatchList;
    @FXML
    private CheckBox reviewMonday, reviewTuesday, reviewWednesday, reviewThursday, reviewFriday, reviewSaturday, reviewSunday;
    @FXML
    private RadioButton monday2, monday1, tuesday2, tuesday1, wednesday2, wednesday1, thursday2, thursday1, friday2, friday1, saturday2, saturday1, sunday2, sunday1;
    @FXML
    private CheckBox oraMonday, oraTuesday, oraWednesday, oraThursday, oraFriday, oraSaturday, oraSunday;
    @FXML
    private Button updateReview, giveReview, makeReservationMonday, makeReservationTuesday, makeReservationWednesday, makeReservationThursday, makeReservationFriday, makeReservationSaturday, makeReservationSunday;
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
    private ChoiceBox<String> sliderWednesdayProgram, sliderThursdayProgram, sliderFridayProgram, sliderSaturdayProgram, sliderTuesdayProgram, sliderMondayProgram, sliderSundayProgram;
    @FXML
    private TableView<MovieScreening> mondayTableProgram, tuesdayTableProgram, wednesdayTableProgram, sundayTableProgram, saturdayTableProgram, fridayTableProgram, thursdayTableProgram;
    @FXML
    private TableColumn<MovieScreening, String> mondayTableProgramTip, tuesdayTableProgramTip, wednesdayTableProgramTip, thursdayTableProgramTip, sundayTableProgramTip,  saturdayTableProgramTip, fridayTableProgramTip;
    @FXML
    private TableColumn<MovieScreening, String> mondayTableProgramName, tuesdayTableProgramName, wednesdayTableProgramName, thursdayTableProgramName, sundayTableProgramName, saturdayTableProgramName, fridayTableProgramName;
    @FXML
    private TableColumn<MovieScreening, String> mondayTableProgramGen, tuesdayTableProgramGen, wednesdayTableProgramGen, thursdayTableProgramGen, sundayTableProgramGen, saturdayTableProgramGen, fridayTableProgramGen;
    @FXML
    private TableColumn<MovieScreening, LocalTime> mondayTableProgramOra, tuesdayTableProgramOra, wednesdayTableProgramOra, sundayTableProgramOra, saturdayTableProgramOra, fridayTableProgramOra, thursdayTableProgramOra;
    @FXML
    private TableColumn<MovieScreening, Double> mondayTableProgramReview, tuesdayTableProgramReview, wednesdayTableProgramReview, thursdayTableProgramReview, sundayTableProgramReview, saturdayTableProgramReview, fridayTableProgramReview;
    private ObservableList<MovieScreening> modelMondayProgram = FXCollections.observableArrayList(), modelTuesdayProgram = FXCollections.observableArrayList(), modelWednesdayProgram = FXCollections.observableArrayList(), modelThursdayProgram = FXCollections.observableArrayList(), modelFridayProgram = FXCollections.observableArrayList(), modelSaturdayProgram = FXCollections.observableArrayList(), modelSundayProgram = FXCollections.observableArrayList();
    private ObservableList<Reservation> modelReservationModel = FXCollections.observableArrayList();
    private ObservableList<Movie> modelMovieWatchList = FXCollections.observableArrayList(), modelMovie = FXCollections.observableArrayList();
    private Service service;
    private User userCurent;
    private final List<LocalDate> times = new ArrayList<>();
    public MainPageController() {}
    public void setService(Service service)
    {
        this.service = service;
        initView();
        initialize();
    }
    public void setUserCurent(User userCurent) {this.userCurent = userCurent;}
    private void fillProgramTables(ObservableList<MovieScreening> modelMovie, LocalDate date, ChoiceBox<String> choiceBoxNames)
    {
        List<MovieScreening> movieScreeningList = service.findMovieScreenFromDay(date);
        ObservableList<String> numeMovies = FXCollections.observableArrayList();
        for (MovieScreening movieScreening : movieScreeningList)
        {
            if (!numeMovies.contains(movieScreening.getMovieName()))
            {
                numeMovies.add(movieScreening.getMovieName());
            }
        }
        numeMovies.add("");
        choiceBoxNames.setItems(numeMovies);
        modelMovie.setAll(movieScreeningList);
    }
    private void disableButtons(LocalDate dateNow, LocalDate date, Button button)
    {
        if(dateNow.getDayOfWeek().getValue() > date.getDayOfWeek().getValue())
        {
            button.setDisable(true);
        }
    }
    public void initiateLabel()
    {
        labelWelcome.setText("Bine ati venit, " + userCurent.getNume().split(" ")[0]);
    }
    private void initView()
    {
        initiateLabel();
        LocalDate dateNow = LocalDate.now();
        DayOfWeek dayOfWeek =  dateNow.getDayOfWeek();
        DayOfWeek dayOfWeek1 = DayOfWeek.MONDAY;
        int dif = dayOfWeek1.getValue() - dayOfWeek.getValue();
        times.addAll(Arrays.asList(dateNow.plusDays(dif), dateNow.plusDays(dif+1), dateNow.plusDays(dif+2), dateNow.plusDays(dif+3), dateNow.plusDays(dif+4), dateNow.plusDays(dif+5), dateNow.plusDays(dif+6)));
        if(service.checkIfShould(times))
        {
            service.addMovieScreenings(times);
        }
        fillProgramTables(modelMondayProgram, times.get(0), sliderMondayProgram);
        fillProgramTables(modelTuesdayProgram, times.get(1), sliderTuesdayProgram);
        fillProgramTables(modelWednesdayProgram, times.get(2), sliderWednesdayProgram);
        fillProgramTables(modelThursdayProgram, times.get(3), sliderThursdayProgram);
        fillProgramTables(modelFridayProgram, times.get(4), sliderFridayProgram);
        fillProgramTables(modelSaturdayProgram, times.get(5), sliderSaturdayProgram);
        fillProgramTables(modelSundayProgram, times.get(6), sliderSundayProgram);
        disableButtons(dateNow, times.get(0), makeReservationMonday);
        disableButtons(dateNow, times.get(1), makeReservationTuesday);
        disableButtons(dateNow, times.get(2), makeReservationWednesday);
        disableButtons(dateNow, times.get(3), makeReservationThursday);
        disableButtons(dateNow, times.get(4), makeReservationFriday);
        disableButtons(dateNow, times.get(5), makeReservationSaturday);
        disableButtons(dateNow, times.get(6), makeReservationSunday);
        List<Reservation> reservationListUser = service.findAllReservationsFromUser(userCurent);
        modelReservationModel.setAll(new ArrayList<>());
        modelReservationModel.addAll(reservationListUser);
        ObservableList<String> values = FXCollections.observableArrayList();
        for (Reservation reservation : reservationListUser)
        {
            if (!values.contains(reservation.getMovieName()))
            {
                values.add(reservation.getMovieName());
            }
        }
        values.add("");
        filterAfterName.setItems(values);
        List<Movie> movies = service.findAllMovies();
        modelMovie.setAll(movies);
        List<Movie> watchList = service.getWatchListForUser(userCurent);
        modelMovieWatchList.setAll(watchList);
        reviewSelector.valueProperty().addListener((observable, oldValue, newValue) -> currentValue.setText(String.valueOf(Math.floor((Double) newValue))));
        refreshFilters(filterNameWatchList, 0);
        refreshFilters(filterGenreWatchList, 1);
    }
    private void initializeColumns(TableColumn<MovieScreening, String> columnName, TableColumn<MovieScreening, LocalTime> columnTime, TableColumn<MovieScreening, String> columnTip, TableColumn<MovieScreening, String> columnGenre, TableColumn<MovieScreening, Double> columnReview, TableView<MovieScreening> table, ObservableList<MovieScreening> modelTable)
    {
        columnName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        columnTip.setCellValueFactory(new PropertyValueFactory<>("TipString"));
        columnGenre.setCellValueFactory(new PropertyValueFactory<>("GenString"));
        columnReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        table.setItems(modelTable);
    }
    private void initialize()
    {
        initializeColumns(mondayTableProgramName, mondayTableProgramOra, mondayTableProgramTip, mondayTableProgramGen, mondayTableProgramReview, mondayTableProgram, modelMondayProgram);
        initializeColumns(tuesdayTableProgramName, tuesdayTableProgramOra, tuesdayTableProgramTip, tuesdayTableProgramGen, tuesdayTableProgramReview, tuesdayTableProgram, modelTuesdayProgram);
        initializeColumns(wednesdayTableProgramName, wednesdayTableProgramOra, wednesdayTableProgramTip, wednesdayTableProgramGen, wednesdayTableProgramReview, wednesdayTableProgram, modelWednesdayProgram);
        initializeColumns(thursdayTableProgramName, thursdayTableProgramOra, thursdayTableProgramTip, thursdayTableProgramGen, thursdayTableProgramReview, thursdayTableProgram, modelThursdayProgram);
        initializeColumns(fridayTableProgramName, fridayTableProgramOra, fridayTableProgramTip, fridayTableProgramGen, fridayTableProgramReview, fridayTableProgram, modelFridayProgram);
        initializeColumns(saturdayTableProgramName, saturdayTableProgramOra, saturdayTableProgramTip, saturdayTableProgramGen, saturdayTableProgramReview, saturdayTableProgram, modelSaturdayProgram);
        initializeColumns(sundayTableProgramName, sundayTableProgramOra, sundayTableProgramTip, sundayTableProgramGen, sundayTableProgramReview, sundayTableProgram, modelSundayProgram);
        accountTableReservationName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        accountTableReservationOra.setCellValueFactory(new PropertyValueFactory<>("Time"));
        accountTableReservationData.setCellValueFactory(new PropertyValueFactory<>("Data"));
        accountTableReservationLocuri.setCellValueFactory(new PropertyValueFactory<>("Seats"));
        accountTableReservationMovieHall.setCellValueFactory(new PropertyValueFactory<>("MovieHall"));
        accountTableReservationStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        accountTableReservation.setItems(modelReservationModel);

        movieColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        movieColumnGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        movieColumnReview.setCellValueFactory(new PropertyValueFactory<>("Review"));
        movieTable.setItems(modelMovie);

        watchListNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        watchListGenColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        watchListReviewColumn.setCellValueFactory(new PropertyValueFactory<>("Review"));
        tableWatchList.setItems(modelMovieWatchList);
    }
    @FXML
    private void handleFilterByNameMonday()
    {
        String movieName = sliderMondayProgram.getValue();
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
    private void handleFilterByNameFriday()
    {
        String movieName = sliderFridayProgram.getValue();
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
    private void handleFilterByNameTuesday()
    {
        String movieName = sliderTuesdayProgram.getValue();
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
    private void handleFilterByNameWednesday()
    {
        String movieName = sliderWednesdayProgram.getValue();
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
    private void handleFilterByNameThursday()
    {
        String movieName = sliderThursdayProgram.getValue();
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
    private void handleFilterByNameSaturday()
    {
        String movieName = sliderSaturdayProgram.getValue();
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
    private void handleFilterByNameSunday()
    {
        String movieName = sliderSundayProgram.getValue();
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
    private void handleDeleteReservation()
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
        ObservableList<String> values = FXCollections.observableArrayList();
        for (Reservation reservation : reservationListUser)
        {
            if (!values.contains(reservation.getMovieName()))
            {
                values.add(reservation.getMovieName());
            }
        }
        values.add("");
        filterAfterName.setItems(values);
    }
    @FXML
    private void handleUpdateReservation() throws IOException {
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
    private void makeReservationMonday() throws IOException {
        MovieScreening movieScreening = mondayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationTuesday() throws IOException {
        MovieScreening movieScreening = tuesdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationWednesday() throws IOException {
        MovieScreening movieScreening = wednesdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationThursday() throws IOException {
        MovieScreening movieScreening = thursdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationFriday() throws IOException {
        MovieScreening movieScreening = fridayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationSaturday() throws IOException {
        MovieScreening movieScreening = saturdayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void makeReservationSunday() throws IOException {
        MovieScreening movieScreening = sundayTableProgram.getSelectionModel().getSelectedItem();
        if(movieScreening==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati un film!");
            alert.showAndWait();
        }
        else if(movieScreening.getDate().isEqual(LocalDate.now()) && movieScreening.getTime().isAfter(LocalTime.now()) || movieScreening.getDate().isAfter(LocalDate.now()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/tickets-selection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 601, 333);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            TicketsSelectionController appController = fxmlLoader.getController();
            appController.setMainPageController(this);
            appController.setUserCurent(userCurent);
            appController.setMovieScreening(movieScreening);
            appController.setService(this.service);
        }
    }
    @FXML
    private void handleDeactivateMonday2() {monday1.setSelected(false);}
    @FXML
    private void handleDeactivateMonday1() {monday2.setSelected(false);}
    @FXML
    private void handleDeactivateTuesday2() {tuesday1.setSelected(false);}
    @FXML
    private void handleDeactivateTuesday1() {tuesday2.setSelected(false);}
    @FXML
    private void handleDeactivateWednesday2() {wednesday1.setSelected(false);}
    @FXML
    private void handleDeactivateWednesday1() {wednesday2.setSelected(false);}
    @FXML
    private void handleDeactivateThursday2() {thursday1.setSelected(false);}
    @FXML
    private void handleDeactivateThursday1() {thursday2.setSelected(false);}
    @FXML
    private void handleDeactivateFriday2() {friday1.setSelected(false);}
    @FXML
    private void handleDeactivateFriday1() {friday2.setSelected(false);}
    @FXML
    private void handleDeactivateSaturday2() {saturday1.setSelected(false);}
    @FXML
    private void handleDeactivateSaturday1() {saturday2.setSelected(false);}
    @FXML
    private void handleDeactivateSunday2() {sunday1.setSelected(false);}
    @FXML
    private void handleDeactivateSunday1() {sunday2.setSelected(false);}
    @FXML
    private void handleSortMonday()
    {
        List<MovieScreening> listaCompleta = mondayTableProgram.getItems();
        if(oraMonday.isSelected() && !reviewMonday.isSelected())
        {
            if(monday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(monday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewMonday.isSelected() && !oraMonday.isSelected())
        {
            if(monday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(monday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraMonday.isSelected() && reviewMonday.isSelected())
        {
            if(monday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(monday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelMondayProgram = FXCollections.observableArrayList(listaCompleta);
        mondayTableProgram.setItems(modelMondayProgram);
    }
    @FXML
    private void handleSortTuesday()
    {
        List<MovieScreening> listaCompleta = tuesdayTableProgram.getItems();
        if(oraTuesday.isSelected() && !reviewTuesday.isSelected())
        {
            if(tuesday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(tuesday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewTuesday.isSelected() && !oraTuesday.isSelected())
        {
            if(tuesday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(tuesday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraTuesday.isSelected() && reviewTuesday.isSelected())
        {
            if(tuesday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(tuesday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelTuesdayProgram = FXCollections.observableArrayList(listaCompleta);
        tuesdayTableProgram.setItems(modelTuesdayProgram);
    }
    @FXML
    private void handleSortWednesday()
    {
        List<MovieScreening> listaCompleta = wednesdayTableProgram.getItems();
        if(oraWednesday.isSelected() && !reviewWednesday.isSelected())
        {
            if(wednesday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(wednesday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewWednesday.isSelected() && !oraWednesday.isSelected())
        {
            if(wednesday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(wednesday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraWednesday.isSelected() && reviewWednesday.isSelected())
        {
            if(wednesday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(wednesday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelWednesdayProgram = FXCollections.observableArrayList(listaCompleta);
        wednesdayTableProgram.setItems(modelWednesdayProgram);
    }
    @FXML
    private void handleSortThursday()
    {
        List<MovieScreening> listaCompleta = thursdayTableProgram.getItems();
        if(oraThursday.isSelected() && !reviewThursday.isSelected())
        {
            if(thursday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(thursday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewThursday.isSelected() && !oraThursday.isSelected())
        {
            if(thursday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(thursday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraThursday.isSelected() && reviewThursday.isSelected())
        {
            if(thursday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(thursday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());                    });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelThursdayProgram = FXCollections.observableArrayList(listaCompleta);
        thursdayTableProgram.setItems(modelThursdayProgram);
    }
    @FXML
    private void handleSortFriday()
    {
        List<MovieScreening> listaCompleta = fridayTableProgram.getItems();
        if(oraFriday.isSelected() && !reviewFriday.isSelected())
        {
            if(friday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(friday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewFriday.isSelected() && !oraFriday.isSelected())
        {
            if(friday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(friday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraFriday.isSelected() && reviewFriday.isSelected())
        {
            if(friday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(friday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());
                });

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelFridayProgram = FXCollections.observableArrayList(listaCompleta);
        fridayTableProgram.setItems(modelFridayProgram);
    }
    @FXML
    private void handleSortSaturday()
    {
        List<MovieScreening> listaCompleta = saturdayTableProgram.getItems();
        if(oraSaturday.isSelected() && !reviewSaturday.isSelected())
        {
            if(saturday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(saturday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewSaturday.isSelected() && !oraSaturday.isSelected())
        {
            if(saturday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(saturday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraSaturday.isSelected() && reviewSaturday.isSelected())
        {
            if(saturday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(saturday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelSaturdayProgram = FXCollections.observableArrayList(listaCompleta);
        saturdayTableProgram.setItems(modelSaturdayProgram);
    }
    @FXML
    private void handleSortSunday()
    {
        List<MovieScreening> listaCompleta = sundayTableProgram.getItems();
        if(oraSunday.isSelected() && !reviewSunday.isSelected())
        {
            if(sunday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getTime));
            }
            else if(sunday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(reviewSunday.isSelected() && !oraSunday.isSelected())
        {
            if(sunday1.isSelected())
            {
                listaCompleta.sort(Comparator.comparing(MovieScreening::getReview));
            }
            else if(sunday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> o2.getReview().compareTo(o1.getReview()));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        if(oraSunday.isSelected() && reviewSunday.isSelected())
        {
            if(sunday1.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o1.getReview().compareTo(o2.getReview());
                    }
                    return o1.getTime().compareTo(o2.getTime());
                });
            }
            else if(sunday2.isSelected())
            {
                listaCompleta.sort((o1, o2) -> {
                    if(o1.getTime().equals(o2.getTime()))
                    {
                        return o2.getReview().compareTo(o1.getReview());
                    }
                    return o2.getTime().compareTo(o1.getTime());
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selectati tipul de sortare!");
                alert.showAndWait();
            }
        }
        modelSundayProgram = FXCollections.observableArrayList(listaCompleta);
        sundayTableProgram.setItems(modelSundayProgram);
    }
    @FXML
    private void handleResetReservations()
    {
        if(filterAfterDate.getValue()!=null)
        {
            String valueSelected = filterAfterName.getValue();
            List<Reservation> reservationList = service.findAllReservationsFromUser(userCurent);
            if(valueSelected!=null && !valueSelected.equals(""))
            {
                reservationList = reservationList.stream().filter(x->x.getMovieName().equals(valueSelected)).toList();
            }
            modelReservationModel = FXCollections.observableArrayList(reservationList);
            ObservableList<String> movieNames = FXCollections.observableArrayList();
            for(Reservation reservation : service.findAllReservationsFromUser(userCurent))
            {
                if(!movieNames.contains(reservation.getMovieName()))
                {
                    movieNames.add(reservation.getMovieName());
                }
            }
            movieNames.add("");
            filterAfterDate.setValue(null);
            filterAfterName.setItems(movieNames);
            filterAfterName.setValue(valueSelected);
        }
    }
    @FXML
    private void handleFilterAfterName()
    {
        String valueSelected = filterAfterName.getValue();
        LocalDate date = filterAfterDate.getValue();
        List<Reservation> reservationList = service.findAllReservationsFromUser(userCurent);
        if(date!=null)
        {
            reservationList = reservationList.stream().filter(x->x.getData().isEqual(date)).toList();
        }
        if(valueSelected!=null && !valueSelected.equals(""))
        {
            reservationList =  reservationList.stream().filter(x->x.getMovieScreening().getMovie().getName().equals(valueSelected)).toList();
        }
        modelReservationModel = FXCollections.observableArrayList(reservationList);
        accountTableReservation.setItems(modelReservationModel);
    }
    @FXML
    private void handleFilterAfterDate()
    {
        String valueSelected = filterAfterName.getValue();
        LocalDate date = filterAfterDate.getValue();
        List<Reservation> reservationList = service.findAllReservationsFromUser(userCurent);
        if(valueSelected!=null && !valueSelected.equals(""))
        {
            reservationList = reservationList.stream().filter(x->x.getMovieName().equals(valueSelected)).toList();
        }
        if(date!=null)
        {
            reservationList =  reservationList.stream().filter(x->x.getMovieScreening().getDate().equals(date)).toList();
        }
        modelReservationModel = FXCollections.observableArrayList(reservationList);
        accountTableReservation.setItems(modelReservationModel);
        ObservableList<String> movieNames = FXCollections.observableArrayList();
        for(Reservation reservation : reservationList)
        {
            if(!movieNames.contains(reservation.getMovieName()))
            {
                movieNames.add(reservation.getMovieName());
            }
        }
        movieNames.add("");
        filterAfterName.setItems(movieNames);
        filterAfterName.setValue(valueSelected);
    }
    @FXML
    private void handleSearchMovie()
    {
        String value = searchMovie.getText();
        List<Movie> movies;
        if(value.equals(""))
        {
            movies = service.findAllMovies();
        }
        else
        {
            movies = service.findAllMovies().stream().filter(x->x.getName().contains(value)).toList();
        }
        modelMovie = FXCollections.observableArrayList(movies);
        movieTable.setItems(modelMovie);
    }
    @FXML
    private void handleAddToWatchList() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        service.addMovieToWatchList(userCurent, selectedMovie);
        modelMovieWatchList.setAll(service.getWatchListForUser(userCurent));
        refreshFilters(filterNameWatchList, 0);
        refreshFilters(filterGenreWatchList, 1);
    }
    @FXML
    private void handleDeleteFromWatchList() {
        Movie movie = tableWatchList.getSelectionModel().getSelectedItem();
        service.deleteMovieFromWatchList(userCurent, movie);
        modelMovieWatchList.setAll(service.getWatchListForUser(userCurent));
        refreshFilters(filterNameWatchList, 0);
        refreshFilters(filterGenreWatchList,1);
    }
    @FXML
    private void handleMovieInformation() throws IOException {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/movie-information.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 601, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        MovieInformationController appController = fxmlLoader.getController();
        appController.setMovie(selectedMovie);
    }
    @FXML
    private void handleLogOut() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 418, 344);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        LoginController appController = fxmlLoader.getController();
        appController.setService(this.service);
        (logOut.getParentPopup().getOwnerWindow()).hide();
    }
    @FXML
    private void handleGiveReview() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        double review = reviewValue.getValue();
        service.addMovieReview(selectedMovie, userCurent, review);
        double value = service.findMovieReview(selectedMovie, userCurent);
        if(value!=-1)
        {
            giveReview.setDisable(true);
            updateReview.setDisable(false);
            reviewValue.getValueFactory().setValue(value);
        }
        else
        {
            giveReview.setDisable(false);
            updateReview.setDisable(true);
            reviewValue.getValueFactory().setValue(1d);
        }
    }
    @FXML
    private void handleUpdateSpinner() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        double value = service.findMovieReview(selectedMovie, userCurent);
        if(value!=-1)
        {
            giveReview.setDisable(true);
            updateReview.setDisable(false);
            reviewValue.getValueFactory().setValue(value);
        }
        else
        {
            giveReview.setDisable(false);
            updateReview.setDisable(true);
            reviewValue.getValueFactory().setValue(1d);
        }
    }
    @FXML
    private void handleUpdateReview() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        double review = reviewValue.getValue();
        service.updateMovieReview(selectedMovie, userCurent, review);
        double value = service.findMovieReview(selectedMovie, userCurent);
        if(value!=-1)
        {
            giveReview.setDisable(true);
            updateReview.setDisable(false);
            reviewValue.getValueFactory().setValue(value);
        }
        else
        {
            giveReview.setDisable(false);
            updateReview.setDisable(true);
            reviewValue.getValueFactory().setValue(1d);
        }
    }
    @FXML
    private void handleSelectWatchListName()
    {
        String nameSelected = filterNameWatchList.getValue();
        String genreSelected = filterGenreWatchList.getValue();
        double reviewSelected = Math.floor(reviewSelector.getValue());
        List<Movie> watchList = service.getWatchListForUser(userCurent);
        if(nameSelected!= null && !nameSelected.equals(""))
        {
            watchList = watchList.stream().filter(x->x.getName().equals(nameSelected)).toList();
        }
        if(genreSelected!=null && !genreSelected.equals(""))
        {
            watchList = watchList.stream().filter(x->x.getGenre().toString().equals(genreSelected)).toList();
        }
        if(reviewSelected!=0d)
        {
            watchList = watchList.stream().filter(x->Math.floor(x.getReview()) == reviewSelected).toList();
        }
        modelMovieWatchList = FXCollections.observableArrayList(watchList);
        if(genreSelected==null || genreSelected.equals(""))
        {
            refreshFilters(filterGenreWatchList, 1);
        }
        tableWatchList.setItems(modelMovieWatchList);
    }
    @FXML
    private void handleSelectWatchListGenre()
    {
        String nameSelected = filterNameWatchList.getValue();
        String genreSelected = filterGenreWatchList.getValue();
        double reviewSelected = Math.floor(reviewSelector.getValue());
        List<Movie> watchList = service.getWatchListForUser(userCurent);
        if(genreSelected!= null && !genreSelected.equals(""))
        {
            watchList = watchList.stream().filter(x->x.getGenre().toString().equals(genreSelected)).toList();
        }
        if(nameSelected!=null && !nameSelected.equals(""))
        {
            watchList = watchList.stream().filter(x->x.getName().equals(nameSelected)).toList();
        }
        if(reviewSelected!=0d)
        {
            watchList = watchList.stream().filter(x->Math.floor(x.getReview()) == reviewSelected).toList();
        }
        modelMovieWatchList = FXCollections.observableArrayList(watchList);
        if(nameSelected==null || nameSelected.equals(""))
        {
            refreshFilters(filterNameWatchList, 0);
        }
        tableWatchList.setItems(modelMovieWatchList);
    }
    private void refreshFilters(ChoiceBox<String> toUpdate, int val)
    {
        List<Movie> watchList = modelMovieWatchList;
        ObservableList<String> values = FXCollections.observableArrayList();
        if(val == 0)
        {
            for (Movie movie : watchList) {
                values.add(movie.getName());
            }
        }
        else
        {
            for (Movie movie : watchList) {
                if(!values.contains(movie.getGenre().toString()))
                {
                    values.add(movie.getGenre().toString());
                }
            }
        }
        values.add("");
        toUpdate.setItems(values);
    }
    @FXML
    private void handleFilterAfterReview() {
        String nameSelected = filterNameWatchList.getValue();
        String genreSelected = filterGenreWatchList.getValue();
        double reviewSelected = Math.floor(reviewSelector.getValue());
        List<Movie> watchList = service.getWatchListForUser(userCurent);
        if(reviewSelected != 0d)
        {
            watchList = watchList.stream().filter(x->Math.floor(x.getReview())==reviewSelected).toList();
        }
        if(nameSelected!=null && !nameSelected.equals(""))
        {
            watchList = watchList.stream().filter(x->x.getName().equals(nameSelected)).toList();
        }
        if(genreSelected!=null && !genreSelected.equals(""))
        {
            watchList = watchList.stream().filter(x->x.getGenre().toString().equals(genreSelected)).toList();
        }
        modelMovieWatchList = FXCollections.observableArrayList(watchList);
        if(nameSelected==null || nameSelected.equals(""))
        {
            refreshFilters(filterNameWatchList, 0);
        }
        if(genreSelected==null || genreSelected.equals(""))
        {
            refreshFilters(filterGenreWatchList, 1);
        }
        tableWatchList.setItems(modelMovieWatchList);
    }
    @FXML
    private void handleUpdateAccount() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/update-information.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 489, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        UpdateInformationController appController = fxmlLoader.getController();
        appController.setMainPageController(this);
        appController.setUserCurent(userCurent);
        appController.setService(this.service);
    }
}