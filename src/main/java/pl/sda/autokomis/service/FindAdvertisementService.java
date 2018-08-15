package pl.sda.autokomis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.AdvertisementDTO;
import pl.sda.autokomis.model.Car;
import pl.sda.autokomis.model.User;
import pl.sda.autokomis.repository.AdvertisementRepository;

import java.security.Principal;
import java.util.*;


@Service
public class FindAdvertisementService {

    private AdvertisementRepository advertisementRepository;
    private MongoUserDetailsService userDetailsService;
    private AdvertisementDTOConverterService converterService;

    @Autowired
    public FindAdvertisementService(
            AdvertisementRepository advertisementRepository,
            MongoUserDetailsService userDetailsService,
            AdvertisementDTOConverterService converterService) {
        this.advertisementRepository = advertisementRepository;
        this.userDetailsService = userDetailsService;
        this.converterService = converterService;
    }


    public Page<Advertisement> getAllAdvertisements(Pageable pageable) {
        return advertisementRepository.getAllBy(pageable);
    }


    public Page<Advertisement> getAllAdvertisementsByUser(Pageable pageable, Principal principal) {
        User user = userDetailsService.getUserFromRepository(principal.getName());
        return advertisementRepository.getAllByUserId(user.getUserId(), pageable);
    }

    public List<Advertisement> getAllAdvertisementsAsList(){
        return advertisementRepository.getAllBy();
    }

    public Page<Advertisement> getAllAdvertisementsByParams(Map<String, List<String>> filterParams, Pageable pageable) {
        List<AdvertisementDTO> advertisementsDTOs
                = converterService.convertAdvertsAndCarsToAdvertDTOsList(getAllAdvertisementsAsList());

        Set<AdvertisementDTO> getAdvertisementsByCarModel = new HashSet<>();
        Set<AdvertisementDTO> getAdvertisementsByCarMake = new HashSet<>();
        Set<AdvertisementDTO> getAdvertisementsByCarColor = new HashSet<>();
        Set<AdvertisementDTO> getAdvertisementsByCarProductionYear = new HashSet<>();
        Set<AdvertisementDTO> getAdvertisementsByCarMinPrice = new HashSet<>();
        Set<AdvertisementDTO> getAdvertisementsByCarMaxPrice = new HashSet<>();

        List<Set<AdvertisementDTO>> singleFilterResultsList = new ArrayList<>();

        for (AdvertisementDTO advertisementsDTO : advertisementsDTOs) {
            for (String parameter : filterParams.keySet()) {
                switch (parameter) {

                    case "model":
                        if(!singleFilterResultsList.contains(getAdvertisementsByCarModel)){
                            singleFilterResultsList.add(getAdvertisementsByCarModel);
                        }
                        for (String model : filterParams.get("model")) {
                            if(advertisementsDTO.getCar().getModel().equalsIgnoreCase(model)){
                                getAdvertisementsByCarModel.add(advertisementsDTO);
                            }
                        }
                        break;

                    case "make":
                        if(!singleFilterResultsList.contains(getAdvertisementsByCarMake)){
                            singleFilterResultsList.add(getAdvertisementsByCarMake);
                        }
                        for(String make : filterParams.get("make")){
                            if(advertisementsDTO.getCar().getMake().equalsIgnoreCase(make)){
                                getAdvertisementsByCarMake.add(advertisementsDTO);
                            }
                        }
                        break;

                    case "color":
                        if(!singleFilterResultsList.contains(getAdvertisementsByCarColor)){
                            singleFilterResultsList.add(getAdvertisementsByCarColor);
                        }
                        for(String color: filterParams.get("color")){
                            if(advertisementsDTO.getCar().getColor().equalsIgnoreCase(color)){
                                getAdvertisementsByCarColor.add(advertisementsDTO);
                            }
                        }

                        break;

                    case "productionYear":
                        if(!singleFilterResultsList.contains(getAdvertisementsByCarProductionYear)){
                            singleFilterResultsList.add(getAdvertisementsByCarProductionYear);
                        }
                        for(String productionYear : filterParams.get("productionYear")){
                            if(advertisementsDTO.getCar().getProductionYear() == Integer.parseInt(productionYear)){
                                getAdvertisementsByCarProductionYear.add(advertisementsDTO);
                            }
                        }
                        break;
                    case "priceMin":
                        if(!singleFilterResultsList.contains(getAdvertisementsByCarMinPrice)){
                            singleFilterResultsList.add(getAdvertisementsByCarMinPrice);
                        }
                        if(advertisementsDTO.getAdvertisement().getPrice()
                                >= Integer.parseInt(filterParams.get("priceMin").get(0))){
                            getAdvertisementsByCarMinPrice.add(advertisementsDTO);
                        }
                        break;
                    case "priceMax":
                        if(!singleFilterResultsList.contains(getAdvertisementsByCarMaxPrice)){
                            singleFilterResultsList.add(getAdvertisementsByCarMaxPrice);
                        }
                        if(advertisementsDTO.getAdvertisement().getPrice()
                                <= Integer.parseInt(filterParams.get("priceMax").get(0))){
                            getAdvertisementsByCarMaxPrice.add(advertisementsDTO);
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        for (Set<AdvertisementDTO> advertisementDTOs : singleFilterResultsList) {
            singleFilterResultsList.get(0).retainAll(advertisementDTOs);
        }
        Set<AdvertisementDTO> resultSet = singleFilterResultsList.get(0);

        List<Advertisement> listOfAllFiltersResult = new ArrayList<>();

        for (AdvertisementDTO advertisementDTO : resultSet) {
            listOfAllFiltersResult.add(advertisementDTO.getAdvertisement());
        }

        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > listOfAllFiltersResult.size()
                ? listOfAllFiltersResult.size() : (start + pageable.getPageSize());

        return new PageImpl<>(listOfAllFiltersResult.subList(start, end), pageable, listOfAllFiltersResult.size());
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
