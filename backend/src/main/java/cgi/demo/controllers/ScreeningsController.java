package cgi.demo.controllers;


import cgi.demo.repositories.ScreeningRepository;
import cgi.demo.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/screening/{id}")
    public ResponseEntity<?> getAllReservedSeatsAtScreening(@PathVariable Long id){
        if(screeningRepository.findById(id).isPresent()){
            List<Integer> takenSeats = screeningService.getAllReservedSeats(id);
            return ResponseEntity.status(200).body(Map.of("taken_seats",takenSeats));
        }else{
            return ResponseEntity.status(404).body(Map.of("message","No screening with given id: "+id));
        }
    }
}
