package pl.sda.autokomis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.AdvertisementDTO;
import pl.sda.autokomis.service.AdvertisementDTOConverterService;
import pl.sda.autokomis.service.FindAdvertisementService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/advertisements")
public class DisplayAdvertisementController {

    private FindAdvertisementService findAdvertisementService;
    private AdvertisementDTOConverterService converterService;

    @Autowired
    public DisplayAdvertisementController(
            FindAdvertisementService findAdvertisementService, AdvertisementDTOConverterService converterService) {
        this.findAdvertisementService = findAdvertisementService;
        this.converterService = converterService;
    }

    @GetMapping("/all")
    public Page<AdvertisementDTO> getAllAdvertisements(@RequestParam Integer size, @RequestParam Integer pageNumber) {
        Pageable pageable = new PageRequest(pageNumber, size);
        Page<Advertisement> allAdvertisements = findAdvertisementService.getAllAdvertisements(pageable);
        return converterService.convertAdvertsAndCarsToAdvertDTOsPage(allAdvertisements);
    }

    @GetMapping("/user")
    public Page<AdvertisementDTO> getUsersAdvertisements(
            Principal principal,
            @RequestParam Integer size,
            @RequestParam Integer pageNumber) {
        Pageable pageable = new PageRequest(pageNumber, size);
        Page<Advertisement> allAdvertisementsByUser
                = findAdvertisementService.getAllAdvertisementsByUser(pageable, principal);
        return converterService.convertAdvertsAndCarsToAdvertDTOsPage(allAdvertisementsByUser);

    }

    @GetMapping("/{byFilter}")
    public Page<AdvertisementDTO> getFilteredAdvertisements(
            @MatrixVariable(pathVar = "byFilter") Map<String, List<String>> filterParams,
            @RequestParam Integer size,
            @RequestParam Integer pageNumber){
        Pageable pageable = new PageRequest(pageNumber, size);
        Page<Advertisement> allAdvertisementsByParams
                = findAdvertisementService.getAllAdvertisementsByParams(filterParams, pageable);
        return converterService.convertAdvertsAndCarsToAdvertDTOsPage(allAdvertisementsByParams);
    }


    @GetMapping("/sold")
    public Page<AdvertisementDTO> getSoldAdvertisements(@RequestParam Integer size, @RequestParam Integer pageNumber){
        Pageable pageable = new PageRequest(pageNumber, size);
        Page<Advertisement> soldAdvertisements = findAdvertisementService.getSoldAdvertisements(pageable);
        return converterService.convertAdvertsAndCarsToAdvertDTOsPage(soldAdvertisements);
    }

    @GetMapping("/unsold")
    public Page<AdvertisementDTO> getUnSoldAdvertisements(@RequestParam Integer size, @RequestParam Integer pageNumber){
        Pageable pageable = new PageRequest(pageNumber, size);
        Page<Advertisement> unsoldAdvertisements = findAdvertisementService.getUnsoldAdvertisements(pageable);
        return converterService.convertAdvertsAndCarsToAdvertDTOsPage(unsoldAdvertisements);
    }

}
