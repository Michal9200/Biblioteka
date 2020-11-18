package pl.sda.spring.library.web;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.spring.library.domain.book.Book;
import pl.sda.spring.library.domain.book.BookService;
import pl.sda.spring.library.model.SearchParams;

import javax.validation.Valid;
import java.time.LocalDate;

import java.util.Optional;


@Controller
@RequestMapping("lib/book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @RequestMapping
    @PreAuthorize("isAuthenticated()")
    ModelAndView displayCarsPage() {
        ModelAndView mav = new ModelAndView("books.html");
        mav.addObject("books", bookService.getAll());
        mav.addObject("todayDate", LocalDate.now());
        mav.addObject("params", new SearchParams());

        return mav;
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    ModelAndView handleCarFiltering(@ModelAttribute("params") SearchParams params) {
        ModelAndView mav = new ModelAndView("books.html");
        mav.addObject("books", bookService.searchByParams(params));
        mav.addObject("todayDate", LocalDate.now());
        mav.addObject("params", params);
        return mav;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ModelAndView displayAddCarPage() {
        ModelAndView mav = new ModelAndView("addBook.html");
        mav.addObject("book", new Book());
        return mav;
    }

    @PostMapping("/addOrEdit")
    @PreAuthorize("hasRole('ADMIN')")
    String handleAddCar(@ModelAttribute("book") @Valid Book book) {

        if (book.getId() != null) {
            bookService.update(book);
        } else {
            bookService.create(book);
        }
        String name = new SecurityProperties.User().getName();
        return "redirect:/lib/book";
    }



    @GetMapping("/edit/{id}")
    ModelAndView displayEditCarPage(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        ModelAndView mav = new ModelAndView();
        if (book.isPresent()) {
            mav.addObject("book", book.get());
            mav.setViewName("addBook.html");
        } else {
            mav.addObject("message", String.format("Książka z id %d nie istnieje", id));
            mav.setViewName("error.html");
        }
        return mav;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    String handleDeleteCar(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/lib/book";
    }

}
