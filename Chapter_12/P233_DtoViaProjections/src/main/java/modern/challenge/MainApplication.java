package modern.challenge;

import modern.challenge.projection.AuthorNameAge;
import java.util.List;
import modern.challenge.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            List<AuthorNameAge> authors = bookstoreService.fetchFirst2ByBirthplace();

            System.out.println("Number of authors:" + authors.size());

            for (AuthorNameAge author : authors) {
                System.out.println("Author name: " + author.getName() 
                        + " | Age: " + author.getAge());
            }
        };
    }
}
