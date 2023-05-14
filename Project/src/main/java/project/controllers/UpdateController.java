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
import project.domain.User;
import project.service.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UpdateController {
    @FXML
    private Button reserve;
    @FXML
    private ChoiceBox timePicker;
    @FXML
    private DatePicker datePicker;
    private Service service;
    private User userCurent;
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
    public void setUserCurent(User userCurent) {this.userCurent = userCurent;}
    private void initView()
    {

    }
    @FXML
    private void handleUpdateHours(ActionEvent actionEvent)
    {
        date = datePicker.getValue();
        if(date.isAfter(LocalDate.now()) || date.equals(LocalDate.now()))
        {
            reserve.setDisable(false);
            List<MovieScreening> movieScreeningList = service.findMovieScreenFromDay(date).stream().filter(x->x.getMovieName().equals(rezervareaCurenta.getMovieName())).toList();
            ObservableList<LocalTime> times = FXCollections.observableArrayList();
            for(int i = 0; i < movieScreeningList.size(); i++)
            {
                times.add(movieScreeningList.get(i).getTime());
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
    private void handleUpdateTime(ActionEvent actionEvent)
    {
        time = LocalTime.parse(timePicker.getValue().toString());
        if(time.isBefore(LocalTime.now()))
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
