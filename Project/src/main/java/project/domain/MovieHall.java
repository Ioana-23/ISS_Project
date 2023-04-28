package project.domain;

import java.util.List;

public class MovieHall extends Entity<Integer> {
    private int number;
    private List<Seat> hallConfiguration;

    public MovieHall(int number, List<Seat> hallConfiguration)
    {
        this.number = number;
        this.hallConfiguration = hallConfiguration;
    }
    public int getNumber() {return number;}
    public List<Seat> getHallConfiguration() {
        return hallConfiguration;
    }
    public void setNumber(int number) {this.number = number;}
    public void setHallConfiguration(List<Seat> hallConfiguration) {this.hallConfiguration = hallConfiguration;}
}
