package cgi.demo.services;

import cgi.demo.DTO.ScreeningDTO;
import cgi.demo.DTO.UserFavoriteGenresProjection;
import cgi.demo.entities.Screening;
import cgi.demo.entities.User;
import cgi.demo.repositories.MovieRepository;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
import cgi.demo.repositories.UserRepository;
import cgi.demo.utils.MovieDetailsAPIRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    MovieDetailsAPIRequestUtil util;

    public List<ScreeningDTO> getRecomendations(String user) {

        User userInfo = userRepository.findByUsername(user);
        //Ordered list of genres by genre ticket count
        List<UserFavoriteGenresProjection> favorites = userRepository.findUserFavoriteGenres(userInfo.getId());

        HashSet<Screening> addedScreenings = new HashSet<>();
        List<ScreeningDTO> allScreenings = new ArrayList<>();
        for (UserFavoriteGenresProjection projection:favorites) {

            List<Screening> genreScreenings = screeningRepository.findUpcomingScreeningByGenreId(projection.getId());
            for (Screening s: genreScreenings) {
                if(addedScreenings.contains(s)) continue;
                else addedScreenings.add(s);
                ScreeningDTO dto = new ScreeningDTO();
                dto.setScreening(s);
                try {
                    String[] ratingAndImg = util.getMovieRatingsAndImage(s.getMovie().getTitle());
                    dto.setScore(ratingAndImg[0]);
                    dto.setPosterUrl(ratingAndImg[1]);
                } catch (Exception e){
                    dto.setPosterUrl(null);
                    dto.setScore(null);
                }
                allScreenings.add(dto);
            }

        }
        return allScreenings;
    }
}
