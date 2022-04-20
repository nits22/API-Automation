package clients;

import endpoints.accounts.UserEndpoint;
import endpoints.weather.WeatherEndpoint;
import io.restassured.response.Response;
import restUtils.RequestProcessor;

public class UserClient {

    public Response getUserDetails(String udid){
        UserEndpoint userEndpoint = new UserEndpoint(udid);
        Response response = new RequestProcessor().processRequest(userEndpoint);
        return response;
    }

}
