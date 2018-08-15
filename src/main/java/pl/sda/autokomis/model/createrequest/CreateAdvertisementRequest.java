package pl.sda.autokomis.model.createrequest;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Validated
public class CreateAdvertisementRequest {

    @NotNull
    private double price;

    @NotNull
    private CreateCarRequest createCarRequest;

}
