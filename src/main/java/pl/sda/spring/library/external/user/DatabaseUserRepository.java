package pl.sda.spring.library.external.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.spring.library.domain.user.User;
import pl.sda.spring.library.domain.user.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserEntity;

    @Override
    public void create(User user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
        jpaUserEntity.save(entity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserEntity.findByUsername(username).map(ent -> new User(ent.getUsername(), ent.getPassword(), ent.getRole()));
    }


}
