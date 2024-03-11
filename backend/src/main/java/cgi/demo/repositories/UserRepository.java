package cgi.demo.repositories;

import cgi.demo.DTO.UserFavoriteGenresProjection;
import cgi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query(value = "SELECT g.*, COUNT(*) as count " +
            "FROM ticket t " +
            "JOIN screening s ON t.screening_id = s.id " +
            "JOIN movie m ON s.movie_id = m.id " +
            "JOIN movie_genre mg ON mg.movie_id = m.id " +
            "JOIN genres g ON mg.genre_id = g.id " +
            "WHERE t.user_id = ?1 " +
            "GROUP BY g.genre, g.id ORDER BY count DESC;", nativeQuery = true)
    List<UserFavoriteGenresProjection> findUserFavoriteGenres(Long id);

}
