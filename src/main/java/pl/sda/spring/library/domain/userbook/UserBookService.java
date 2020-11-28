package pl.sda.spring.library.domain.userbook;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.spring.library.domain.book.BookService;
import pl.sda.spring.library.domain.user.UserService;

import java.util.List;


@Service
@AllArgsConstructor
public class UserBookService implements UserBookRepository {

    private UserBookRepository userBookRepository;

    @Override
    public void create(UserBook userBook) {
        userBookRepository.create(userBook);
    }

    @Override
    public void delete(Long id) {
        userBookRepository.delete(id);
    }

    @Override
    public List<UserBook> findAllByUsername(String username) {
        return userBookRepository.findAllByUsername(username);
    }


}
