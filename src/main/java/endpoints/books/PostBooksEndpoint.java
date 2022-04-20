package endpoints.books;

import entity.AuthType;
import entity.HttpMethod;
import entity.Param;
import entity.RequestBody;
import template.IServiceEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostBooksEndpoint implements IServiceEndpoint {
    PostBooksRequestBody postBooksRequestBody;
    public PostBooksEndpoint(PostBooksRequestBody postBooksRequestBody){
        this.postBooksRequestBody=postBooksRequestBody;
    }

    @Override
    public String url() {
        return "https://demoqa.com/BookStore/v1/Books";
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public List<Param> pathParam() {
        return null;
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
        return new RequestBody(PostBooksRequestBody.class, postBooksRequestBody);
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
