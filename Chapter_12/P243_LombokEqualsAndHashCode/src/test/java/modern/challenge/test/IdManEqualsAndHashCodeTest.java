package modern.challenge.test;

import java.util.HashSet;
import java.util.Set;
import modern.challenge.IdManBook;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IdManEqualsAndHashCodeTest {

    @Autowired
    private TestEntityManager entityManager;

    private static final IdManBook book = new IdManBook();
    private static final Set<IdManBook> books = new HashSet<>();

    @BeforeClass
    public static void setUp() {
        book.setId(1L);
        book.setTitle("Modern History");
        book.setIsbn("001-100-000-111");

        books.add(book);
    }

    @Test
    // Find the Book that has never been persisted
    // Transition state at assert point: NEW
    public void A_givenBookInSetWhenContainsThenTrue() throws Exception {

        assertTrue(books.contains(book));
    }

    @Test
    // Find the Book after persist
    // Transition state at first assert point: NEW
    // Transition state at second and third assert point: MANAGED
    public void B_givenBookWhenPersistThenSuccess() throws Exception {

        assertNotNull(book.getId());

        entityManager.persistAndFlush(book);
        assertNotNull(book.getId());

        assertTrue(books.contains(book));
    }

    @Test
    // Find the Book after a merge() - UPDATE statement   
    // Transition state at assert point: MANAGED
    public void C_givenBookWhenMergeThenSuccess() throws Exception {

        book.setTitle("New Modern History");
        IdManBook mergedBook = entityManager.merge(book);
        entityManager.flush();

        assertTrue(books.contains(mergedBook));
    }

    @Test
    // Find the Book after a find() - SELECT statement    
    // Transition state at second assert point: MANAGED
    public void D_givenBookWhenFindThenSuccess() throws Exception {

        IdManBook foundBook = entityManager.find(IdManBook.class, book.getId());
        entityManager.flush();

        assertTrue(books.contains(foundBook));
    }

    @Test
    // Find the Book after an explicit detach
    // Transition state at assert point: DETACHED    
    public void E_givenBookWhenFindAndDetachThenSuccess() throws Exception {

        IdManBook foundBook = entityManager.find(IdManBook.class, book.getId());
        entityManager.detach(foundBook);

        assertTrue(books.contains(foundBook));
    }

    @Test
    // Find the Book after a remove() - DELETE statement
    // Transition state at assert points: REMOVED    
    public void F_givenBookWhenFindAndRemoveThenSuccess() throws Exception {

        IdManBook foundBook = entityManager.find(IdManBook.class, book.getId());
        entityManager.remove(foundBook);
        entityManager.flush();

        assertTrue(books.contains(foundBook));

        books.remove(foundBook);

        assertFalse(books.contains(foundBook));
    }

}
