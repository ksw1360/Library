package com.example.Library.Service;

import java.sql.Connection;
import java.sql.SQLException;
import com.example.Library.DAO.BookDAO;
import com.example.Library.DAO.RentalDAO;
import com.example.Library.DB_Utils.DBUtil;

public class LibraryService {
    public BookDAO bookDAO = new BookDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    public boolean rentBook(int bookId, int memberId) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            var book = bookDAO.getAllBooks().stream()
                    .filter(b -> b.getId() == bookId)
                    .findFirst()
                    .orElse(null);

            if (book == null || book.getAvailable_count() <= 0) {
                return false;
            }

            int newCount = book.getAvailable_count() - 1;
            int updateCountResult = bookDAO.updateAvailableCount(conn, bookId, newCount);
            int insertCount = rentalDAO.insertRental(conn, bookId, memberId);

            if (updateCountResult == 1 && insertCount == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnBook(int bookId) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            var book = bookDAO.getAllBooks().stream()
                    .filter(b -> b.getId() == bookId)
                    .findFirst()
                    .orElse(null);

            if (book != null) {
                int newCount = book.getAvailable_count() + 1;
                bookDAO.updateAvailableCount(conn, bookId, newCount);
            }

            int updateRentalCount = rentalDAO.updateReturnDate(conn, bookId);

            if (updateRentalCount > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // boolean chk = libraryService.insertBook(bookId, ISBN, bookName, author,
    // status.totalcount, count);
    public boolean insertBook(String ISBN, String BookName, String author, String status, int totalcount,
            int count) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            int insertBookCount = bookDAO.insertNewBook(conn, ISBN, BookName, author, status, totalcount, count);
            if (insertBookCount > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}