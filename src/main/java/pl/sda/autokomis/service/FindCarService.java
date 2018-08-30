package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.Car;
import pl.sda.autokomis.model.Filter;
import pl.sda.autokomis.repository.CarRepository;

import java.util.List;
import java.util.Set;

@Service
public class FindCarService {

    private CarRepository carRepository;

    @Autowired
    public FindCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getCarByAdvertisement(Advertisement advertisement){
        return carRepository.getByCarId(advertisement.getCarId());
    }

    public List<Car> getCarsByParams(Filter filter){
        return carRepository.getCarsByParams(
                filter.getCarMakes(),
                filter.getCarModels(),
                filter.getCarColors(),
                filter.getMinMileage(),
                filter.getMaxMileage(),
                filter.getMinProductionYear(),
                filter.getMaxProductionYear()
        );
    }
}
