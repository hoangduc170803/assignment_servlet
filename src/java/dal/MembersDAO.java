package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Model.Members;

public class MembersDAO extends DBContext {

    public List<Members> getAllMembers() {
        List<Members> members = new ArrayList<>();

        try {
            String query = "SELECT * FROM Members";
            try (PreparedStatement statement = getConnection().prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int memberId = resultSet.getInt("member_id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    Date membershipDate = resultSet.getDate("membership_date");

                    Members member = new Members(memberId, username, password, firstName, lastName, email, phone,
                            address, membershipDate);
                    members.add(member);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public void addMember(Members member) {
        try {
            String query = "INSERT INTO Members (username, password, first_name, last_name, email, phone, address, membership_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, member.getUsername());
                statement.setString(2, member.getPassword());
                statement.setString(3, member.getFirstName());
                statement.setString(4, member.getLastName());
                statement.setString(5, member.getEmail());
                statement.setString(6, member.getPhone());
                statement.setString(7, member.getAddress());
                statement.setDate(8, new java.sql.Date(member.getMembershipDate().getTime()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
