import Gallery.GalleriesController;
import Library.BookController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    BookController bc;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new   FXMLLoader(getClass().getResource("/Library/views/Book.fxml"));
        Parent root = (Parent)loader.load();
        bc=loader.getController();
        primaryStage.setTitle("Book");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
