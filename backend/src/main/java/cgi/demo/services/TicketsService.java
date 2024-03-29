package cgi.demo.services;

import cgi.demo.DTO.PurchaseConfirmationDTO;
import cgi.demo.DTO.PurchaseDTO;
import cgi.demo.DTO.SeatsProjectionIMPL;
import cgi.demo.DTO.UserTicketsProjection;
import cgi.demo.entities.Screening;
import cgi.demo.entities.Ticket;
import cgi.demo.entities.User;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
import cgi.demo.repositories.UserRepository;
import cgi.demo.utils.TicketModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketsService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketModelMapper mapper;

    public List<UserTicketsProjection> getAllUserTickets(String username){
        User user = userRepository.findByUsername(username);
        return ticketRepository.findTicketsByUser(user);
    }


    public PurchaseConfirmationDTO buyTicket(PurchaseDTO dto, String username) {

        try {
            //fetch the user details from the database
            User user = userRepository.findByUsername(username);
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
                purchased.setUser(user);
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
