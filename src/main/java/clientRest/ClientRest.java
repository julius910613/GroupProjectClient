package clientRest;

import entity.User;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.glassfish.jersey.jackson.JacksonFeature;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

/**
 * Created by xwen on 3/12/14.
 */
public class ClientRest {

    private static final String REST_SERVICE_URL_USER = "http://localhost:8080/userService/registerUser";
    private static final String REST_SERVICE_URL="http://localhost:8080/userService";
    Client client = ClientBuilder.newClient().register(JacksonFeature.class);

    public void addUser(User user) {
          if(user.equals(client.target(REST_SERVICE_URL).path("/{userEmailAddress}").resolveTemplate("userEmailAddress", user.getUserEmailAddress()).request().get(User.class)))
          {
              //client.target(REST_SERVICE_URL).request().post(Entity.entity())
              System.out.println("Already exits");
          }else {
              //User.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
         client.target(REST_SERVICE_URL_USER).request().post(Entity.entity(user, MediaType.APPLICATION_JSON), User.class);

        System.out.println("User has been registered");
          }
    }

    public boolean isExist(String email) {

        User user = client.target(REST_SERVICE_URL).path("/{userEmailAddress}").resolveTemplate("userEmailAddress", email).request().get(User.class);
        if(user==null){
            return false;
        }else {
            return true;
        }
    }



}
