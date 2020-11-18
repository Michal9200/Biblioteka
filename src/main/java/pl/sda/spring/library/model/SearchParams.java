package pl.sda.spring.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchParams {
    private String title;
    private String author;
    private String isbn;
    private Integer yearOfPublishment;
}
