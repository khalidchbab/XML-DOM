package Library;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Library.models.Book;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.*;


public class BookController implements Initializable {
    private ObservableList<Book> booksData = FXCollections.observableArrayList();
    ArrayList<Book> books = new ArrayList<Book>();
    @FXML
    private Button gallery,back_field;
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book,String> table_title;
    @FXML
    private TableColumn<Book,String> table_author;
    @FXML
    private TableColumn<Book,String> table_genre;
    @FXML
    private TableColumn<Book,Integer> table_id;
    @FXML
    private JFXTextField title_field;
    @FXML
    private JFXTextField author_field;
    @FXML
    private JFXTextField genre_field;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("this is init");
        booksData = FXCollections.observableArrayList();
        try{
            booksData=loadBooks();
            emptyFile();
            populateTable();
            System.out.println(booksData);
        }catch (Exception e){
            System.out.println(e);
        }


    }
    @FXML
    protected void addBook(){
        Book bk=new Book(Book.getCount()+1,
                title_field.getText(),
                author_field.getText(),
                genre_field.getText());
        booksData.add(bk);
    }
    @FXML
    void openGallery(){
        try {
            saveBooksWithoutClosing();
            Stage stage1 = (Stage) gallery.getScene().getWindow();
            stage1.hide();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gallery/views/Galleries.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Galleries");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            System.out.println(e);
        }catch (Exception e){
            System.out.println(e);
        }
}

    protected void populateTable() throws Exception{
        System.out.println("populate");
        table_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        table_author.setCellValueFactory(new PropertyValueFactory<>("Author"));
        table_genre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.setItems(booksData);
        table.setEditable(true);

        doubleClick();
    }

    void doubleClick(){
        table.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                if(event.getClickCount()>1)
                {
                    Book bk = table.getSelectionModel().getSelectedItem();

                    System.out.println(table.getSelectionModel().getSelectedItem().getId());
                    try
                    {
                        FXMLLoader loader = new   FXMLLoader(getClass().getResource("views/BookEdit.fxml"));
                        Parent root = (Parent)loader.load();
                        BookEditController bookEditController = loader.getController();
                        bookEditController.editBook(booksData,bk);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                        if(!stage.isShowing()) {
                            try{
                                refreshTable();
                            }catch (Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                    catch(IOException e)
                    {
                        System.out.println(e);
                    }
                }
            }
        });
    }

    void refreshTable(){
        table.getColumns().get(0).setVisible(false);
        table.getColumns().get(0).setVisible(true);
    }

    @FXML
    ObservableList<Book> loadBooks() throws Exception {
        System.out.println("load");
        File fXmlFile = new File("src/Library/books.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("book");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                booksData.add(new Book(Integer.parseInt(eElement.getAttribute("id")),eElement.getElementsByTagName("title").item(0).getTextContent(),eElement.getElementsByTagName("author").item(0).getTextContent(),eElement.getElementsByTagName("genre").item(0).getTextContent()));
            }
        }
        for (Book bk:books) {
            System.out.println(bk.getTitle());
        }
        emptyFile();
        return booksData;
    }

    public static void emptyFile() throws Exception{
        System.out.println("empty");
        File fXmlFile = new File("src/Library/books.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        Node nList = doc.getElementsByTagName("library").item(0);
        NodeList list = nList.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if ("book".equals(node.getNodeName())) {
                nList.removeChild(node);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/Library/books.xml"));
        transformer.transform(source, result);

    }

    public void saveBooks() throws Exception{
        System.out.println("saving");
        emptyFile();

        File fXmlFile = new File("src/Library/books.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        Node library = doc.getFirstChild();
        System.out.println(booksData.size());
        for (Book bk:booksData) {
            System.out.println(bk.getTitle()+"ddddd");
            Element book = doc.createElement("book");

            library.appendChild(book);

            String id = Integer.toString(bk.getId());
            book.setAttribute("id", id);

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(bk.getTitle()));
            book.appendChild(title);

            Element author = doc.createElement("author");
            author.appendChild(doc.createTextNode(bk.getAuthor()));
            book.appendChild(author);

            Element genre = doc.createElement("genre");
            genre.appendChild(doc.createTextNode(bk.getGenre()));
            book.appendChild(genre);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/Library/books.xml"));
        transformer.transform(source, result);
        System.exit(0);
    }

    public void saveBooksWithoutClosing() throws Exception{
        System.out.println("saving");
        emptyFile();

        File fXmlFile = new File("src/Library/books.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        Node library = doc.getFirstChild();
        System.out.println(booksData.size());
        for (Book bk:booksData) {
            System.out.println(bk.getTitle()+"ddddd");
            Element book = doc.createElement("book");

            library.appendChild(book);

            String id = Integer.toString(bk.getId());
            book.setAttribute("id", id);

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(bk.getTitle()));
            book.appendChild(title);

            Element author = doc.createElement("author");
            author.appendChild(doc.createTextNode(bk.getAuthor()));
            book.appendChild(author);

            Element genre = doc.createElement("genre");
            genre.appendChild(doc.createTextNode(bk.getGenre()));
            book.appendChild(genre);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/Library/books.xml"));
        transformer.transform(source, result);
    }
}
