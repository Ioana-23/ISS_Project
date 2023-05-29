package project.domainHibernate;

import java.time.LocalDate;
import java.util.Objects;

public class Movie{
    private int id;
    private String name;
    private String genre;
    private double review;
    private String date;
    private String informatii;
    private String imagePath;

    public Movie(int id, String name, String genre, double review, String date, String informatii, String imagePath) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.review = review;
        this.date = date;
        this.informatii = informatii;
        this.imagePath = imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Movie() {

    }
    public void setInformatii(String informatii){this.informatii = informatii;}
    public void setImagePath(String imagePath){this.imagePath = imagePath;}
    public String getInformatii(){return this.informatii;}
    public String getImagePath(){return this.imagePath;}
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setReview(double review) {
        this.review = review;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getGenre() {
        return genre;
    }
    public double getReview() {
        return review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
