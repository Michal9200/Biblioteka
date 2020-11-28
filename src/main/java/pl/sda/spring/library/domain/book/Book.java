package pl.sda.spring.library.domain.book;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sda.spring.library.validator.Isbn;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    private Long id;
    @NotBlank(message = "Title should be not blank")
    private String title;
    @NotBlank(message = "Author should be not blank")
    private String author;

    @Isbn
    private String isbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearOfPublishment;

    private String publishingHouse;
}
