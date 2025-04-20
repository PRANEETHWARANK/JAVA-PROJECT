package LibraryManagementSystem;


public class Book {
    private String title;
    private String author;
    private int year;
    private String isbn;
    private String username;

    public Book(String title, String author, int year, String isbn, String username) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.username = username;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getIsbn() { return isbn; }
    public String getUsername() { return username; }
}

