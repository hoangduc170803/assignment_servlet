package dal;

import Model.Author;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AuthorDAO extends DBContext {

    public Map<Integer, Author> getAllAuthors() {
        Map<Integer, Author> list = new HashMap<>();
        try {
            String sql = "SELECT * FROM Authors";
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Author a = new Author();
                a.setAuthor_id(rs.getInt("author_id"));
                a.setName(rs.getString("name"));
                // Chuyển đổi ngày sinh từ String sang Date
                java.util.Date birthdate = new java.util.Date(rs.getDate("birthdate").getTime());
                a.setBirthdate(birthdate);
                a.setNationality(rs.getString("nationality"));
                list.put(a.getAuthor_id(), a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void addAuthor(Author author) {
        try {
            String query = "INSERT INTO Authors(name, birthdate, nationality) VALUES (?, ?, ?)";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, author.getName());

                // Convert birthdate to java.sql.Date
                java.sql.Date birthdateSql = new java.sql.Date(author.getBirthdate().getTime());
                statement.setDate(2, birthdateSql);

                statement.setString(3, author.getNationality());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Author getAuthorById(int Id) {
        Author author = null;

        try {
            String query = "SELECT * FROM Authors WHERE author_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setInt(1, Id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("author_id");
                        String name = resultSet.getString("name");
                        Date birthdate = resultSet.getDate("birthdate"); // Lấy dữ liệu kiểu Date từ cột birthdate
                        String nationality = resultSet.getString("nationality");

                        author = new Author(id, name, birthdate, nationality);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    public void updateAuthor(Author author) {
        try {
            String query = "UPDATE Authors SET name = ?, birthdate = ?, nationality = ? WHERE author_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, author.getName());
                statement.setDate(2, new java.sql.Date(author.getBirthdate().getTime())); // Chuyển đổi java.util.Date sang java.sql.Date
                statement.setString(3, author.getNationality());
                statement.setInt(4, author.getAuthor_id());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteAuthor(int id) {
        boolean success = false;
        String sql = "DELETE FROM Authors WHERE author_id  = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý ngoại lệ một cách phù hợp, ví dụ ghi log
        }
        return success;
    }
}
