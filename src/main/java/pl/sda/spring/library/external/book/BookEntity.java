package pl.sda.spring.library.external.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sda.spring.library.external.user_book.UserBookEntity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false, unique = true, length = 13)
    private String isbn;
    private LocalDate yearOfPublishment;
    private String publishingHouse;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private Set<UserBookEntity> userBooks;



}
