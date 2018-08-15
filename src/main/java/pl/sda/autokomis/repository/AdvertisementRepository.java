package pl.sda.autokomis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.sda.autokomis.model.Advertisement;

import java.util.List;


@Repository
public interface AdvertisementRepository extends MongoRepository<Advertisement, String>{

    Advertisement getAdvertisementByAdvertisementId(String advertisementId);
    Page<Advertisement> getAllBy(Pageable pageable);
    List<Advertisement> getAllBy();
    Page<Advertisement> getAllByUserId(String userId, Pageable pageable);
    Page<Advertisement> getAdvertisementsByIsSold(Boolean isSold, Pageable pageable);
}
