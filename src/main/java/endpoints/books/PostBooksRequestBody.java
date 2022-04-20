package endpoints.books;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class PostBooksRequestBody {

    public String userId;
    public CollectionOfIsbn[] collectionOfIsbns;

    public static class CollectionOfIsbn{
        public String isbn;
    }
}
