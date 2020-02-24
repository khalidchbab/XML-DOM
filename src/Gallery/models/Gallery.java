package Gallery.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Gallery {
    private int id;
    private String address;
    private String manager;
    private ObservableList<Contents> content= FXCollections.observableArrayList();
    public Gallery() {
    }
    public Gallery(int id,String manager) {
        this.id=id;
        this.manager = manager;
    }

    public void setContent(ObservableList<Contents> content) {
        this.content = content;
    }

    public Gallery(int id, String address, String manager) {
        this.id = id;
        this.address = address;
        this.manager = manager;
    }

    public Gallery(int id, String address, String manager, ObservableList<Contents> content) {
        this.id = id;
        this.address = address;
        this.manager = manager;
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public ObservableList<Contents> getContent() {
        return content;
    }

}
