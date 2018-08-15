package pl.sda.autokomis.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.sda.autokomis.model.Advertisement;
import pl.sda.autokomis.model.AdvertisementDTO;
import pl.sda.autokomis.model.Car;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdvertisementDTOConverterService {

    private FindCarService findCarService;

    public AdvertisementDTOConverterService(FindCarService findCarService) {
        this.findCarService = findCarService;
    }

    public Page<AdvertisementDTO> convertAdvertsAndCarsToAdvertDTOsPage(Page<Advertisement> advertisements) {
        return advertisements.map(new Converter<Advertisement, AdvertisementDTO>() {
            @Override
            public AdvertisementDTO convert(Advertisement advertisement) {
                Car car = findCarService.getCarByAdvertisement(advertisement);
                return AdvertisementDTO.builder()
                        .advertisement(advertisement)
                        .car(car)
                        .build();
            }
        });
    }

    public List<AdvertisementDTO> convertAdvertsAndCarsToAdvertDTOsList(List<Advertisement> advertisements) {
        List<AdvertisementDTO> convertedList = new ArrayList<>();
        for (Advertisement advertisement : advertisements) {
            convertedList.add(AdvertisementDTO.builder()
                    .advertisement(advertisement)
                    .car(findCarService.getCarByAdvertisement(advertisement))
                    .build());
        }
        return convertedList;
    }

    public AdvertisementDTO convertAdverAndCarToAdvertDTO(Advertisement advertisement) {
        Car car = findCarService.getCarByAdvertisement(advertisement);
        return AdvertisementDTO.builder()
                .advertisement(advertisement)
                .car(car)
                .build();
    }


}
