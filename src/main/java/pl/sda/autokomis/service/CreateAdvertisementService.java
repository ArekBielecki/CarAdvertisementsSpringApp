package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.Car;
import pl.sda.autokomis.model.createrequest.CreateAdvertisementRequest;
import pl.sda.autokomis.model.createrequest.CreateCarRequest;
import pl.sda.autokomis.model.User;
import pl.sda.autokomis.repository.AdvertisementRepository;
import pl.sda.autokomis.repository.CarRepository;
import pl.sda.autokomis.repository.UserRepository;

import java.security.Principal;

@Service
public class CreateAdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private CarRepository carRepository;
    private UserDetailsService userDetailsService;
    private UserRepository userRepository;

    @Autowired
    public CreateAdvertisementService(
            AdvertisementRepository advertisementRepository,
            CarRepository carRepository,
            UserDetailsService userDetailsService,
            UserRepository userRepository) {
        this.advertisementRepository = advertisementRepository;
        this.carRepository = carRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public void addAdvertisement(CreateAdvertisementRequest advertisementRequest, Principal principal){

        User user = ((MongoUserDetailsService) userDetailsService).getUserFromRepository(principal.getName());

        Car car = createCar(advertisementRequest.getCreateCarRequest());
        carRepository.save(car);

        Advertisement advertisement = Advertisement.builder()
                .userId(user.getUserId())
                .carId(car.getCarId())
                .price(advertisementRequest.getPrice())
                .isSold(false)
                .build();
        advertisementRepository.save(advertisement);

        user.getListOfAdvertisementsIds().add(advertisement.getAdvertisementId());
        userRepository.save(user);

    }

    private Car createCar(CreateCarRequest carRequest){
        return Car.builder()
                .make(carRequest.getMake())
                .model(carRequest.getModel())
                .mileage(carRequest.getMileage())
                .color(carRequest.getColor())
                .productionYear(carRequest.getProductionYear())
                .build();

    }
}
