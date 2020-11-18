package pl.sda.spring.library.domain.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.spring.library.model.SearchParams;
import pl.sda.spring.library.validator.AlreadyExistException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    public Optional<Book> getBookById(Long id){ return  bookRepository.findOne(id);}
    public List<Book> getAll(){ return bookRepository.findAll(); }
    public void create(Book book){
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new AlreadyExistException(String.format("Book with isbn number %s already exists", book.getIsbn()));
        }
        bookRepository.create(book);
    }
    public void update(Book book){
        bookRepository.update(book);
    }
    public void delete(Long id){
        bookRepository.delete(id);
    }
    public List<Book> searchByParams(SearchParams searchParams) {
        return bookRepository.findByParams(searchParams);
    }
}
