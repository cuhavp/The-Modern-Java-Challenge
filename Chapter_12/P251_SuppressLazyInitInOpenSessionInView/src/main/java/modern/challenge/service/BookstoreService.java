package modern.challenge.service;

import modern.challenge.entity.Author;
import modern.challenge.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class BookstoreService {

    private final AuthorRepository authorRepository;

    public BookstoreService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author fetchAuthorWithBooks() {
        Author author = authorRepository.findByNameWithBooks("Name_2");

        return author;
    }

    public Author fetchAuthorWithoutBooks() {
        Author author = authorRepository.findByName("Name_2");

        // explicitly set Books of the Author to null
        // in order to avoid fetching them from the database
        author.setBooks(null);

        // or, to an empty collection
        // author.setBooks(Collections.emptyList());
        return author;
    }
}
