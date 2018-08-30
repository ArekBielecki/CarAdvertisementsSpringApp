package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.*;
import pl.sda.autokomis.repository.AdvertisementRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class FindAdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private MongoUserDetailsService userDetailsService;
    private FindCarService findCarService;

    @Autowired
    public FindAdvertisementService(
            AdvertisementRepository advertisementRepository,
            MongoUserDetailsService userDetailsService,
            FindCarService findCarService) {
        this.advertisementRepository = advertisementRepository;
        this.userDetailsService = userDetailsService;
        this.findCarService = findCarService;
    }


    public Page<Advertisement> getAllAdvertisements(Pageable pageable) {
        return advertisementRepository.getAllBy(pageable);
    }


    public Page<Advertisement> getAllAdvertisementsByUser(Pageable pageable, Principal principal) {
        User user = userDetailsService.getUserFromRepository(principal.getName());
        return advertisementRepository.getAllByUserId(user.getUserId(), pageable);
    }

    public List<Advertisement> getAllAdvertisementsAsList() {
        return advertisementRepository.getAllBy();
    }

    public Page<Advertisement> getAllAdvertisementsByParams(Filter filter, Pageable pageable) {

        Set<Advertisement> advertisementsByParams = advertisementRepository.getAdvertisementsByParams(
                filter.getMinPrice(), filter.getMaxPrice(), filter.getIsSold());

        List<Car> carsByParams = findCarService.getCarsByParams(filter);

        List<String> carsIdsList = carsByParams.stream().map(car -> car.getCarId()).collect(Collectors.toList());

        Set<Advertisement> advertisementsByCarsIds = advertisementRepository.getAllByCarsIds(carsIdsList);

        advertisementsByParams.retainAll(advertisementsByCarsIds);

        List<String> filteredAdvertisementIdsList = advertisementsByParams.stream()
                .map(advertisement -> advertisement.getAdvertisementId())
                .collect(Collectors.toList());

        return advertisementRepository.getAllByAdvertisementsIds(filteredAdvertisementIdsList, pageable);
    }

    public Page<Advertisement> getSoldAdvertisements(Pageable pageable) {
        return advertisementRepository.getAdvertisementsByIsSold(true, pageable);
    }

    public Page<Advertisement> getUnsoldAdvertisements(Pageable pageable) {
        return advertisementRepository.getAdvertisementsByIsSold(false, pageable);
    }

    public Advertisement findByAdvertisementId(String advertisementId) {
        return advertisementRepository.getAdvertisementByAdvertisementId(advertisementId);
    }


}
