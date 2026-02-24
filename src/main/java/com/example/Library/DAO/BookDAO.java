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
                        rs.getInt("id"), // 🌟 DB의 진짜 고유번호! (컬럼명이 다르면 맞춰주세요)
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

    // 조건절(WHERE)을 isbn이 아니라 고유번호(id)로!
    public int updateAvailableCount(Connection conn, int bookId, int availableCount) throws SQLException {
        String sql = "UPDATE books SET available_count = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, availableCount);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate();
        }
    }
}