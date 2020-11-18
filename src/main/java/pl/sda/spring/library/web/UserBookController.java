package pl.sda.spring.library.web;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.spring.library.domain.book.BookService;
import pl.sda.spring.library.domain.user.UserService;
import pl.sda.spring.library.external.book.BookEntity;
import pl.sda.spring.library.external.book.JpaBookRepository;
import pl.sda.spring.library.external.borrowBook.JpaUserBookRepository;
import pl.sda.spring.library.external.borrowBook.UserBookEntity;
import pl.sda.spring.library.external.user.JpaUserRepository;
import pl.sda.spring.library.external.user.UserEntity;

import java.time.LocalDate;
import java.util.Optional;


@Controller
@RequestMapping("/lib/myBorrowBooks")
@AllArgsConstructor
public class UserBookController {

    private JpaBookRepository jpaBookRepository;
    private JpaUserRepository jpaUserRepository;
    private JpaUserBookRepository jpaUserBookRepository;

    @RequestMapping
    @PreAuthorize("isAuthenticated()")
    ModelAndView displayCarsPage() {
        ModelAndView mav = new ModelAndView("myBorrowBooks.html");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        mav.addObject("books", jpaUserBookRepository.findAllByUserEntity_Username(userName));
        mav.addObject("todayDate", LocalDate.now());

        return mav;
    }

    @GetMapping("/add/{id}")
    @PreAuthorize("hasRole('USER')")
    String displayAddCarPage(@PathVariable Long id) {
        BookEntity book = jpaBookRepository.getOne(id);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = jpaUserRepository.findByUsername(userName);

        UserBookEntity userBookEntity = new UserBookEntity();
        userBookEntity.setUserEntity(user.get());
        userBookEntity.setBookEntity(book);
        userBookEntity.setBorrowDate(LocalDate.now());

        jpaUserBookRepository.save(userBookEntity);
        return "redirect:/lib/book";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    String handleDeleteCar(@PathVariable Long id) {
        jpaUserBookRepository.deleteById(id);
        return "redirect:/lib/myBorrowBooks";
    }


}
