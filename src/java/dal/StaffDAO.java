package dal;

import Model.Staff;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class StaffDAO extends DBContext {

    public Map<Integer, Staff> getAllStaff() {
        Map<Integer, Staff> staffList = new HashMap<>();
        String sql = "SELECT * FROM Staff";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setUsername(rs.getString("username"));
                staff.setPassword(rs.getString("password"));
                staff.setFirstName(rs.getString("first_name"));
                staff.setLastName(rs.getString("last_name"));
                staff.setEmail(rs.getString("email"));
                staff.setPhone(rs.getString("phone"));
                staff.setPosition(rs.getString("position"));
                staff.setHireDate(rs.getDate("hire_date"));
                staffList.put(staff.getStaffId(), staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle or throw SQLException as needed
        }
        return staffList;
    }

    public void updateRole(String username, String newRole) throws SQLException {

        // Update role in Account table
        String sql = "UPDATE Account SET role = 'Admin' WHERE username = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();

        }
    }

// Method to delete staff from Staff and Account tables
    public void deleteStaff(String username) throws SQLException {
        String sqlDeleteStaff = "DELETE FROM Staff WHERE username = ?";
        String sqlDeleteAccount = "DELETE FROM Account WHERE username = ?";

        try (PreparedStatement pstmtStaff = connection.prepareStatement(sqlDeleteStaff); PreparedStatement pstmtAccount = connection.prepareStatement(sqlDeleteAccount)) {

            // Set parameters and execute deletion for Staff table
            pstmtStaff.setString(1, username);
            pstmtStaff.executeUpdate();

            // Set parameters and execute deletion for Account table
            pstmtAccount.setString(1, username);
            pstmtAccount.executeUpdate();
        }
    }

    // Method to add admin to Admin table
    public void addAdmin(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO Admin (username, password, role) VALUES (?, ?, 'Admin')";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        }
    }

    public boolean updateStaff(Staff staff) {
        boolean success = false;
        try {
            String sql = "UPDATE Staff SET first_name = ?, last_name = ?, email = ?, phone = ?, position = ?, hire_date = ? WHERE staff_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, staff.getFirstName());
            pstmt.setString(2, staff.getLastName());
            pstmt.setString(3, staff.getEmail());
            pstmt.setString(4, staff.getPhone());
            pstmt.setString(5, staff.getPosition());
            pstmt.setDate(6, new java.sql.Date(staff.getHireDate().getTime())); // Convert java.util.Date to java.sql.Date
            pstmt.setInt(7, staff.getStaffId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public Staff getStaffById(int id) {
        Staff staff = null;
        try {
            String sql = "SELECT * FROM Staff WHERE staff_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setUsername(rs.getString("username"));
                staff.setPassword(rs.getString("password"));
                staff.setFirstName(rs.getString("first_name"));
                staff.setLastName(rs.getString("last_name"));
                staff.setEmail(rs.getString("email"));
                staff.setPhone(rs.getString("phone"));
                staff.setPosition(rs.getString("position"));
                staff.setHireDate(rs.getDate("hire_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public boolean deleteStaff(int id) {
        boolean success = false;
        String sql = "DELETE FROM Staff WHERE staff_id  = ?";
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

    public Staff getStaffByUsername(String username) {
        Staff staff = null;
        String sql = "SELECT * FROM Staff WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setUsername(rs.getString("username"));
                staff.setPassword(rs.getString("password"));
                staff.setFirstName(rs.getString("first_name"));
                staff.setLastName(rs.getString("last_name"));
                staff.setEmail(rs.getString("email"));
                staff.setPhone(rs.getString("phone"));
                staff.setPosition(rs.getString("position"));
                staff.setHireDate(rs.getDate("hire_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staff;
    }
    public boolean updateStaffPassword(Staff staff) {
        String sqlMembers = "UPDATE Staff SET password = ? WHERE username = ?";
        String sqlAccount = "UPDATE Account SET password = ? WHERE username = ?";
        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Update Members table
            try (PreparedStatement statementMembers = connection.prepareStatement(sqlMembers)) {
                statementMembers.setString(1, staff.getPassword());
                statementMembers.setString(2, staff.getUsername());
                statementMembers.executeUpdate();
            }

            // Update Account table
            try (PreparedStatement statementAccount = connection.prepareStatement(sqlAccount)) {
                statementAccount.setString(1, staff.getPassword());
                statementAccount.setString(2, staff.getUsername());
                statementAccount.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback transaction if there was an error
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                // Reset auto-commit mode to its default state
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String username = "b";
        StaffDAO s = new StaffDAO();
        Staff st = s.getStaffByUsername(username);
        if (st != null) {
            System.out.println("Staff found:");
            System.out.println("ID: " + st.getStaffId());
            System.out.println("Username: " + st.getUsername());
            System.out.println("Full Name: " + st.getPosition());
            System.out.println("Email: " + st.getEmail());
            // Print other attributes as needed
        } else {
            System.out.println("Staff with username " + username + " not found.");
        }
    }
    
        
}
