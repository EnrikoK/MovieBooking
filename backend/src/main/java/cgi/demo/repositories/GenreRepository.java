package cgi.demo.repositories;

import cgi.demo.entities.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genres,Long> {
}
