package crud.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import crud.dao.Book;
import crud.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class BookEditor extends VerticalLayout implements KeyNotifier {

    private final List<String> genders = new ArrayList();
    private final BookRepository repository;
    private Book book;

    private final TextField title = new TextField("Title");
    private final TextField author = new TextField("Author");
    private final TextField publisher = new TextField("Publisher");

    private final TextField yearField = new TextField("Year");
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private NativeButton toAuthorPage = new NativeButton("Author page");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout action = new HorizontalLayout(save, cancel, delete);

    Binder<Book> binder = new Binder<>(Book.class);


    private ChangeHandler changeHandler;

    @Autowired
    public BookEditor(BookRepository repository) {
        this.repository = repository;

        HorizontalLayout fields = new HorizontalLayout(title, author, publisher, yearField);
        add(fields, action, toAuthorPage);

        binder.bindInstanceFields(this);
        setSpacing(true);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        toAuthorPage.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editBook(book));
        setVisible(false);
    }

    public void editBook(Book book) {
        if (book == null) {
            setVisible(false);
            return;
        }
        if (book.getYear() == null)
            yearField.setValue("");
        else
            yearField.setValue(String.valueOf(book.getYear()));
        final boolean percisted = book.getId() != null;
        if (percisted) {
            this.book = repository.findById(book.getId()).get();
        } else {
            this.book = book;
        }
        toAuthorPage.addClickListener(e -> toAuthorPage.getUI().ifPresent(ui -> ui.navigate("author/" + this.book.getAuthor())));
        cancel.setVisible(percisted);
        toAuthorPage.setVisible(percisted);
        binder.setBean(this.book);
        setVisible(true);
        title.focus();
    }

    void delete() {
        repository.delete(book);
        changeHandler.onChange();
    }

    void save() {
        try {
            this.book.setYear(Integer.parseInt(yearField.getValue()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        repository.save(book);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
