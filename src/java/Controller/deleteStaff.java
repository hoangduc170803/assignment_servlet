/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Staff;
import dal.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class deleteStaff extends HttpServlet {
   

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy id của danh mục cần xóa từ request parameter
        int staffid = Integer.parseInt(request.getParameter("staff_id"));

        // Khởi tạo đối tượng CategoryDAO
        StaffDAO staffDAO = new StaffDAO();

        // Kiểm tra xem danh mục có tồn tại trong cơ sở dữ liệu hay không
        Staff staffToDelete = staffDAO.getStaffById(staffid);
        if (staffToDelete== null) {
            // Nếu danh mục không tồn tại, xử lý lỗi và chuyển về trang listCategory
            request.setAttribute("error", "Danh mục không tồn tại hoặc đã bị xóa trước đó!");
            request.getRequestDispatcher("listStaffs").forward(request, response);
            return; // Kết thúc xử lý request
        }

        // Xóa danh mục từ cơ sở dữ liệu
        boolean deleteSuccess = staffDAO.deleteStaff(staffid);

        if (deleteSuccess) {
            // Nếu xóa thành công, cập nhật lại danh sách danh mục
            Map<Integer, Staff> updatedStaffList = staffDAO.getAllStaff();

            // Lưu danh sách đã cập nhật vào request để hiển thị trên trang
            request.setAttribute("list", updatedStaffList);
            request.setAttribute("success", "Danh mục đã được xóa thành công!");

        } else {
            // Nếu xóa không thành công, xử lý thông báo lỗi
            request.setAttribute("error", "Xóa danh mục không thành công!");
        }

        // Chuyển hướng về trang danh sách danh mục
        request.getRequestDispatcher("listStaffs").forward(request, response);
    }
    } 

   
