package endpoints.books;

import entity.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PostBooksResponse extends BaseResponse {

    public ArrayList<Book> books;

    public class Book{
        public String isbn;
    }
}
