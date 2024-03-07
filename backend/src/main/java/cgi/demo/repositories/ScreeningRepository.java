package cgi.demo.repositories;

import cgi.demo.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    //@Query(value = "SELECT * FROM screening JOIN movie ON screening.movie_id = movie.id",nativeQuery = true)
    //List<ScreeningWithMovieDTO> findAllScreeningsWithMovie();

    List<Screening> findAll();

    @Query(value = "SELECT ticket.seat FROM ticket WHERE ticket.screening_id = ?1", nativeQuery = true)
    List<Integer> findAllBookedSeatsAtScreening(Long id);

    Optional<Screening> findById(Long id);
}
