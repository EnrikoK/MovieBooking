package cgi.demo.controllers;

import cgi.demo.DTO.PurchaseConfirmationDTO;
import cgi.demo.DTO.PurchaseDTO;
import cgi.demo.DTO.UserTicketsProjection;
import cgi.demo.entities.Ticket;
import cgi.demo.services.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController {

    @Autowired
    private TicketsService ticketsService;

    @GetMapping("/user-tickets")
    public ResponseEntity<?> getUserTickets(){
        List<UserTicketsProjection> tickets = ticketsService
                .getAllUserTickets(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName()
                );
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/purchase-ticket")
    public ResponseEntity<?> purchaseTicket(@RequestBody PurchaseDTO dto){

        PurchaseConfirmationDTO transaction = ticketsService.buyTicket(
                dto,
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if(transaction == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","These seats are already taken"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(transaction);

    }   
}
