package accountsTests;

import clients.UserClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import endpoints.accounts.UserResponse;
import entity.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.Iterator;

public class UserDetailsTest extends BaseTest {

    @Test
    public void getUserDetails() {
        System.out.println("thread -- " + Thread.currentThread().getId());
        Response response = new UserClient().getUserDetails("44525dda-8032-49e2-a859-630d86d0bf16");
        //System.out.println(response.jsonPath().get("userId").toString());

        UserResponse userResponse = response.as(UserResponse.class);

        System.out.println(userResponse.userId);

        JsonUtils utils = new JsonUtils();
        JsonArray jsonArray = utils.getValueArray(response, "books");

        Iterator iterator = jsonArray.iterator();

        while(iterator.hasNext()){
            JsonObject object = (JsonObject) iterator.next();
            System.out.println(object.get("isbn").getAsString());
        }

    }
}