package utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static String getJsonString(Object objectInstance) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(objectInstance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
