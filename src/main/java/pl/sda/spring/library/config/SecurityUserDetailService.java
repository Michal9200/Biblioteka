package pl.sda.spring.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sda.spring.library.domain.user.User;
import pl.sda.spring.library.domain.user.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(user -> mapToUserDetails(user))
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    private UserDetails mapToUserDetails(User user){
        List<GrantedAuthority> authorities =
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
