package crud.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import crud.dao.Book;
import crud.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("author")
public class AuthorView extends VerticalLayout implements HasUrlParameter<String> {



    final private BookRepository repository;

    final private Grid<Book> grid;
    Label authorLabel = new Label();

    @Autowired
    public AuthorView(BookRepository repository) {
        this.repository = repository;
        add(authorLabel);
        grid = new Grid<>(Book.class);
        grid.setColumns("title", "publisher", "year");
        grid.setSortableColumns("publisher", "year");
        grid.setMultiSort(true);
        add(grid);

    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        List<Book>  books = repository.findByAuthor(parameter);
        if (!books.isEmpty()) {
            authorLabel.setText(books.get(0).getAuthor());
        }
        else {
            authorLabel.setText(parameter + " not found");
            grid.setVisible(false);
        }
        grid.setItems(books);
    }
}
