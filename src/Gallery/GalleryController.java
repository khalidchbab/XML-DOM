package Gallery;

import Gallery.models.Contents;
import Gallery.models.Gallery;
import Library.models.Book;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GalleryController implements Initializable
{
    @FXML
    private JFXButton validateButton,backButton;
    @FXML
    private JFXTextField manager,address,type,title,price,gallary_address,gallary_manager,gallary_id,author;
    @FXML
    private TableColumn<Gallery,String> table_title;
    @FXML
    private TableColumn<Gallery,String> table_type;
    @FXML
    private TableColumn<Gallery,String> table_author;
    @FXML
    private TableColumn<Gallery,String> table_price;
    @FXML
    private TableColumn<Gallery,Integer> table_content_id;

    @FXML
    private ObservableList<Gallery> galleries= FXCollections.observableArrayList();
    private Gallery gallery;
    @FXML
    TableView<Contents> content_table;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void fill(ObservableList<Gallery> galleries, Gallery gl) throws Exception{
        this.gallery=gl;
        System.out.println("loooool "+gallery);
        gallary_manager.setText(gl.getManager());
        gallary_id.setText(Integer.toString(gl.getId()));
        gallary_address.setText(gl.getAddress());
        populateTable();
    }

    public void add(ObservableList<Gallery> galleries) throws Exception{
        backButton.setDisable(true);
        validateButton.setDisable(true);
        this.galleries=galleries;
    }

    protected void populateTable() throws Exception{
        System.out.println("populate");
        System.out.println();
        table_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        table_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_content_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        System.out.println(gallery.getContent());
        content_table.setItems(gallery.getContent());
        content_table.setEditable(true);
    }
    @FXML
    protected void addGallery(){
        Gallery bk=new Gallery(galleries.size()+1,address.getText(),manager.getText());
        Contents c=new Contents(0,bk.getId(),type.getText(),author.getText());
        bk.getContent().add(c);
        galleries.add(bk);
        Stage stage1 = (Stage) type.getScene().getWindow();
        stage1.close();

    }



}


