package tests;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import entities.User;
import objects.Delete_Undo;
import objects.PasswordReset;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIT {
    private static User newUser;
    private static Delete_Undo delUndo;
    private static PasswordReset passwordReset;


    @BeforeClass
    public static void init() {
        newUser = new User("Dina", "dina", "dina@gmail.com", "user", false);
        passwordReset = new PasswordReset(newUser.getPassword(), "pass");
    }

    @Test
    public void _01_getAllUsers() {
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");

        Response response  = client.target("http://localhost:8880/project/users").register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response.getStatus());
        List<User> users = response.readEntity(List.class);
        Assert.assertNotNull(users);
    }


    @Test
    public void _02_getUserByUsername() {
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");

        Response response  = client.target("http://localhost:8880/project/users/find").queryParam("username", "Salma").register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response.getStatus());
        User user = response.readEntity(User.class);
        Assert.assertNotNull(user);
    }



    @Test
    public void _03_addUser() {
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");

        Response response  = client.target("http://localhost:8880/project/users/add").register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newUser,MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void _04_deleteUser() {
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");

        Response response  = client.target("http://localhost:8880/project/users/delete/"+newUser.getUsername()).register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        Assert.assertEquals(200, response.getStatus());


        Response response2  = client.target("http://localhost:8880/project/users/find").queryParam("username", newUser.getUsername()).register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response2.getStatus());
        User user = response2.readEntity(User.class);

        Assert.assertEquals(true, user.isDelete());
    }

    @Test
    public void _05_undoDeleteUser() {
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");

        Response response  = client.target("http://localhost:8880/project/users/undo/"+newUser.getUsername()).register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(newUser.getUsername(),MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());


        Response response2  = client.target("http://localhost:8880/project/users/find").queryParam("username", newUser.getUsername()).register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response2.getStatus());
        User user = response2.readEntity(User.class);

        Assert.assertEquals(false, user.isDelete());
    }



    @Test
    public void _06_resetPassword() {
        String oldPass = newUser.getPassword();
        Client client= ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("Ahmed", "ahmed");
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);

        Response response  = client.target("http://localhost:8880/project/users/reset").register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(passwordReset,MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());

        Response response2  = client.target("http://localhost:8880/project/users/find").queryParam("username", newUser.getUsername()).register(feature).register(JacksonJsonProvider.class)
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response2.getStatus());
        User user = response2.readEntity(User.class);
        Assert.assertNotEquals(oldPass, user.getPassword());

    }

}
