package modern.challenge.service;

import java.util.ArrayList;
import java.util.List;
import modern.challenge.entity.Author;
import modern.challenge.entity.Book;
import modern.challenge.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class BookstoreService {

    private final AuthorRepository authorRepository;

    public BookstoreService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void batchAuthorsAndBooks() {

        List<Author> authors = new ArrayList<>();

        long pk = -1;
        for (int i = 0; i < 40; i++) {

            Author author = new Author();
            author.setId((long) i + 1);
            author.setName("Name_" + i);
            author.setSurname("Surname_" + i);
            author.setAge(18 + i);

            for (int j = 0; j < 5; j++) {
                Book book = new Book();
                book.setId(++pk);
                book.setTitle("Title: " + j);
                book.setIsbn("Isbn: " + j);

                author.addBook(book);
            }

            authors.add(author);
        }

        authorRepository.saveInBatch(authors);
    }
}
