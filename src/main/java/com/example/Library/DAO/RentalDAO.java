package com.example.Library.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RentalDAO {

    public int insertRental(Connection conn, int bookId, int userId) {
        String sql = "INSERT INTO rentals (book_id, user_id, rent_date, due_date, status) " +
                "VALUES (?, ?, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY), '대여중')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId); // 🌟 깔끔하게 int로!
            pstmt.setInt(2, userId); // 🌟 깔끔하게 int로!
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ 대여 기록 INSERT 실패: " + e.getMessage());
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
}