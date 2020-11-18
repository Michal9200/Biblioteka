package pl.sda.spring.library.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.spring.library.config.CompanyInfo;

import java.time.LocalDate;


@Controller
@AllArgsConstructor
public class MainPageController {

    private CompanyInfo companyInfo;

    @GetMapping("/")
    ModelAndView displayMainPage() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("date", LocalDate.now().toString());
        mav.addObject("info", companyInfo);

        mav.setViewName("main.html");
        return mav;
    }



}
