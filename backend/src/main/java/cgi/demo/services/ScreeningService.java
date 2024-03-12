package cgi.demo.services;

import cgi.demo.DTO.*;
import cgi.demo.entities.Movie;
import cgi.demo.entities.Screening;
import cgi.demo.entities.Ticket;
import cgi.demo.entities.User;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
import cgi.demo.repositories.UserRepository;
import cgi.demo.utils.TicketModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


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

    public List<Screening> getAllScreenings(){
        return screeningRepository.findAll();
    }


    public List<SeatsProjection> getAllReservedSeats(Long id) {
        return screeningRepository.findAllBookedSeatsAtScreening(id);
    }

    public List<Screening> getUpcomingScreenings() {
        Date date = new Date();
        return screeningRepository.findUpcomingScreenings(date);
    }

    public ScreeningInfoDTO getScreeningInfo(Long id){
        ScreeningInfoDTO returnInfo = new ScreeningInfoDTO();
        returnInfo.setScreening(screeningRepository.getById(id));
        returnInfo.setTakenSeats(screeningRepository.findAllBookedSeatsAtScreening(id));
        return returnInfo;
    }






}
