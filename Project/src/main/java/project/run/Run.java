package project.run;

import project.domain.Genre;
import project.domain.Seat;
import project.domain.Status;
import project.domain.Tip;
import project.repo.*;
import project.service.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Run {
        public static void main(String[] args) {
        try
        {
            DatabaseRepositoryUser repositoryUser = new DatabaseRepositoryUser();
            DatabaseRepositoryMovie repositoryMovie = new DatabaseRepositoryMovie();
            DatabaseRepositoryMovieHall repositoryMovieHall = new DatabaseRepositoryMovieHall();
            DatabaseRepositoryReservation repositoryReservation = new DatabaseRepositoryReservation();
            DatabaseRepositorySeat repositorySeat = new DatabaseRepositorySeat();
            DatabaseRepositoryMovieHallSeats repositoryMovieHallSeats = new DatabaseRepositoryMovieHallSeats();
            DatabaseRepositoryReservationSeats repositoryReservationSeats = new DatabaseRepositoryReservationSeats();
            DatabaseRepositoryMovieReview repositoryMovieReview = new DatabaseRepositoryMovieReview();
            DatabaseRepositoryMovieScreening repositoryMovieScreening = new DatabaseRepositoryMovieScreening();
            Service service = new Service(repositoryUser, repositoryMovie, repositoryMovieHall, repositoryReservation, repositorySeat, repositoryMovieHallSeats, repositoryReservationSeats, repositoryMovieReview, repositoryMovieScreening);
            service.addUser("ardeleancatalin03@gmail.com", "abra12#", "Catalin Ardelean", new ArrayList<>(Arrays.asList(0,7,3,7,5,2,5,3,6,0)));
            service.addMovie("Suzume", Genre.Animatie, 7.8, LocalDate.parse("2022-04-14"));
            service.addMovie("Snowpiercer", Genre.Actiune, 7.1, LocalDate.parse("2014-07-11"));
            service.addMovie("A Marriage Story", Genre.Drama, 7.9, LocalDate.parse("2019-12-06"));
            service.addMovieHall(2, new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(2,4))));
            service.addMovieScreening("Snowpiercer", Genre.Actiune, 7.1, LocalDate.parse("2014-07-11"), LocalDate.parse("2023-06-23"), LocalTime.parse("19:50"), Tip.DD,4, new ArrayList<>(Arrays.asList(new Seat(1, 1), new Seat(1, 2))));
            service.addMovieScreening("A Marriage Story", Genre.Drama, 7.9, LocalDate.parse("2019-12-06"), LocalDate.parse("2023-04-28"), LocalTime.parse("20:30:00"), Tip.DDD,2, new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(2,4))));
            service.addMovieScreening("Suzume", Genre.Animatie, 7.8, LocalDate.parse("2022-04-14"), LocalDate.parse("2023-04-28"), LocalTime.parse("20:30:00"), Tip.DDDDX,2, new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(1,3))));
            service.addReservation("A Marriage Story", Genre.Drama, 7.9, LocalDate.parse("2019-12-06"), LocalDate.parse("2023-04-28"), LocalTime.parse("20:30:00"), 2, new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(2,4))), Tip.DDD, Status.Inregistrata, LocalDateTime.parse("2023-04-08T21:00:00"),new ArrayList<>(Arrays.asList(new Seat(1,1))),"ardeleancatalin03@gmail.com", "abra12#", "Catalin Ardelean", new ArrayList<>(Arrays.asList(0,7,3,7,5,2,5,3,6,0)));
            service.addReservation("Suzume", Genre.Animatie, 7.8, LocalDate.parse("2022-04-14"), LocalDate.parse("2023-04-28"), LocalTime.parse("20:30:00"), 3, new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(2,4))), Tip.DDD, Status.Inregistrata, LocalDateTime.parse("2023-04-08T21:00:00"),new ArrayList<>(Arrays.asList(new Seat(1,1))),"ardeleancatalin03@gmail.com", "abra12#", "Catalin Ardelean", new ArrayList<>(Arrays.asList(0,7,3,7,5,2,5,3,6,0)));
            service.addReservation("Suzume", Genre.Animatie, 7.8, LocalDate.parse("2022-04-14"), LocalDate.parse("2023-04-28"), LocalTime.parse("20:30:00"), 3, new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(2,4))), Tip.DDD, Status.Inregistrata, LocalDateTime.parse("2023-04-08T21:00:00"),new ArrayList<>(Arrays.asList(new Seat(1,1), new Seat(2, 4))),"baciuioana23@gmail.com", "abra12#", "Ioana Baciu", new ArrayList<>(Arrays.asList(0,7,3,7,5,2,5,3,6,9)));
            service.addSeat(2,4);
            service.addReservation("Snowpiercer", Genre.Actiune, 7.1, LocalDate.parse("2014-07-11"), LocalDate.parse("2023-06-23"), LocalTime.parse("19:50"), 4, new ArrayList<>(Arrays.asList(new Seat(1, 1), new Seat(1, 2))), Tip.DDDDX, Status.Inregistrata, LocalDateTime.parse("2023-06-23T21:15:00"), new ArrayList<>(Arrays.asList(new Seat(1,1))),"a", "a", "a", new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0)));
            service.addMovieReview("Suzume", Genre.Animatie, 7.8, LocalDate.parse("2022-04-14"), "baciuioana23@gmail.com", "abra12#", "Ioana Baciu", new ArrayList<>(Arrays.asList(0,7,3,7,5,2,5,3,6,9)), 8);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
