/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Publisher;
import dal.PublisherDAO;
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
public class deletePublisher extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
        // Lấy id của danh mục cần xóa từ request parameter
        int publisherid = Integer.parseInt(request.getParameter("id"));

        // Khởi tạo đối tượng CategoryDAO
        PublisherDAO pDAO = new PublisherDAO();

        // Kiểm tra xem danh mục có tồn tại trong cơ sở dữ liệu hay không
        Map<Integer, Publisher> listP = pDAO.getAllPublishers();
        if (listP== null) {
            // Nếu danh mục không tồn tại, xử lý lỗi và chuyển về trang listCategory
            request.setAttribute("error", "Danh mục không tồn tại hoặc đã bị xóa trước đó!");
            request.getRequestDispatcher("listPublisher").forward(request, response);
            return; // Kết thúc xử lý request
        }

        // Xóa danh mục từ cơ sở dữ liệu
        boolean deleteSuccess = pDAO.deletePublisher(publisherid);

        if (deleteSuccess) {
            // Nếu xóa thành công, cập nhật lại danh sách danh mục
            Map<Integer, Publisher> updatedPublisherList = pDAO.getAllPublishers();

            // Lưu danh sách đã cập nhật vào request để hiển thị trên trang
            request.setAttribute("list", updatedPublisherList);
            request.setAttribute("success", "Danh mục đã được xóa thành công!");

        } else {
            // Nếu xóa không thành công, xử lý thông báo lỗi
            request.setAttribute("error", "Xóa danh mục không thành công!");
        }

        // Chuyển hướng về trang danh sách danh mục
        request.getRequestDispatcher("listPublisher").forward(request, response);
    }
    } 
