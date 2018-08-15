package pl.sda.autokomis.model.updaterequest;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Validated
public class UpdateAdvertisementRequest {

    private String advertisementId;

    private Double price;

    @Size(min = 2)
    private String make;

    private String model;

    @Min(value = 0)
    private Integer mileage;

    private String color;

    @Min(value = 1900)
    @Max(value = 2018)
    private Integer productionYear;

}
