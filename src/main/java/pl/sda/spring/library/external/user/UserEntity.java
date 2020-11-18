package pl.sda.spring.library.external.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sda.spring.library.external.book.BookEntity;
import pl.sda.spring.library.external.borrowBook.UserBookEntity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 100)
    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<UserBookEntity> userBooks = new HashSet<>();


}
