package restUtils;

import entity.AuthType;
import entity.HttpMethod;
import entity.RequestBody;
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

public class RequestHandler {


    public Response processRequest(IServiceEndpoint iServiceEndpoint) {
        Response response = processIServiceEndpoint(iServiceEndpoint);
        String endpointName = iServiceEndpoint.getClass().getSimpleName().replaceAll("Endpoint", "");
        String noOfRetries = System.getProperty("noOfRetries");
        int retries = (noOfRetries == null || noOfRetries.isEmpty()) ? 0 : Integer.parseInt(noOfRetries);
        for (int i = 0; i < retries && isResponse5xx(response); i++) {
            Reporter.log(String.format("\n%s Response Status Code --- %d --> Retrying again --> Retry no - %d", endpointName, response.getStatusCode(), i + 1), true);

            response = processIServiceEndpoint(iServiceEndpoint);
        }
        return response;
    }


    private Response processIServiceEndpoint(IServiceEndpoint iServiceEndpoint) {
        RestAssured.registerParser("text/plain", Parser.JSON);
        RestAssured.registerParser("application/grpc", Parser.JSON);
        RestAssured.registerParser("text/html", Parser.JSON);

        String endpointName = iServiceEndpoint.getClass().getSimpleName().replaceAll("Endpoint", "");
        String url = iServiceEndpoint.url();
        HttpMethod httpMethod = iServiceEndpoint.httpMethod();
        RequestBody body = iServiceEndpoint.body();

        RequestSpecification requestSpecification = formRequestSpecification(iServiceEndpoint);

        //logRequestDetailsWithCurl(iServiceEndpoint, endpointName, url, httpMethod, body);
        Response response = makeAPIRequestAsPerHTTPMethod(url, httpMethod, requestSpecification);
        printResponseDetails(iServiceEndpoint, endpointName, response);

        //printCurlForServerErrors(iServiceEndpoint, url, response);

        return response;
    }

    private boolean isResponse5xx(Response response) {
        return (response.getStatusCode() >= 500) && (response.getStatusCode() < 505);
    }


    private RequestSpecification formRequestSpecification(IServiceEndpoint iServiceEndpoint) {
        RestAssuredConfig config = RestAssured.config()
                .encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        RequestSpecification request = given().config(config);

        if (iServiceEndpoint.headers() != null) {
            iServiceEndpoint.headers().forEach(h -> request.header(h.getKey(), h.getValue()));
        }

        if (iServiceEndpoint.queryParam() != null) {
            iServiceEndpoint.queryParam().forEach(q -> request.queryParam(q.getKey(), q.getValue()));
        }

        if (iServiceEndpoint.pathParam() != null) {
            iServiceEndpoint.pathParam().forEach(p -> request.pathParam(p.getKey(), p.getValue()));
        }

        if (iServiceEndpoint.body() != null)
            request.body(iServiceEndpoint.body().getBodyString());

        Map<Object, Object> auth = iServiceEndpoint.auth();
        Object authType = auth.get("type");
        if (!authType.equals(AuthType.NONE)) {
            if (authType.equals(AuthType.BASIC)) {
                request.auth().basic(auth.get("username").toString(), auth.get("password").toString());
            }
            if (authType.equals(AuthType.BEARER)) {
                request.auth().oauth2(auth.get("bearerToken").toString());
            }
        }

        if (iServiceEndpoint.formParam() != null)
            iServiceEndpoint.formParam().forEach(p -> request.formParam(p.getKey(), p.getValue()));

        return request;
    }


    private Response makeAPIRequestAsPerHTTPMethod(String url, HttpMethod httpMethod, RequestSpecification requestSpecification) {
        Response response = null;
        switch (httpMethod) {
            case GET:
                response = requestSpecification.get(url);
                break;
            case POST:
                response = requestSpecification.post(url);
                break;
            case PUT:
                response = requestSpecification.put(url);
                break;
            case PATCH:
                response = requestSpecification.patch(url);
                break;
            case DELETE:
                response = requestSpecification.delete(url);
        }

        return response;
    }

    private void printResponseHeaders(String endpointName, Response response) {
        String responseHeaders = response.headers().toString();
        Reporter.log(String.format("%s Response headers --- \n%s", endpointName, responseHeaders), true);
    }

    private void printResponseDetails(IServiceEndpoint iServiceEndpoint, String endpointName, Response response) {
        Reporter.log(String.format(endpointName + " Response Status Code --- (%s)", response.getStatusCode()), true);

        Reporter.log(String.format(endpointName + " Response --- %s", response.asString()), true);
        printResponseHeaders(endpointName, response);
    }

}