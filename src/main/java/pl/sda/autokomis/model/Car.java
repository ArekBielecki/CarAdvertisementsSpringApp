package pl.sda.autokomis.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "cars")
public class Car {

    @Id
    private String carId;

    private String make;

    private String model;

    private Integer mileage;

    private String color;

    private Integer productionYear;
}
