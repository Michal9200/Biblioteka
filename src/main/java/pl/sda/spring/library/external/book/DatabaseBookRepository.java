package pl.sda.spring.library.external.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.spring.library.domain.book.Book;
import pl.sda.spring.library.domain.book.BookRepository;
import pl.sda.spring.library.model.SearchParams;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DatabaseBookRepository implements BookRepository {

    private JpaBookRepository jpaBookRepository;


    @Override
    public Optional<Book> findOne(Long id) {
        return jpaBookRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return jpaBookRepository.findByIsbn(isbn).isPresent();
    }

    @Override
    public List<Book> findAll() {
        return jpaBookRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void create(Book book) {
        jpaBookRepository.save(toEntity(book));
    }

    @Override
    public void update(Book book) {
        if (!jpaBookRepository.existsById(book.getId())) {
            throw new IllegalStateException("Updated object not exist");
        }
        jpaBookRepository.save(toEntity(book));
    }

    @Override
    public void delete(Long id) {
        jpaBookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByParams(SearchParams searchParams) {
        return jpaBookRepository.findBasedOnSearchParams(searchParams)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Book toDomain(BookEntity entity){
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .isbn(entity.getIsbn())
                .yearOfPublishment(entity.getYearOfPublishment())
                .publishingHouse(entity.getPublishingHouse())
                .build();
    }

    private BookEntity toEntity(Book book){
        return BookEntity.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .yearOfPublishment(book.getYearOfPublishment())
                .publishingHouse(book.getPublishingHouse())
                .build();
    }
}
