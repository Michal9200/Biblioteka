package pl.sda.spring.library.domain.user;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.spring.library.domain.book.Book;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String role;

    public void encodePasword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
    }

}
