package crud;

import crud.dao.Book;
import crud.dao.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(BookRepository repository) {
        return (args) -> {
            repository.save(new Book("The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin Harcourt", 2013));
            repository.save(new Book("The Fellowship of the Ring", "J. R. R. Tolkien", "Houghton Mifflin Harcourt", 2012));
            repository.save(new Book("The Two Towers", "J. R. R. Tolkien", "Houghton Mifflin Harcourt", 2012));
            repository.save(new Book("The Return of the King", "J. R. R. Tolkien", "Houghton Mifflin Harcourt", 2012));
            repository.save(new Book("The Green Mile", "Stephen King", "Pocket Books" , 2017));
            repository.save(new Book("The Shining", "Stephen King", "Anchor" , 2012));
        };
    }
}