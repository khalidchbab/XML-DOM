package Library.models;

public class Book {
    static int count=0;
    private int id;
    private String title;
    private String author;
    private String Genre;

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        Genre = genre;
        count++;
    }

    // Getters and Setters
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Book.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
