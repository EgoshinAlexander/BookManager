package crud.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import crud.dao.Book;
import crud.dao.BookRepository;

import java.util.List;

@Route()
public class MainView extends VerticalLayout {
    private final BookRepository repository;
    private final BookEditor editor;

    final Grid<Book> grid;
    final Button addNewCustomerButton;

    public MainView(BookRepository repository, BookEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(Book.class);
        this.addNewCustomerButton = new Button("New book", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(addNewCustomerButton);
        setSpacing(true);

        grid.setColumns("id", "title", "author", "publisher", "year");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.setHeight("300px");

        add(actions, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editBook(e.getValue());
        });

        addNewCustomerButton.addClickListener(e -> editor.editBook(new Book()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listBooks();
        });
        listBooks();
    }

    private void listBooks() {
        List<Book> listBooks = repository.findAll();
        grid.setItems(listBooks);
    }
}