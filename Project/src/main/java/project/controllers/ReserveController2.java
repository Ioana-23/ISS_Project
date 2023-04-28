package project.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import project.domain.MovieScreening;
import project.domain.Properties;
import project.domain.Seat;
import project.domain.User;
import project.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReserveController2 {
    @FXML
    private Label hallNumber;
    @FXML
    private AnchorPane anchor;
    private Service service;
    private User userCurent;
    private MovieScreening movieScreening;
    private Properties properties;
    private List<List<Button>> configuration = new ArrayList<>();

    public ReserveController2() {}

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

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public void initView()
    {
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
            anchorPane.setLayoutX(20.0);
            anchorPane.setLayoutY(34.0);
            int finalI = i+1;
            List<Seat> seats = movieScreening.getMovieHall().getHallConfiguration().stream().filter(x->x.getLineNumber()== finalI).toList();
            for(int j = 0; j < seats.size(); j++)
            {
                Button button = new Button();
                char lineLetter = (char) ('A' + (seats.get(j).getLineNumber()-1));
                int finalJ = seats.get(j).getSeatnumber();
                button.setText(lineLetter + "/" + finalJ);
                button.setPrefHeight(35);
                button.setPrefWidth(35);
                button.setPrefSize(35, 35);
                button.setLayoutX(20.0+36*(seats.get(j).getSeatnumber()-1));
                button.setLayoutY(2.0 + 40 * (seats.get(j).getLineNumber()-1));
                button.setMnemonicParsing(false);
                button.setStyle("-fx-background-color: GREEN; -fx-border-color: BLACK;-fx-text-fill: WHITE; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
                int k = 0;
                while(k < list.size())
                {
                    if(list.get(k).getLineNumber()==seats.get(j).getLineNumber() && list.get(k).getSeatnumber()==seats.get(j).getSeatnumber())
                    {
                        button.setStyle("-fx-background-color: GRAY; -fx-border-color: BLACK;-fx-text-fill: WHITE; -fx-font-size: 9.5; -fx-background-size: 35 35; -fx-padding: 0");
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
}
