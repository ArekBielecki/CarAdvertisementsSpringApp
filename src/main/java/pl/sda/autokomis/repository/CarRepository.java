package pl.sda.autokomis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sda.autokomis.model.Car;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {

    Car getByCarId(String carId);

        @Query("{$and : [" +
            "{ $or : [ { $where: '?0.length==0' } , { make : { $in: ?0 } } ] }, " +
            "{ $or : [ { $where: '?1.length==0'  } , { model : { $in: ?1 } } ] }, " +
            "{ $or : [ { $where: '?2.length==0'  } , { color : { $in: ?2 } } ] }, " +
            "{ $or : [ { $expr: { $eq : [?3, null] } } , { mileage : { $gt: ?3 } } ] }," +
            "{ $or : [ { $expr: { $eq : [?4, null] } } , { mileage : { $lt: ?4 } } ] }," +
            "{ $or : [ { $expr: { $eq : [?5, null] } } , { productionYear : { $gt: ?5 } } ] }," +
            "{ $or : [ { $expr: { $eq : [?6, null] } } , { productionYear : { $lt: ?6 } } ] }" +
            " ]}")
    List<Car> getCarsByParams(
            List<String> makes,
            List<String> models,
            List<String> colors,
            Integer minMileage,
            Integer maxMileage,
            Integer minProductionYear,
            Integer maxProductionYear);

}
