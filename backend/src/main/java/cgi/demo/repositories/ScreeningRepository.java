package cgi.demo.repositories;

import cgi.demo.DTO.SeatsProjection;
import cgi.demo.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    List<Screening> findAll();

    @Query(value = "SELECT ticket.seat, ticket.row FROM ticket WHERE ticket.screening_id = ?1", nativeQuery = true)
    List<SeatsProjection> findAllBookedSeatsAtScreening(Long id);

    //@Query(value = "SELECT * FROM screening WHERE screening.id = ?1", nativeQuery = true)
    Optional<Screening> findScreeningById(Long id);

    @Query(value = "SELECT * FROM screening  WHERE screening.date > ?1",nativeQuery = true)
    List<Screening>findUpcomingScreenings(Date date);


    @Query(value = "SELECT s.* FROM screening s " +
                    "JOIN movie m ON s.movie_id = m.id " +
                    "JOIN movie_genre mg  ON mg.movie_id = m.id " +
                    "WHERE mg.genre_id = ?1 AND s.date > CURRENT_TIMESTAMP;",nativeQuery = true)
    List<Screening> findUpcomingScreeningByGenreId(Long id);

}
