package cgi.demo.services;

import cgi.demo.DTO.UserFavoriteGenresProjection;
import cgi.demo.entities.Screening;
import cgi.demo.entities.User;
import cgi.demo.repositories.MovieRepository;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
import cgi.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecomendationsService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    UserRepository userRepository;

    public List<Screening> getRecomendations(String user) {

        User userInfo = userRepository.findByUsername(user);
        List<UserFavoriteGenresProjection> favorites = userRepository.findUserFavoriteGenres(userInfo.getId());
        //favorites.sort(Comparator.comparing(UserFavoriteGenresProjection::getCount));

        List<Screening> allScreenings = new ArrayList<>();
        for (UserFavoriteGenresProjection projection:favorites) {
            System.out.println(projection);
            List<Screening> genreScreenings = screeningRepository.findUpcomingScreeningByGenreId(projection.getId());
            allScreenings.addAll(genreScreenings);

        }

        return allScreenings;
    }
}
