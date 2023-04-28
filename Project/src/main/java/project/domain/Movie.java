package project.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Movie extends Entity<Integer>{
    private String name;
    private Genre genre;
    private double review;
    private LocalDate date;

    public Movie(String name, Genre genre, double review, LocalDate date) {
        this.name = name;
        this.genre = genre;
        this.review = review;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
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
