package pl.sda.autokomis.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "pl.sda.autokomis")
public class MongoConfiguration extends AbstractMongoConfiguration{

    @Value("${configure.mongodb.port:19123}")
    private Integer mongoPort;

    @Value("${configure.mongodb.host:ds019123.mlab.com}")
    private String mongoHost;

    @Value("${configure.mongodb.dbname:sda}")
    private String mongoDBName;

    @Value("${configure.mongodb.user:root}")
    private String user;

    @Value("${configure.mongodb.password:admin123}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return mongoDBName;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoCredential m = MongoCredential.createCredential(user, mongoDBName, password.toCharArray());
        List<ServerAddress> addresses = new ArrayList<>();
        addresses.add(new ServerAddress(mongoHost + ":" + mongoPort));
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(m);

        return new MongoClient(addresses, credentials);
    }
}
