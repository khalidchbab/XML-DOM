package Library;

import Library.models.Book;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BookEditController {
    private int id;
    @FXML
    private Button modifyButton,deleteButton,back_field;

    @FXML
    private JFXTextField title_field,author_field,genre_field;
    private ObservableList<Book> booksData = FXCollections.observableArrayList();
    private Book book;
    public void editBook(ObservableList<Book> books, Book bk){
        title_field.setText(bk.getTitle());
        author_field.setText(bk.getAuthor());
        genre_field.setText(bk.getGenre());
        this.id=bk.getId();
        this.booksData=books;
        this.book=bk;
    }
    public void modifyBook(){
        book.setTitle(title_field.getText());
        book.setAuthor(author_field.getText());
        book.setGenre(genre_field.getText());
        System.out.println(book+" "+book.getTitle());
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }
    public void deleteBook(){
        booksData.remove(book);
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    void openLibrary(){
        try {
            Stage stage1 = (Stage) back_field.getScene().getWindow();
            stage1.hide();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/Book.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            System.out.println(e);
        }
    }


}
