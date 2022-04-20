package restUtils;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import entity.AuthType;
import entity.HttpMethod;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import template.IServiceEndpoint;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestProcessor {

    public Response processRequest(IServiceEndpoint iServiceEndpoint){
        String endpoint = iServiceEndpoint.getClass().getSimpleName().replace("endpoint", "");

        Response response = processIserviceEndpoint(iServiceEndpoint);

        printResponse(response, endpoint);

        return response;
    }

    private void printResponse(Response response, String endpoint) {
        Reporter.log(String.format(" Response status code is --- %s", response.getStatusCode()), true);
        Reporter.log(String.format(" %s Response is : %s", endpoint, response.asString()), true);
        printResponseHeaders(endpoint, response);
    }

    private void printResponseHeaders(String endpointName, Response response) {
        String responseHeaders = response.headers().toString();
        Reporter.log(String.format("%s Response headers --- \n%s", endpointName, responseHeaders), true);
    }

    private Response processIserviceEndpoint(IServiceEndpoint iServiceEndpoint) {
        RestAssured.registerParser("application/grpc", Parser.JSON);
        RestAssured.registerParser("text/html", Parser.JSON);
        RestAssured.registerParser("text/plain", Parser.JSON);

        RequestSpecification requestSpecification = formRequestSpecification(iServiceEndpoint);

        String endpoint = iServiceEndpoint.getClass().getSimpleName().replaceAll("Endpoint", "");

        String url = iServiceEndpoint.url();

        HttpMethod httpMethod = iServiceEndpoint.httpMethod();

        Response response = makeApiRequest(url, httpMethod, requestSpecification);

        return response;
    }

    private RequestSpecification formRequestSpecification(IServiceEndpoint iServiceEndpoint) {
        RestAssuredConfig config = RestAssured.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        config = CurlLoggingRestAssuredConfigFactory.updateConfig(config);
        RequestSpecification requestSpecification = given().config(config);

        if(iServiceEndpoint.headers() != null){
            iServiceEndpoint.headers().forEach(p -> requestSpecification.header(p.getKey(), p.getValue()));
        }

        if(iServiceEndpoint.pathParam() != null){
            iServiceEndpoint.pathParam().forEach(p -> requestSpecification.pathParam(p.getKey(), p.getValue()));
        }

        if(iServiceEndpoint.queryParam() != null){
            iServiceEndpoint.queryParam().forEach(p -> requestSpecification.queryParam(p.getKey(), p.getValue()));
        }

        if(iServiceEndpoint.body() != null){
            requestSpecification.body(iServiceEndpoint.body().getBodyString());
        }

        Map<Object, Object> auth = iServiceEndpoint.auth();
        Object authType = auth.get("type");
        if (!authType.equals(AuthType.NONE)) {
            if (authType.equals(AuthType.BASIC)) {
                requestSpecification.auth().preemptive().basic(auth.get("username").toString(), auth.get("password").toString());
            }
            if (authType.equals(AuthType.BEARER)) {
                requestSpecification.auth().oauth2(auth.get("bearerToken").toString());
            }
        }

        if (iServiceEndpoint.formParam() != null)
            iServiceEndpoint.formParam().forEach(p -> requestSpecification.formParam(p.getKey(), p.getValue()));


        return requestSpecification;
    }


    private Response makeApiRequest(String url, HttpMethod httpMethod, RequestSpecification requestSpecification) {
        Response response = null;
        switch (httpMethod){
            case GET:
                response = requestSpecification.get(url);
                break;
            case POST:
                response = requestSpecification.post(url);
                break;
            case PUT:
                response = requestSpecification.put(url);
                break;
        }
        return response;
    }
}
