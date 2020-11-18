package pl.sda.spring.library.external.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaBookRepository extends JpaRepository<BookEntity,Long>, CustomDatabaseBookRepository {

    Optional<BookEntity> findByIsbn(String isbn);
}
