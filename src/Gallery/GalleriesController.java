package Gallery;

import Gallery.models.Contents;
import Gallery.models.Gallery;
import Library.BookEditController;
import Library.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GalleriesController implements Initializable
{
    private ObservableList<Gallery> galleries = FXCollections.observableArrayList();
    @FXML
    private TableView<Gallery> table;
    @FXML
    private TableColumn<Gallery,String> table_manager;
    @FXML
    private TableColumn<Gallery,Integer> table_id;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            loadContent();
            emptyFile();
            populateTable();
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println(galleries.get(1).getContent().get(0).getPrice()+ " sizzza");
    }
    @FXML
    public void loadContent() throws Exception{
        System.out.println("load");
        File fXmlFile = new File("src/Gallery/galleries.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("gallery");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Gallery g = new Gallery();
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                NodeList node = eElement.getElementsByTagName("contents");
                g.setManager(eElement.getElementsByTagName("manager").item(0).getTextContent());
                g.setId(Integer.parseInt(eElement.getAttribute("id")));
                g.setAddress(eElement.getAttribute("address"));
                System.out.println(node.getLength()+" node lenght");
                for (int temp2 = 0; temp2 < node.getLength(); temp2++) {
                    Contents c=new Contents();
                    Node nNode2 = node.item(temp2);
                    if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement2 = (Element) nNode2;
                        c.setId(Integer.parseInt(eElement2.getAttribute("id")));
                        c.setGallery_id(g.getId());
                        c.setType(eElement2.getElementsByTagName("type").item(0).getTextContent());
                        c.setAuthor(eElement2.getElementsByTagName("author").item(0).getTextContent());
                        c.setPrice(Double.parseDouble(eElement2.getElementsByTagName("price").item(0).getTextContent()));
                        c.setTitle(eElement2.getElementsByTagName("title").item(0).getTextContent());
                    }
                    System.out.println(c.getPrice());
                    g.getContent().add(c);
                }
                System.out.println("dqdd"+ g.getContent().size());
            }
            galleries.add(g);
        }
    }

    public void emptyFile() throws Exception{
        System.out.println("empty");
        File fXmlFile = new File("src/Gallery/galleries.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        Node nList = doc.getElementsByTagName("galleries").item(0);
        NodeList list = nList.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if ("gallery".equals(node.getNodeName())) {
                nList.removeChild(node);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/Gallery/galleries.xml"));
        transformer.transform(source, result);
    }
    @FXML
    public void saveContent() throws Exception{

        System.out.println("saving");
        emptyFile();

        File fXmlFile = new File("src/Gallery/galleries.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        Node gls = doc.getFirstChild();
        System.out.println(galleries.size());
        for (Gallery gl:galleries) {
            Element gallery = doc.createElement("gallery");
            gls.appendChild(gallery);

            String id = Integer.toString(gl.getId());
            gallery.setAttribute("id", id);
            gallery.setAttribute("address", gl.getAddress());

            Element title = doc.createElement("manager");
            title.appendChild(doc.createTextNode(gl.getManager()));
            gallery.appendChild(title);
            for (Contents c:gl.getContent()) {
                Element content = doc.createElement("contents");
                gallery.appendChild(content);
                content.setAttribute("id", Integer.toString(c.getId()));
                content.setAttribute("gallery_id", Integer.toString(c.getGallery_id()));

                Element type = doc.createElement("type");
                type.appendChild(doc.createTextNode(c.getType()));
                content.appendChild(type);

                Element author = doc.createElement("author");
                author.appendChild(doc.createTextNode(c.getAuthor()));
                content.appendChild(author);

                Element titlec = doc.createElement("title");
                titlec.appendChild(doc.createTextNode(c.getTitle()));
                content.appendChild(titlec);

                Element price = doc.createElement("price");
                price.appendChild(doc.createTextNode(Double.toString(c.getPrice())));
                content.appendChild(price);
            }



        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/Gallery/galleries.xml"));
        transformer.transform(source, result);

    }

    protected void populateTable() throws Exception{
        System.out.println("populate");
        table_manager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        System.out.println(galleries);
        table.setItems(galleries);
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
                    Gallery bk = table.getSelectionModel().getSelectedItem();

                    System.out.println(table.getSelectionModel().getSelectedItem().getId());
                    try
                    {
                        Stage stage1 = (Stage) table.getScene().getWindow();
                        stage1.close();

                        FXMLLoader loader = new   FXMLLoader(getClass().getResource("views/Contents.fxml"));
                        Parent root = (Parent)loader.load();
                        GalleryController galleryController = loader.getController();
                        galleryController.fill(galleries,bk);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setResizable(false);
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                        if(!stage.isShowing()) {
                            try{
                                saveContent();
                            }catch (Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                    catch(IOException e)
                    {
                        System.out.println(e);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        });
    }
    @FXML
    void addGallery() {
                try{
                        FXMLLoader loader = new   FXMLLoader(getClass().getResource("views/AddGallery.fxml"));
                        Parent root = (Parent)loader.load();
                        GalleryController galleryController = loader.getController();
                        galleryController.add(galleries);
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
                    }catch (Exception e){
                        System.out.println(e);
                    }
        }
    void refreshTable(){
        table.getColumns().get(0).setVisible(false);
        table.getColumns().get(0).setVisible(true);
    }

}


