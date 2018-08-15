package pl.sda.autokomis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.autokomis.model.createrequest.CreateUserRequest;
import pl.sda.autokomis.service.CreateUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class CreateUserController {

    private CreateUserService createUserService;

    @Autowired
    public CreateUserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid CreateUserRequest request){
        createUserService.createUser(request);
    }
}
