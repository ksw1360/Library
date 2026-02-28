package com.example.Library.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 파야라/하이버네이트 등 프레임워크 사용 시 필수
public class BookDTO {
    private int id;
    private String isbn;
    private String bookname;
    private String author;
    private String price;
    private int available_count;
    private String status;

    @Builder
    public BookDTO(int id, String isbn, String bookname, String author, String price, int available_count) {
        this.id = id;
        this.isbn = isbn;
        this.bookname = bookname;
        this.author = author;
        this.price = price;
        this.available_count = available_count;
        this.status = (available_count > 0) ? "대여가능" : "대여중";
    }
}