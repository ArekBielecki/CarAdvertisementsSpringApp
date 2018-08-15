package pl.sda.autokomis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sda.autokomis.model.updaterequest.UpdateAdvertisementRequest;
import pl.sda.autokomis.service.UpdateAdvertisementService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/advertisement")
public class UpdateAdvertisementController {

    private UpdateAdvertisementService updateAdvertisementService;

    @Autowired
    public UpdateAdvertisementController(UpdateAdvertisementService updateAdvertisementService) {
        this.updateAdvertisementService = updateAdvertisementService;
    }

    @PutMapping("/update")
    public void updateAdvertisement(@RequestBody @Valid UpdateAdvertisementRequest request, Principal principal){
        updateAdvertisementService.updateAdvertisement(request, principal);
    }

    @PutMapping("/markAsSold")
    public void markAdvertisementAsSold(@RequestParam String advertisementId, Principal principal){
        updateAdvertisementService.markAdvertisementAsSold(advertisementId, principal);
    }
}
