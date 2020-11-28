package pl.sda.spring.library.domain.userbook;

import lombok.*;
import pl.sda.spring.library.domain.book.Book;
import pl.sda.spring.library.domain.user.User;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    private Long id;

    private Book book;

    private User User;

    private LocalDate borrowDate;

    public UserBook(Book book, User user, LocalDate borrowDate) {
        this.book = book;
        User = user;
        this.borrowDate = borrowDate;
    }
}
