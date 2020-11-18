package pl.sda.spring.library.external.book;

import pl.sda.spring.library.model.SearchParams;

import java.util.List;

public interface CustomDatabaseBookRepository {

    List<BookEntity> findBasedOnSearchParams(SearchParams searchParams);

}
