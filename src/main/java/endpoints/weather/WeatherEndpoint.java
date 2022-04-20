package endpoints.weather;

import entity.HttpMethod;
import entity.Param;
import entity.RequestBody;
import template.IServiceEndpoint;

import java.util.ArrayList;
import java.util.List;

public class WeatherEndpoint implements IServiceEndpoint {
    String city;
    public WeatherEndpoint(String city){
        this.city=city;
    }

    @Override
    public String url() {
        return "https://goweather.herokuapp.com/weather/{city}";
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public List<Param> pathParam() {
        List<Param> list = new ArrayList<>();
        list.add(new Param("city", city));
        return list;
    }

    @Override
    public List<Param> queryParam() {
        return null;
    }

    @Override
    public List<Param> headers() {
        return null;
    }

    @Override
    public RequestBody body() {
        return null;
    }
}
