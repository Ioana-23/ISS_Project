package project.domain;

public class MovieReview extends Entity<Integer>{
    private final Movie movie;
    private final User user;
    private final double star;
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
