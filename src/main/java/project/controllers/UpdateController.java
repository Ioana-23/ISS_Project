package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import project.domain.MovieScreening;
import project.domain.Reservation;
import project.service.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UpdateController {
    @FXML
    private Button reserve;
    @FXML
    private ChoiceBox<LocalTime> timePicker;
    @FXML
    private DatePicker datePicker;
    private Service service;
    private Reservation rezervareaCurenta;
    private LocalDate date;
    private LocalTime time;
    private MainPageController mainPageController;

    public UpdateController() {}

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    public void setRezervareaCurenta(Reservation rezervareaCurenta) {this.rezervareaCurenta = rezervareaCurenta;}

    public void setService(Service service)
    {
        this.service = service;
        initView();
    }
    private void initView()
    {

    }
    @FXML
    private void handleUpdateHours()
    {
        date = datePicker.getValue();
        if(date.isAfter(LocalDate.now()) || date.equals(LocalDate.now()))
        {
            reserve.setDisable(false);
            List<MovieScreening> movieScreeningList = service.findMovieScreenFromDay(date).stream().filter(x->x.getMovieName().equals(rezervareaCurenta.getMovieName())).toList();
            ObservableList<LocalTime> times = FXCollections.observableArrayList();
            for (MovieScreening movieScreening : movieScreeningList)
            {
                times.add(movieScreening.getTime());
            }
            if(times.size()==0)
            {
                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setContentText("Ne pare rau, dar nu sunt disponibile vizionari pentru filmul " + rezervareaCurenta.getMovieName() + " in data de " + date);
                message.showAndWait();
            }
            timePicker.setItems(times);
        }
        else
        {
            reserve.setDisable(true);
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setContentText("Nu puteti alege o zi inainte de data curenta!");
            message.showAndWait();
        }
    }
    @FXML
    private void handleUpdateTime()
    {
        time = LocalTime.parse(timePicker.getValue().toString());
        if(date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now()))
        {
            reserve.setDisable(true);
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setContentText("Nu puteti alege o ora inainte de ora curenta!");
            message.showAndWait();
        }
        else
        {
            reserve.setDisable(false);
        }
    }
    @FXML
    private void handleUpdateReservation(ActionEvent actionEvent) {
        service.updateReservation(rezervareaCurenta, date, time);
        mainPageController.refreshData();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
