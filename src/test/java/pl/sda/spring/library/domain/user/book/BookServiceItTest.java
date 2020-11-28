package pl.sda.spring.library.domain.user.book;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.spring.library.domain.book.Book;
import pl.sda.spring.library.domain.book.BookService;
import pl.sda.spring.library.external.book.BookEntity;
import pl.sda.spring.library.external.book.JpaBookRepository;
import pl.sda.spring.library.model.SearchParams;
import pl.sda.spring.library.validator.AlreadyExistException;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class BookServiceItTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private JpaBookRepository jpaBookRepository;

    @BeforeEach
    public void cleanDb() {
        jpaBookRepository.deleteAll();
    }

    @Test
    public void shouldNotPersistBookWhenIsbnAlreadyExists() {
        //given
        Book book = new Book(null, "Lalka", "Bolesław Prus", "12354668", LocalDate.now(), "PWN");
        BookEntity bookEntity = new BookEntity(null, "Potop", "Henryk Sienkiewicz", "12354668", LocalDate.now(), "PWN", null);
        jpaBookRepository.save(bookEntity);
        //when
        AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
                () -> bookService.create(book));
        //than
        Assertions.assertEquals("Book with isbn number 12354668 already exists", ex.getMessage());
    }


    @Test
    public void shouldPersistCarInDb() {
        //given
        Book book = new Book(null, "Lalka", "Bolesław Prus", "12354668", LocalDate.now(), "PWN");
        //when
        bookService.create(book);
        //then
        List<BookEntity> books = jpaBookRepository.findAll();
        Assertions.assertEquals(1, books.size());

        BookEntity expectedBook = books.get(0);
        Assertions.assertEquals("Lalka", expectedBook.getTitle());
        Assertions.assertEquals("Bolesław Prus", expectedBook.getAuthor());
        Assertions.assertEquals("12354668", expectedBook.getIsbn());
        Assertions.assertEquals(LocalDate.now(), expectedBook.getYearOfPublishment());
        Assertions.assertEquals("PWN", expectedBook.getPublishingHouse());
    }

    @Test
    public void shouldReturnEmptyListWhenNoBooksExistInDb() {
        //when
        List<Book> book = bookService.searchByParams(new SearchParams());
        //then
        Assertions.assertTrue(book.isEmpty());
    }

    @Test
    public void shouldReturnByAuthor() {
        //given
        BookEntity bookEntity = new BookEntity(null, "Potop", "Henryk Sienkiewicz", "12354668", LocalDate.now(), "PWN", null);
        BookEntity bookEntity1 = new BookEntity(null, "Lalka", "Bolesław Prus", "123458", LocalDate.now(), "PWN", null);
        BookEntity bookEntity2 = new BookEntity(null, "Ogniem i mieczem", "Henryk Sienkiewicz", "985355", LocalDate.now(), "PWN", null);
        jpaBookRepository.saveAll(Lists.list(bookEntity, bookEntity1, bookEntity2));
        SearchParams searchParams = new SearchParams();
        searchParams.setAuthor("Henryk Sienkiewicz");
        //when
        List<Book> bookList = bookService.searchByParams(searchParams);
        //than
        Assertions.assertEquals(2, bookList.size());
        Assertions.assertTrue(bookList.stream().allMatch(book -> book.getAuthor().equals("Henryk Sienkiewicz")));
    }

    @Test
    public void shouldReturnByTitle() {
        //given
        BookEntity bookEntity = new BookEntity(null, "Potop", "Henryk Sienkiewicz", "12354668", LocalDate.now(), "PWN", null);
        BookEntity bookEntity1 = new BookEntity(null, "Lalka", "Bolesław Prus", "123458", LocalDate.now(), "PWN", null);
        BookEntity bookEntity2 = new BookEntity(null, "Ogniem i mieczem", "Henryk Sienkiewicz", "985355", LocalDate.now(), "PWN", null);
        jpaBookRepository.saveAll(Lists.list(bookEntity, bookEntity1, bookEntity2));
        SearchParams searchParams = new SearchParams();
        searchParams.setTitle("Potop");
        //when
        List<Book> bookList = bookService.searchByParams(searchParams);
        //than
        Assertions.assertEquals(1, bookList.size());
        Assertions.assertTrue(bookList.stream().allMatch(book -> book.getTitle().equals("Potop")));
    }

    @Test
    public void shouldReturnByIsbn() {
        //given
        BookEntity bookEntity = new BookEntity(null, "Potop", "Henryk Sienkiewicz", "12354668", LocalDate.now(), "PWN", null);
        BookEntity bookEntity1 = new BookEntity(null, "Lalka", "Bolesław Prus", "123458", LocalDate.now(), "Nowa Era", null);
        BookEntity bookEntity2 = new BookEntity(null, "Ogniem i mieczem", "Henryk Sienkiewicz", "985355", LocalDate.now(), "PWN", null);
        jpaBookRepository.saveAll(Lists.list(bookEntity, bookEntity1, bookEntity2));
        SearchParams searchParams = new SearchParams();
        searchParams.setIsbn("123458");
        //when
        List<Book> bookList = bookService.searchByParams(searchParams);
        //than
        Assertions.assertEquals(1, bookList.size());
        Assertions.assertTrue(bookList.stream().allMatch(book -> book.getIsbn().equals("123458")));
    }

}
