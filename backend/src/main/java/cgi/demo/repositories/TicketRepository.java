package cgi.demo.repositories;

import cgi.demo.DTO.UserTicketsProjection;
import cgi.demo.entities.Ticket;
import cgi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query(value ="SELECT * FROM ticket WHERE ticket.screening_id = ?1 AND ticket.row = ?2 AND ticket.seat = ?3 " ,nativeQuery = true)
    Optional<Ticket> findTicketByScreeningAndSeats(Long id, int row, int seat);

    List<UserTicketsProjection> findTicketsByUser(User user);
}
