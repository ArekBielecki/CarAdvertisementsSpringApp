package pl.sda.autokomis.model.createrequest;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Validated
public class CreateCarRequest {

    @NotNull
    @Size(min = 2)
    String make;

    @NotNull
    String model;

    @Min(value = 0)
    private Integer mileage;

    private String color;

    @Min(value = 1900)
    @Max(value = 2018)
    private Integer productionYear;


}
