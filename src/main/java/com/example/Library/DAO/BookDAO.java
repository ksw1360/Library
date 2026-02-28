package com.example.Library.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public int insertNewBook(Connection conn, String ISBN, String BookName, String author, String status,
            int totalcount, int count) {
        int result = 0;

        String sql = "INSERT INTO Library_db.books (isbn, bookname, author, status, total_count, available_count, createdate) "
                + "VALUES(?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 자동 증가라 제외
            // pstmt.setInt(1, ID);
            pstmt.setString(1, ISBN);
            pstmt.setString(2, BookName);
            pstmt.setString(3, author);
            pstmt.setString(4, status);
            pstmt.setInt(5, totalcount);
            pstmt.setInt(6, count);
            // Timestamp는 SQL에서 CURRENT_TIMESTAMP로 처리되므로 자바에서 세팅할 필요가 없습니다! 쾌적하죠?

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}