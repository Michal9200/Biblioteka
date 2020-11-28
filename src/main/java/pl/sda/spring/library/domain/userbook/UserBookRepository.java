package pl.sda.spring.library.domain.userbook;

import java.util.List;

public interface UserBookRepository {

    void create(UserBook userBook);
    void delete(Long id);
    List<UserBook> findAllByUsername(String username);

}
