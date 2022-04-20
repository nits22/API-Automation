package bookStoreTests;

import clients.PostBooksClient;
import endpoints.books.PostBooksRequestBody;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PostBooksTest {

    @Test
    public void myTest(){
        PostBooksClient postBooksClient = new PostBooksClient();
        PostBooksRequestBody postBooksRequestBody = postBooksClient.createPostBookRequest("44525dda-8032-49e2-a859-630d86d0bf16", "9781449325862");
        Response response = postBooksClient.postBooksToUser(postBooksRequestBody);
        System.out.println(response.asString());
    }
}
