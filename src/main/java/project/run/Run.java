package project.run;

import project.domain.MovieHall;
import project.domain.Seat;
import project.repo.*;
import project.service.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Run {
        public static void main(String[] args) {
        try
        {
            DatabaseRepositoryUserHibernate repositoryUser = new DatabaseRepositoryUserHibernate();
            DatabaseRepositoryMovieHibernate repositoryMovie = new DatabaseRepositoryMovieHibernate();
            DatabaseRepositoryMovieHallHibernate repositoryMovieHall = new DatabaseRepositoryMovieHallHibernate();
            DatabaseRepositoryReservationHibernate repositoryReservation = new DatabaseRepositoryReservationHibernate();
            DatabaseRepositorySeatHibernate repositorySeat = new DatabaseRepositorySeatHibernate();
            DatabaseRepositoryMovieHallSeatsHibernate repositoryMovieHallSeats = new DatabaseRepositoryMovieHallSeatsHibernate();
            DatabaseRepositoryMovieReviewHibernate repositoryMovieReview = new DatabaseRepositoryMovieReviewHibernate();
            DatabaseRepositoryMovieScreeningHibernate repositoryMovieScreening = new DatabaseRepositoryMovieScreeningHibernate();
            Service service = new Service(repositoryUser, repositoryMovie, repositoryMovieHall, repositoryReservation, repositorySeat, repositoryMovieHallSeats, repositoryMovieReview, repositoryMovieScreening);
            /*service.addUser("ardeleancatalin03@gmail.com", "abra12#", "Catalin Ardelean", new ArrayList<>(Arrays.asList(0,7,3,7,5,2,5,3,6,0)));
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
        */
            Random random = new Random();
            for(int i = 1; i <= 10; i++)
            {
                int nrLinii = random.nextInt(10);
                while(nrLinii<6)
                {
                    nrLinii = random.nextInt(10);
                }
                for(int j = 1; j <= nrLinii; j++)
                {
                    int nrColoane = random.nextInt(15);
                    while(nrColoane<6)
                    {
                        nrColoane = random.nextInt(15);
                    }
                    for(int k = 1; k <= nrColoane; k++)
                    {
                        service.addMovieHall(new MovieHall(i, new ArrayList<>(List.of(new Seat(j, k)))));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
