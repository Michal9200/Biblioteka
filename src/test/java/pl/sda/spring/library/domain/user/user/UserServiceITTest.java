package pl.sda.spring.library.domain.user.user;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.spring.library.domain.user.User;
import pl.sda.spring.library.domain.user.UserService;
import pl.sda.spring.library.external.user.JpaUserRepository;
import pl.sda.spring.library.external.user.UserEntity;
import pl.sda.spring.library.validator.AlreadyExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceITTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateUser() {
        //given
        String password = "Pasword";
        User user = new User("login", password, "admin");
        //when
        userService.register(user);
        //then
        List<UserEntity> all = userRepository.findAll();
        Assertions.assertEquals(1, all.size());
        UserEntity persistedUser = all.get(0);
        Assertions.assertEquals("login", persistedUser.getUsername());
        Assertions.assertEquals("admin", persistedUser.getRole());
        Assertions.assertTrue(passwordEncoder.matches(password, persistedUser.getPassword()));
    }

    @Test
    public void shouldNotPersistUserWhenUsernameAlreadyExists() {
        //given
        User user = new User( "Janusz", "123456", "USER");
        UserEntity userEntity = new UserEntity(null ,"Janusz", "98765", "ADMIN", null);
        userRepository.save(userEntity);
        //when
        AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
                () -> userService.register(user));
        //than
        Assertions.assertEquals("User with name Janusz already exists", ex.getMessage());
    }

    @Test
    public void shouldReturnByUsername() {
        //given
        UserEntity userEntity = new UserEntity(null ,"Janusz", "789", "ADMIN", null);
        UserEntity userEntity1 = new UserEntity(null ,"Piotr", "123", "USER", null);
        UserEntity userEntity2 = new UserEntity(null ,"Angelika", "456", "USER", null);
        userRepository.saveAll(Lists.list(userEntity, userEntity1, userEntity2));
        //when
        List<User> userList = new ArrayList<>();
        userList.add(userService.findUserByName("Piotr").get());
        //than
        Assertions.assertEquals(1, userList.size());
        Assertions.assertTrue(userList.stream().allMatch(user -> user.getUsername().equals("Piotr")));

    }

    @Test
    public void shouldReturnEmptyUserWhenNoUserExistWithThisUsername() {
        //given
        UserEntity userEntity = new UserEntity(null ,"Janusz", "789", "ADMIN", null);
        UserEntity userEntity1 = new UserEntity(null ,"Piotr", "123", "USER", null);
        UserEntity userEntity2 = new UserEntity(null ,"Angelika", "456", "USER", null);
        userRepository.saveAll(Lists.list(userEntity, userEntity1, userEntity2));
        //when
        Optional<User> user = userService.findUserByName("Kasia");
        //than;
        Assertions.assertTrue(user.isEmpty());
    }

}
