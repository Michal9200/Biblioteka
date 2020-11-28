package pl.sda.spring.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.spring.library.validator.AlreadyExistException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AlreadyExistException(String.format("User with name %s already exists", user.getUsername()));
        }
        user.encodePasword(passwordEncoder);
        userRepository.create(user);
    }

    public Optional<User> findUserByName (String name){
        return userRepository.findByUsername(name);
    }

}
