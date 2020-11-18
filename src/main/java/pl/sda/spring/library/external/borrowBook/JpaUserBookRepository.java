package pl.sda.spring.library.external.borrowBook;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.spring.library.external.book.BookEntity;

import java.util.List;


public interface JpaUserBookRepository extends JpaRepository<UserBookEntity, Long> {

    List<UserBookEntity> findAllByUserEntity_Username (String name);

    void deleteById (Long id);
}
