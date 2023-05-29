package project.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Movie extends Entity<Integer>{
    private String name;
    private String genre;
    private double review;
    private LocalDate date;
    private String informatii;
    private String imagePath;
    public Movie(String name, String genre, double review, LocalDate date) {
        this.name = name;
        this.genre = genre;
        this.review = review;
        this.date = date;
    }

    public Movie() {

    }

    public void setInformatii(String informatii){this.informatii = informatii;}
    public void setImagePath(String imagePath){this.imagePath = imagePath;}
    public String getInformatii(){return this.informatii;}
    public String getImagePath(){return this.imagePath;}
    public LocalDate getDate() {
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
    public void setDate(LocalDate date) {
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
        return Objects.equals(name, movie.name) && Objects.equals(date, movie.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
