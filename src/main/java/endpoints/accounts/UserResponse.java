package endpoints.accounts;

import entity.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserResponse extends BaseResponse {
    public String userId;
    public String username;
    public ArrayList<Book> books;
}

@Getter
@Setter
class Book{
    public String isbn;
    public String title;
    public String subTitle;
    public String author;
    public String publish_date;
    public String publisher;
    public int pages;
    public String description;
    public String website;
}