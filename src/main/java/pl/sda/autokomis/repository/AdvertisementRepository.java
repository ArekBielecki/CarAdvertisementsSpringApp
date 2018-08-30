package pl.sda.autokomis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sda.autokomis.model.Advertisement;

import java.util.List;
import java.util.Set;


@Repository
public interface AdvertisementRepository extends MongoRepository<Advertisement, String>{

    Advertisement getAdvertisementByAdvertisementId(String advertisementId);
    Page<Advertisement> getAllBy(Pageable pageable);
    List<Advertisement> getAllBy();
    Page<Advertisement> getAllByUserId(String userId, Pageable pageable);
    Page<Advertisement> getAdvertisementsByIsSold(Boolean isSold, Pageable pageable);

    @Query("{$and : [" +
            "{ $or : [ { $expr: { $eq : [?0, null] } } , { price : { $gt: ?0 } } ] }, " +
            "{ $or : [ { $expr: { $eq : [?1, null] } } , { price : { $gt: ?1 } } ] }, " +
            "{ $or : [ { $expr: { $eq : [?2, null] } } , { isSold : ?2 } ] }" +
            " ]}")
    Set<Advertisement> getAdvertisementsByParams(Integer minPrice, Integer maxPrice, Boolean isSold);

    @Query("{carId : {$in: ?0}}")
    Set<Advertisement> getAllByCarsIds(List<String> carId);

    @Query("{advertisementId : {$in: ?0}}")
    Page<Advertisement> getAllByAdvertisementsIds(List<String> advertisementId, Pageable pageable);
}
