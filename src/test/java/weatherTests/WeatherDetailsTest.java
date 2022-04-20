package weatherTests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.Iterator;

public class WeatherDetailsTest extends BaseTest {

    String city = System.getProperties().getProperty("cityName");

    @Test
    public void getWeatherDetails(){
        System.out.println("thread -- " + Thread.currentThread().getId());
        Response response = getWeatherThread().getWeatherApiCity(city);

        JsonUtils utils = new JsonUtils();
        JsonArray jsonArray = utils.getValueArray(response, "forecast");

        Iterator iterator = jsonArray.iterator();

        while (iterator.hasNext()){
            JsonObject jsonObject = (JsonObject) iterator.next();
            if(jsonObject.get("day").isJsonNull() ? false : jsonObject.get("day").getAsString().equals("2")){
                Assert.assertTrue(jsonObject.get("wind").getAsString().contains("km/h"));
            }
        }

    }
}
