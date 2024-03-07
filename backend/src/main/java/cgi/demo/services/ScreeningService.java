package cgi.demo.services;

import cgi.demo.entities.Screening;
import cgi.demo.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ScreeningService {

    @Autowired
    ScreeningRepository screeningRepository;

    public List<Screening> getAllScreenings(){
        return screeningRepository.findAll();
    }


    public List<Integer> getAllReservedSeats(Long id) {
        return screeningRepository.findAllBookedSeatsAtScreening(id);
    }
}
