package pl.sda.spring.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user){
        user.encodePasword(passwordEncoder);
        userRepository.create(user);
    }

    public Optional<User> findUserByName (String name){
        return userRepository.findByUsername(name);
    }

}
