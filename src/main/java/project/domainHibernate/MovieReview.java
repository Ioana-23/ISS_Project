package project.domainHibernate;

public class MovieReview{
    private int id;
    private Movie movie;
    private User user;
    private double star;

    public MovieReview() {
    }

    public MovieReview(int id, Movie movie, User user, double star) {
        this.id = id;
        this.movie = movie;
        this.user = user;
        this.star = star;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStar(double star) {
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
