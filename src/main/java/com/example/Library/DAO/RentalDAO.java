package com.example.Library.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Library.DB_Utils.DBUtil;
import com.example.Library.DTO.BookDTO;

public class RentalDAO {

    public int insertRental(Connection conn, int bookId, int userId) {
        String sql = "INSERT INTO rentals (book_id, user_id, rent_date, due_date, status) " +
                "VALUES (?, ?, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY), '대여중')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId); // 🌟 깔끔하게 int로!
            pstmt.setInt(2, userId); // 🌟 깔끔하게 int로!
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("대여 기록 INSERT 실패: " + e.getMessage());
            return 0;
        }
    }

    public int updateReturnDate(Connection conn, int bookId) throws SQLException {
        String sql = "UPDATE rentals SET return_date = CURRENT_DATE, status = '반납완료' WHERE book_id = ? AND return_date IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            return pstmt.executeUpdate();
        }
    }

    public List<BookDTO> getAllRentRecords() {
        List<BookDTO> books = new ArrayList<>();
        String sql = "SELECT " +
                " r.id,  " +
                " b.bookname,  " +
                " u.username,  " +
                " r.rent_date,  " +
                " r.due_date,  " +
                " r.return_date,  " +
                " r.status " +
                " FROM rentals r " +
                " JOIN books b ON r.book_id = b.id " +
                " JOIN users u ON r.user_id = u.id " +
                " ORDER BY r.rent_date DESC ";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                book = new BookDTO(
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
}