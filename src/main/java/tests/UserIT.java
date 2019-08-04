package tests;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import entities.User;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIT {
    private User user;

//    @BeforeClass
//    public void init() {
//        user = new User()
//    }

    @Test
    public void _1_getAllUsers() {
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");

        Response response  = client.target("http://localhost:8880/project/users").register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response.getStatus());
        List<User> users = response.readEntity(List.class);
        Assert.assertNotNull(users);
    }




}
