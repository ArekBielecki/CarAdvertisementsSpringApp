package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.User;
import pl.sda.autokomis.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class MongoUserDetailsService implements UserDetailsService{

    private UserRepository userRepository;


    @Autowired
    public MongoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Użytkownik nie znaleziony");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public User getUserFromRepository(String username){
        User user = userRepository.getByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Użytkownik nie znaleziony");
        }
        return user;
    }
}
