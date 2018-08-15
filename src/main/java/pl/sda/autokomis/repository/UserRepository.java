package pl.sda.autokomis.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.sda.autokomis.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    User getByUsername(String username);
}
