package pl.sda.autokomis.model;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdvertisementDTO {

    private Advertisement advertisement;
    private Car car;
}
