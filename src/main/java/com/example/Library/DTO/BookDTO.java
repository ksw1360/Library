package com.example.Library.DTO;

public class BookDTO {
    private int id; // 🌟 DB의 진짜 고유번호 (int)
    private String isbn;
    private String bookname;
    private String author;
    private String price;
    private int available_count;
    private String status;

    public BookDTO(int id, String isbn, String bookname, String author, String price, int available_count) {
        this.id = id;
        this.isbn = isbn;
        this.bookname = bookname;
        this.author = author;
        this.price = price;
        this.available_count = available_count;
        this.status = available_count > 0 ? "대여가능" : "대여중";
    }

    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public int getAvailable_count() {
        return available_count;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAvailable_count(int available_count) {
        this.available_count = available_count;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}