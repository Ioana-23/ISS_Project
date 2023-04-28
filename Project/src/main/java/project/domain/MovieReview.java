package project.domain;

public class MovieReview extends Entity<Pair<Integer, Integer>>{
    private Movie movie;
    private User user;
    private double star;

    public MovieReview(Movie movie, User user, double star) {
        this.movie = movie;
        this.user = user;
        this.star = star;
    }

    public Movie getMovie() {
        return movie;
    }

    public User getUser() {
        return user;
    }

    public double getStar() {
        return star;
    }
}
