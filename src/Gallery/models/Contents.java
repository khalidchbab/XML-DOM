package Gallery.models;

public class Contents {
    private int id;
    private int gallery_id;
    private String type;
    private String author;
    private String title;
    private Double price;

    public Contents(int id, int gallery_id, String type, String author, String title, Double price) {
        this.id = id;
        this.gallery_id = gallery_id;
        this.type = type;
        this.author = author;
        this.title = title;
        this.price = price;
    }

    public Contents(int id, int gallery_id, String type, String author) {
        this.id = id;
        this.gallery_id = gallery_id;
        this.type = type;
        this.author = author;
    }

    public Contents() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(int gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
