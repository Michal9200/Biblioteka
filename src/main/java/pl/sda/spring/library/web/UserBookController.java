package pl.sda.spring.library.web;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.spring.library.domain.book.BookService;
import pl.sda.spring.library.domain.user.UserService;
import pl.sda.spring.library.domain.userbook.UserBook;
import pl.sda.spring.library.domain.userbook.UserBookService;
import pl.sda.spring.library.external.book.JpaBookRepository;
import pl.sda.spring.library.external.user_book.JpaUserBookRepository;
import pl.sda.spring.library.external.user_book.UserBookEntity;
import pl.sda.spring.library.external.user.JpaUserRepository;

import java.time.LocalDate;


@Controller
@RequestMapping("/lib/myBorrowBooks")
@AllArgsConstructor
public class UserBookController {


    private BookService bookService;
    private UserService userService;
    private UserBookService userBookService;

    @RequestMapping
    @PreAuthorize("isAuthenticated()")
    ModelAndView displayCarsPage() {
        ModelAndView mav = new ModelAndView("myBorrowBooks.html");
        mav.addObject("books", userBookService.findAllByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        mav.addObject("todayDate", LocalDate.now());

        return mav;
    }

    @GetMapping("/add/{id}")
    @PreAuthorize("hasRole('USER')")
    String displayAddCarPage(@PathVariable Long id) {
        userBookService.create(new UserBook(bookService.getBookById(id).get(),
                userService.findUserByName((SecurityContextHolder.getContext().getAuthentication().getName())).get(), LocalDate.now()));
        return "redirect:/lib/book";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    String handleDeleteCar(@PathVariable Long id) {
        userBookService.delete(id);
        return "redirect:/lib/myBorrowBooks";
    }


}
