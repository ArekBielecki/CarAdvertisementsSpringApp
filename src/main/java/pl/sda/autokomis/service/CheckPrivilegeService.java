package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.exception.PriviligeNotGrantedException;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.User;

import java.security.Principal;

@Service
public class CheckPrivilegeService {

    private MongoUserDetailsService userDetailsService;

    @Autowired
    public CheckPrivilegeService(MongoUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public boolean checkPrivilegeToUpdate(Advertisement advertisement, Principal principal){
        User user = userDetailsService.getUserFromRepository(principal.getName());
        if(advertisement.getUserId().equals(user.getUserId())){
            return true;
        }
        else{
            throw new PriviligeNotGrantedException("Ogłoszenie o id: " + advertisement.getAdvertisementId()
                    + " nie jest ofertą użytkownika o id: " + user.getUserId());
        }
    }
}
