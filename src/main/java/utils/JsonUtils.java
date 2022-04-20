package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtils {

    public JsonArray getValueArray(Response response, String key){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response.asString());
        JsonObject object = element.getAsJsonObject();
        JsonArray jsonArray = object.getAsJsonArray(key);
        return jsonArray;
    }

    public static String getValuee(String constantKey, String filename){
        try {
            JSONParser jsonParser = new JSONParser();

            JSONObject object = (JSONObject) jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/resources/" + "file.json"));
            System.out.println(object.get("Author"));
            JSONArray array = (JSONArray) object.get(constantKey);
            return array.toJSONString();
        }
        catch (Exception e){

        }
        return null;
    }

    private static File jsonFile;
    public static String getValue(String path) throws IOException {
        String temp = "";
        try {
            jsonFile=new File(System.getProperty("user.dir") + "/src/main/resources/" + "file.json");
            temp= JsonPath.read(jsonFile,"$"+path).toString(); //JsonPath.read(jsonFile,"$['Company List']");
            return temp;
        }
        catch(Exception e) {

        }
        return temp;

    }


    public static void main(String[] args) throws IOException {
        //System.out.println(getValuee("Company List", ""));
        System.out.println(getValue("['Company List']"));
    }
}
