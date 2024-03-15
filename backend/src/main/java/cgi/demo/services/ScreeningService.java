package cgi.demo.services;

import cgi.demo.DTO.*;
import cgi.demo.entities.Screening;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
import cgi.demo.repositories.UserRepository;
import cgi.demo.utils.MovieDetailsAPIRequestUtil;
import cgi.demo.utils.TicketModelMapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.*;

@Service
public class ScreeningService {

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieDetailsAPIRequestUtil util;

    public List<Screening> getAllScreenings(){
        return screeningRepository.findAll();
    }


    public List<SeatsProjection> getAllReservedSeats(Long id) {
        return screeningRepository.findAllBookedSeatsAtScreening(id);
    }

    public List<ScreeningDTO> getUpcomingScreenings() throws JSONException, IOException {
        Date date = new Date();
        List<Screening> s = screeningRepository.findUpcomingScreenings(date);
        List<ScreeningDTO> response = new ArrayList<>();
        for(Screening a:s){
            ScreeningDTO dto = new ScreeningDTO();
            dto.setScreening(a);
            try {
                String[] ratingAndImg = util.getMovieRatingsAndImage(a.getMovie().getTitle());
                dto.setScore(ratingAndImg[0]);
                dto.setPosterUrl(ratingAndImg[1]);
            } catch (Exception e){
                dto.setPosterUrl(null);
                dto.setScore(null);
            }
            response.add(dto);
        }
        return response;
    }

    public ScreeningInfoDTO getScreeningInfo(Long id){
        ScreeningInfoDTO returnInfo = new ScreeningInfoDTO();
        returnInfo.setScreening(screeningRepository.getById(id));
        returnInfo.setTakenSeats(screeningRepository.findAllBookedSeatsAtScreening(id));
        return returnInfo;
    }






}
