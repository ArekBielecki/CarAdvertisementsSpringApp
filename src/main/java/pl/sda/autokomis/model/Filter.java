package pl.sda.autokomis.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class Filter {

    private List<String> carMakes;

    private List<String> carModels;

    private List<String> carColors;

    private Integer minMileage;

    private Integer maxMileage;

    private Integer minProductionYear;

    private Integer maxProductionYear;

    private Integer minPrice;

    private Integer maxPrice;

    private Boolean isSold;

}
