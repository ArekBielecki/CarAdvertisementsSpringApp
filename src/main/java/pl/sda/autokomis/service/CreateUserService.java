package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.exception.UsernameAlreadyExistsException;
import pl.sda.autokomis.model.User;
import pl.sda.autokomis.model.createrequest.CreateUserRequest;
import pl.sda.autokomis.repository.UserRepository;

import java.util.ArrayList;

@Service
public class CreateUserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void createUser(CreateUserRequest request) {
        if(!doesUsernameExistInDatabase(request.getUsername())){
            User user = User
                    .builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .listOfAdvertisementsIds(new ArrayList<>())
                    .build();

            userRepository.save(user);

        }

    }

    private boolean doesUsernameExistInDatabase(String username){
        if(userRepository.getByUsername(username) != null){
            throw new UsernameAlreadyExistsException("Użytkownik o nazwie: " + username + " już istnieje w bazie!");
        }
        else{
            return false;
        }
    }
}
