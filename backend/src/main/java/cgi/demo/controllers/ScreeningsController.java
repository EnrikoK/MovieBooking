package cgi.demo.controllers;


import cgi.demo.DTO.PurchaseConfirmationDTO;
import cgi.demo.DTO.PurchaseDTO;
import cgi.demo.DTO.ScreeningInfoDTO;
import cgi.demo.entities.Screening;
import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.repositories.TicketRepository;
import cgi.demo.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningsController {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping("/all-screenings")
    public ResponseEntity<?> getAllMovies(){
        return ResponseEntity.ok(screeningService.getAllScreenings());
    }


    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingScreenings(){
        List<Screening> upcoming = screeningService.getUpcomingScreenings();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("upcoming",upcoming));
    }

    @GetMapping("/screening/{id}")
    public ResponseEntity<?> getAllReservedSeatsAtScreening(@PathVariable Long id){
        if(screeningRepository.findById(id).isPresent()){
            ScreeningInfoDTO screeningInfoDTO = screeningService.getScreeningInfo(id);
            return ResponseEntity.status(HttpStatus.OK).body(screeningInfoDTO);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","No screening with given id: "+id));
        }
    }

    @PostMapping("/purchase-ticket")
    public ResponseEntity<?> purchaseTicket(@RequestBody PurchaseDTO dto){
        PurchaseConfirmationDTO transaction = screeningService.buyTicket(dto);
        if(transaction == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","These seats are already taken"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(transaction);

    }

}
