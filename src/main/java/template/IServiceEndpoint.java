package template;

import entity.AuthType;
import entity.HttpMethod;
import entity.Param;
import entity.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IServiceEndpoint {
    String url();

    HttpMethod httpMethod();

    List<Param> pathParam();

    List<Param> queryParam();

    List<Param> headers();

    default Map<Object, Object> auth(){
        Map<Object, Object> map = new HashMap<>();
        map.put("type", AuthType.NONE);
        return map;
    }

    default List<Param> formParam(){
        return null;
    }
    default List<String> sslConfig() {
        return null;
    }

    RequestBody body();


}
