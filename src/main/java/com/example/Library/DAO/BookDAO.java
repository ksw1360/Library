package com.example.Library.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.example.Library.DB_Utils.DBUtil;
import com.example.Library.DTO.BookDTO;

public class BookDAO {

    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BookDTO book = new BookDTO(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("bookname"),
                        rs.getString("author"),
                        rs.getString("price"),
                        rs.getInt("available_count"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public int updateAvailableCount(Connection conn, int bookId, int availableCount) throws SQLException {
        String sql = "UPDATE books SET available_count = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, availableCount);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate();
        }
    }

    // "ID\t| ISBN\t\t| 도서명\t\t| 저자\t| 재고\t| 상태");
    // int insertBookCount = bookDAO.insertNewBook(conn, ID, ISBN, BookName, author,
    // status, totalcount, count);
    public int insertNewBook(Connection conn, int ID, String ISBN, String BookName, String author, String status,
            int totalcount, int count) {
        int result = 0;

        // 1. 매개변수로 받은 데이터(ID, status 등)에 맞게 INSERT 컬럼을 조정했습니다.
        // 2. createdate, modifydate는 DB의 CURRENT_TIMESTAMP가 알아서 찍어주므로 ?를 쓰지 않습니다.
        String sql = "INSERT INTO Library_db.books (id, isbn, bookname, author, status, total_count, available_count, createdate) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            pstmt.setString(2, ISBN);
            pstmt.setString(3, BookName);
            pstmt.setString(4, author);
            pstmt.setString(5, status);
            pstmt.setInt(6, totalcount);
            pstmt.setInt(7, count);
            // Timestamp는 SQL에서 CURRENT_TIMESTAMP로 처리되므로 자바에서 세팅할 필요가 없습니다! 쾌적하죠?

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}