package pl.sda.spring.library.external.borrowBook;

import lombok.*;
import pl.sda.spring.library.external.book.BookEntity;
import pl.sda.spring.library.external.user.UserEntity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="UserBook")
public class UserBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    private LocalDate borrowDate;

}
