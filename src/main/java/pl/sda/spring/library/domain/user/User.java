package pl.sda.spring.library.domain.user;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@Setter
@Builder
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
