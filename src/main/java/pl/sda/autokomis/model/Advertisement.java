package pl.sda.autokomis.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Data
@Document(collection = "advertisements")
public class Advertisement {

    @Id
    private String advertisementId;

    private String userId;

    private String carId;

    private Double price;

    private boolean isSold;
}
