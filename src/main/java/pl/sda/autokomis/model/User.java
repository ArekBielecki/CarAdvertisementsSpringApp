package pl.sda.autokomis.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String userId;

    private String username;

    private String password;

    private List<String> listOfAdvertisementsIds;
}
