package dal;

import Model.Publisher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class PublisherDAO extends DBContext {

    public Map<Integer, Publisher> getAllPublishers() {
        Map<Integer, Publisher> list = new HashMap<>();
        try {
            String sql = "SELECT * FROM Publishers";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Publisher p = new Publisher();
                p.setPublisher_id(rs.getInt("publisher_id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setContact(rs.getString("contact"));
                list.put(p.getPublisher_id(), p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public String getPublisherNameByPublisherId(int publisherId) {
        String publisherName = null;
        String query = "SELECT name FROM Publishers WHERE publisher_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, publisherId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    publisherName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publisherName;
    }

    public void addPublisher(Publisher publisher) {
        try {
            String query = "INSERT INTO Publishers(name, address, contact) VALUES (?, ?, ?)";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, publisher.getName());
                statement.setString(2, publisher.getAddress());
                statement.setString(3, publisher.getContact());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Publisher getPublisherById(int publisherId) {
        Publisher publisher = null;

        try {
            String query = "SELECT * FROM Publishers WHERE publisher_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setInt(1, publisherId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("publisher_id");
                        String name = resultSet.getString("name");
                        String address = resultSet.getString("address");
                        String contact = resultSet.getString("contact");

                        publisher = new Publisher(id, name, address, contact);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publisher;
    }

    public void updatePublisher(Publisher publisher) {
        try {
            String query = "UPDATE Publishers SET name = ?, address = ?, contact= ? WHERE publisher_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, publisher.getName());
                statement.setString(2, publisher.getAddress());
                statement.setString(3, publisher.getContact());
                statement.setInt(4, publisher.getPublisher_id());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean deletePublisher(int id) {
        boolean success = false;
        String sql = "DELETE FROM Publishers WHERE publisher_id  = ?";
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
