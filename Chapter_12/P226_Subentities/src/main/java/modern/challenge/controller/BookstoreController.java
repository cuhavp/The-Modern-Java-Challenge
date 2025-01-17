package modern.challenge.controller;

import modern.challenge.entity.AuthorDeep;
import modern.challenge.entity.AuthorShallow;
import modern.challenge.service.BookstoreService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookstoreController {

    private final BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping("/authors/shallow")
    public List<AuthorShallow> fetchAuthorsShallow() {
        return bookstoreService.fetchAuthorsShallow();
    }

    @GetMapping("/authors/deep")
    public List<AuthorDeep> fetchAuthorsDeep() {
        return bookstoreService.fetchAuthorsDeep();
    }
}
