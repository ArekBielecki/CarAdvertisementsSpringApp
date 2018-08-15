package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.Car;
import pl.sda.autokomis.model.updaterequest.UpdateAdvertisementRequest;
import pl.sda.autokomis.repository.AdvertisementRepository;
import pl.sda.autokomis.repository.CarRepository;

import java.security.Principal;

@Service
public class UpdateAdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private FindAdvertisementService findAdvertisementService;
    private CarRepository carRepository;
    private FindCarService findCarService;
    private CheckPrivilegeService checkPrivilegeService;

    @Autowired
    public UpdateAdvertisementService(
            AdvertisementRepository advertisementRepository,
            FindAdvertisementService findAdvertisementService,
            CarRepository carRepository,
            FindCarService findCarService,
            CheckPrivilegeService checkPrivilegeService) {
        this.advertisementRepository = advertisementRepository;
        this.findAdvertisementService = findAdvertisementService;
        this.carRepository = carRepository;
        this.findCarService = findCarService;
        this.checkPrivilegeService = checkPrivilegeService;
    }


    public void updateAdvertisement(UpdateAdvertisementRequest request, Principal principal) {

        Advertisement advertisement = findAdvertisementService.findByAdvertisementId(request.getAdvertisementId());

        if(checkPrivilegeService.checkPrivilegeToUpdate(advertisement, principal)){
            Car car = findCarService.getCarByAdvertisement(advertisement);

            if (request.getPrice() != null) {
                advertisement.setPrice(request.getPrice());
            }
            if (request.getMake() != null) {
                car.setMake(request.getMake());
            }
            if (request.getModel() != null) {
                car.setModel(request.getModel());
            }
            if (request.getMileage() != null) {
                car.setMileage(request.getMileage());
            }
            if (request.getProductionYear() != null) {
                car.setProductionYear(request.getProductionYear());
            }
            if (request.getColor() != null) {
                car.setColor(request.getColor());
            }
            carRepository.save(car);
            advertisementRepository.save(advertisement);
        }
    }

    public void markAdvertisementAsSold(String advertisementId, Principal principal) {
        Advertisement advertisement = findAdvertisementService.findByAdvertisementId(advertisementId);
        if(checkPrivilegeService.checkPrivilegeToUpdate(advertisement, principal)){
            advertisement.setSold(true);
            advertisementRepository.save(advertisement);
        }
    }
}
