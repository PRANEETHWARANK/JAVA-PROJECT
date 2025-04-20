package LibraryManagementSystem;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookManager {

    public static void addBook(Book book) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            String sql = "INSERT INTO books (title, author, year, isbn, username) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setString(4, book.getIsbn());
            stmt.setString(5, book.getUsername());

            stmt.executeUpdate();
            System.out.println("Book added successfully.");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(int bookId, String newTitle, int newId) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            String query = "UPDATE books SET ";
            boolean hasTitle = !newTitle.isEmpty();
            boolean hasId = newId != -1;

            if (hasTitle) query += "title = ?";
            if (hasId) {
                if (hasTitle) query += ", ";
                query += "id = ?";
            }

            query += " WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            int index = 1;

            if (hasTitle) stmt.setString(index++, newTitle);
            if (hasId) stmt.setInt(index++, newId);
            stmt.setInt(index, bookId);

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Book updated." : "Book ID not found.");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void borrowBook(String username, String isbn) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            LocalDate borrowDate = LocalDate.now();
            LocalDate returnDate = borrowDate.plusMonths(1);

            String sql = "INSERT INTO borrowed_books (username, isbn, borrow_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, isbn);
            stmt.setDate(3, Date.valueOf(borrowDate));
            stmt.setDate(4, Date.valueOf(returnDate));

            stmt.executeUpdate();
            System.out.println("Book borrowed! Return by: " + returnDate);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnBook(String username, String isbn) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            LocalDate today = LocalDate.now();

            String sql = "UPDATE borrowed_books SET actual_return_date = ? WHERE username = ? AND isbn = ? AND actual_return_date IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(today));
            stmt.setString(2, username);
            stmt.setString(3, isbn);

            int updated = stmt.executeUpdate();
            System.out.println(updated > 0 ? "Book returned." : "No record found.");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void calculateFine(String username) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        try {
            String sql = "SELECT isbn, return_date, actual_return_date FROM borrowed_books WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            int totalFine = 0;

            while (rs.next()) {
                Date returnDate = rs.getDate("return_date");
                Date actualReturn = rs.getDate("actual_return_date");

                if (actualReturn != null && actualReturn.after(returnDate)) {
                    long daysLate = ChronoUnit.DAYS.between(returnDate.toLocalDate(), actualReturn.toLocalDate());
                    int fine = (int) daysLate * 5;
                    totalFine += fine;

                    System.out.println("Book ISBN: " + rs.getString("isbn") + " | Days Late: " + daysLate + " | Fine: $" + fine);
                }
            }

            System.out.println("Total fine for user '" + username + "': $" + totalFine);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
