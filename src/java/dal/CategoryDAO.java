package dal;

import Model.Category;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.Statement;
import java.sql.ResultSet;

public class CategoryDAO extends DBContext {

    public Map<Integer, Category> getAllCategories() {
        Map<Integer, Category> list = new HashMap<>();
        try {
            String sql = "SELECT * FROM Categories";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("category_id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                list.put(c.getId(), c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Category getCategoryById(int categoryId) {
        Category category = null;
        try {
            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM Categories WHERE category_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, categoryId);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        category = new Category();
                        category.setId(rs.getInt("category_id"));
                        category.setName(rs.getString("name"));
                        category.setDescription(rs.getString("description"));
                    }
                }
            } else {
                System.out.println("Connection to database is not established.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return category;
    }
    public Category getCategoryByName(String name) {
        Category category = null;
        try {
            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM Categories WHERE name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        category = new Category();
                        category.setId(rs.getInt("category_id"));
                        category.setName(rs.getString("name"));
                        category.setDescription(rs.getString("description"));
                    }
                }
            } else {
                System.out.println("Connection to database is not established.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return category;
    }

    public void addCategory(String name, String description) {
        try {
            if (connection != null && !connection.isClosed()) {
                String sql = "INSERT INTO Categories (name, description) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, description);
                    preparedStatement.executeUpdate();
                    System.out.println("Category added successfully.");
                }
            } else {
                System.out.println("Connection to database is not established.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateCategory(Category category) {
        try {
            if (connection != null && !connection.isClosed()) {
                String query = "UPDATE Categories SET name = ?, description = ?  WHERE category_id = ?";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, category.getName());
                    ps.setString(2, category.getDescription());
                    ps.setInt(3, category.getId());
                    ps.executeUpdate();
                    System.out.println("Category updated successfully.");
                }
            } else {
                System.out.println("Connection to database is not established.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean deleteCategory(int id) {
        boolean success = false;
        String sql = "DELETE FROM Categories WHERE category_id  = ?";
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
