package pl.sda.autokomis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.sda.autokomis.model.Car;

import java.util.Set;

@Repository
public interface CarRepository extends MongoRepository<Car, String>{

    Car getByCarId(String carId);
}
