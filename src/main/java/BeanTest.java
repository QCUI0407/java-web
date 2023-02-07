import java.util.ArrayList;
import java.util.List;

public class BeanTest {
    public static void main(String[] args) {
        Album a1 = new Album(1, "Bad Bunny", "Trapeando", 2022, 3.4, "trap");
        Album a2 = new Album(2, "Taylor Swift", "21", 2022, 1.3, "pop");

        Author author1 = new Author(1, "Yogi", "Berra");
        Author author2 = new Author(2, "Jocko", "Willink");

        Quote q1 = new Quote(1, author1, "It's not over til it's over.");
        Quote q2 = new Quote(2, author1, "If the world was perfect, it wouldn't be.");
        Quote q3 = new Quote(3, author2, "Good.");

        List<Album> albumList = new ArrayList<>();
        albumList.add(a1);
        albumList.add(a2);


        List<Author> authorList = new ArrayList<>();
        authorList.add(author1);
        authorList.add(author2);

        List<Quote> quoteList = new ArrayList<>();
        quoteList.add(q1);
        quoteList.add(q2);
        quoteList.add(q3);

        for (Quote quote: quoteList) {
            System.out.println(quote.toString());
        }

        for (Album album: albumList) {
            System.out.println(album.toString());
        }
    }
}