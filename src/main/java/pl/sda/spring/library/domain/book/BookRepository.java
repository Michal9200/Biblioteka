package pl.sda.spring.library.domain.book;

import pl.sda.spring.library.model.SearchParams;

import java.util.List;
import java.util.Optional;

public interface BookRepository {


    Optional<Book> findOne(Long id);
    boolean existsByIsbn(String isbn);
    List<Book> findAll();
    void create(Book book);
    void update(Book book);
    void delete(Long id);
    List<Book> findByParams(SearchParams searchParams);
}
