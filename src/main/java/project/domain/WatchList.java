package project.domain;

public class WatchList {
    private int id;
    private User user;
    private Movie movie;

    public WatchList(int id, User user, Movie movie) {
        this.id = id;
        this.user = user;
        this.movie = movie;
    }

    public WatchList() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }
}
