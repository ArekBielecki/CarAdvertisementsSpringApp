package pl.sda.autokomis.model.createrequest;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Validated
public class CreateUserRequest {

    @NotNull
    @Size(min = 6, max = 32)
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;
}
