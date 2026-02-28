package com.example.Library.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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

    public List<String> getRentalList(Connection conn) {
        String sql = "SELECT r.id, b.bookname, r.rent_date, r.due_date, r.return_date, r.status " +
                " FROM rentals r JOIN books b ON r.book_id = b.id WHERE 1=1" +
                " AND r.status ='대여중' ORDER BY r.rent_date DESC";
        // System.out.println(sql);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            var rs = pstmt.executeQuery();
            List<String> rentalList = new java.util.ArrayList<>();
            while (rs.next()) {
                String rentalInfo = String.format("ID: %d | 도서명: %s | 대여일: %s | 반납예정일: %s | 반납일: %s | 상태: %s",
                        rs.getInt("id"),
                        rs.getString("bookname"),
                        rs.getDate("rent_date"),
                        rs.getDate("due_date"),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toString() : "미반납",
                        rs.getString("status"));
                rentalList.add(rentalInfo);
            }
            return rentalList;
        } catch (SQLException e) {
            System.out.println("대여 리스트 조회 실패: " + e.getMessage());
            return null;
        }
    }
}