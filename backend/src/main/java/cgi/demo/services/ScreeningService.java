package cgi.demo.services;

import cgi.demo.DTO.*;
import cgi.demo.entities.Screening;
import cgi.demo.entities.Ticket;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
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



    public PurchaseConfirmationDTO buyTicket(PurchaseDTO dto) {

        try {
            //Validate, if the selected seats are free
            for (int[] seat : dto.getSeats()) {
                Optional<Ticket> ticket = ticketRepository.findTicketByScreeningAndSeats(
                        dto.getScreeningID(),
                        seat[0],
                        seat[1]);
                if (ticket.isPresent()) {
                    return null;
                }
            }
            //Get the screening info
            Optional<Screening> screening = screeningRepository.findScreeningById(dto.getScreeningID());
            Date purchaseDate = new Date();
            //If seats are free then purchase the tickets.
            // seat is defined as row,col
            List<SeatsProjectionIMPL> soldTickets = new ArrayList<>();
            for (int[] seat : dto.getSeats()) {
                Ticket purchased = new Ticket();
                purchased.setScreening(screening.get());
                purchased.setPurchaseDate(purchaseDate);
                purchased.setRow(seat[0]);
                purchased.setSeat(seat[1]);
                ticketRepository.save(purchased);
                soldTickets.add(mapper.mapToSeats(purchased));
            }
            ticketRepository.flush();

            return new PurchaseConfirmationDTO().builder()
                    .screening(screening.get())
                    .seats(soldTickets).build();
        }catch (Exception e){
            return null;
        }
    }


}
