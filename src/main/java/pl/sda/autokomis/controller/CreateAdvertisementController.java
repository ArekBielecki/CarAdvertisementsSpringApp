package pl.sda.autokomis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.autokomis.model.createrequest.CreateAdvertisementRequest;
import pl.sda.autokomis.service.CreateAdvertisementService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/advertisement")
public class CreateAdvertisementController {

    private CreateAdvertisementService createAdvertisementService;

    @Autowired
    public CreateAdvertisementController(CreateAdvertisementService createAdvertisementService) {
        this.createAdvertisementService = createAdvertisementService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAdvertisement(@RequestBody @Valid CreateAdvertisementRequest adRequest, Principal principal) {

        createAdvertisementService.addAdvertisement(adRequest, principal);
    }
}
