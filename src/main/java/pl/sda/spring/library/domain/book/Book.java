package pl.sda.spring.library.domain.book;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sda.spring.library.validator.Isbn;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    private Long id;
    private String title;
    private String author;

    @Isbn
    private String isbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearOfPublishment;

    private String publishingHouse;
}
