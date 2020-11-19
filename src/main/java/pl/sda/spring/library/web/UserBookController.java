package pl.sda.spring.library.web;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.spring.library.external.book.JpaBookRepository;
import pl.sda.spring.library.external.user_book.JpaUserBookRepository;
import pl.sda.spring.library.external.user_book.UserBookEntity;
import pl.sda.spring.library.external.user.JpaUserRepository;

import java.time.LocalDate;


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
        mav.addObject("books", jpaUserBookRepository.findAllByUserEntity_Username(SecurityContextHolder.getContext().getAuthentication().getName()));
        mav.addObject("todayDate", LocalDate.now());

        return mav;
    }

    @GetMapping("/add/{id}")
    @PreAuthorize("hasRole('USER')")
    String displayAddCarPage(@PathVariable Long id) {
        jpaUserBookRepository.save(new UserBookEntity((jpaUserRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).get(),
                jpaBookRepository.getOne(id),
                LocalDate.now()));
        return "redirect:/lib/book";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    String handleDeleteCar(@PathVariable Long id) {
        jpaUserBookRepository.deleteById(id);
        return "redirect:/lib/myBorrowBooks";
    }


}
