package clients;

import endpoints.accounts.UserEndpoint;
import endpoints.books.PostBooksEndpoint;
import endpoints.books.PostBooksRequestBody;
import endpoints.books.PostBooksResponse;
import io.restassured.response.Response;
import restUtils.RequestProcessor;

import java.util.ArrayList;

public class PostBooksClient {
    public Response postBooksToUser(PostBooksRequestBody postBooksRequestBody){
        PostBooksEndpoint postBooksEndpoint = new PostBooksEndpoint(postBooksRequestBody);
        Response response = new RequestProcessor().processRequest(postBooksEndpoint);
        return response;
    }

    public PostBooksRequestBody createPostBookRequest(String userID, String isbn){
        PostBooksRequestBody.CollectionOfIsbn[] list = new PostBooksRequestBody.CollectionOfIsbn[1];
        PostBooksRequestBody.CollectionOfIsbn collectionOfIsbn = new PostBooksRequestBody.CollectionOfIsbn();
        collectionOfIsbn.isbn = isbn;
        list[0] = collectionOfIsbn;
        PostBooksRequestBody postBooksRequestBody = PostBooksRequestBody.builder().userId(userID).collectionOfIsbns(list).build();
        return postBooksRequestBody;
    }
}
