package endpoints.accounts;

import entity.AuthType;
import entity.HttpMethod;
import entity.Param;
import entity.RequestBody;
import template.IServiceEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserEndpoint implements IServiceEndpoint {
    String udid;
    public UserEndpoint(String udid){
        this.udid=udid;
    }

    @Override
    public String url() {
        return "https://demoqa.com/Account/v1/User/{udid}";
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public List<Param> pathParam() {
        List<Param> list = new ArrayList<>();
        list.add(new Param("udid", udid));
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

    @Override
    public Map<Object, Object> auth() {
        Map<Object, Object> map = new HashMap<>();
        map.put("type", AuthType.BASIC);
        map.put("username", "test001");
        map.put("password", "Test@1234");
        return map;
    }
}
