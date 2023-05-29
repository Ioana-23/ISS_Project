package project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.domain.Movie;

public class MovieInformationController {
    @FXML
    private Label information;
    @FXML
    private ImageView posterImage;
    private Movie movie;
    public void setMovie(Movie movie)
    {
        this.movie = movie;
        initialize();
    }
    private void initialize()
    {
        String imagePath = movie.getImagePath();
        if(movie.getImagePath() == null)
        {
            imagePath = "D:\\Laboratoare\\ANUL 2\\Semestrul 2\\IngineriaSistemelorSoft\\Tema1\\Project\\src\\main\\resources\\images\\NotFound.jpg";
        }
        posterImage.setImage(new Image(imagePath));
        String informatii = movie.getInformatii();
        if(movie.getInformatii() == null)
        {
            informatii = "Ne pare rau! Nu exista informatii in acest moment pentru filmul selectat";
        }
        information.setText(informatii);
    }
}
