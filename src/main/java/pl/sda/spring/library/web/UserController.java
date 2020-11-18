package pl.sda.spring.library.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.spring.library.domain.user.User;
import pl.sda.spring.library.domain.user.UserService;


@Controller
@RequestMapping("/lib/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ModelAndView displayRegisterPage(){
        ModelAndView mav = new ModelAndView("register.html");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping
    public String handleUserRegistration(@ModelAttribute User user){
        userService.register(user);
        return "redirect:/lib/login";
    }

}
