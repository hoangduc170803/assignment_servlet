/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Book;
import dal.BooksDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DeleteBook extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
        // Lấy id của danh mục cần xóa từ request parameter
        int bookid = Integer.parseInt(request.getParameter("id"));

        // Khởi tạo đối tượng CategoryDAO
        BooksDAO bookDAO = new BooksDAO();

        // Kiểm tra xem danh mục có tồn tại trong cơ sở dữ liệu hay không
        Book bookToDelete = bookDAO.getBookById(bookid);
        if (bookToDelete== null) {
            // Nếu danh mục không tồn tại, xử lý lỗi và chuyển về trang listCategory
            request.setAttribute("error", "Danh mục không tồn tại hoặc đã bị xóa trước đó!");
            request.getRequestDispatcher("listBooks").forward(request, response);
            return; // Kết thúc xử lý request
        }

        // Xóa danh mục từ cơ sở dữ liệu
        boolean deleteSuccess = bookDAO.deleteBook(bookid);

        if (deleteSuccess) {
            // Nếu xóa thành công, cập nhật lại danh sách danh mục
            List<Book> updatedBookList = bookDAO.getAllBooks();

            // Lưu danh sách đã cập nhật vào request để hiển thị trên trang
            request.setAttribute("list", updatedBookList);
            request.setAttribute("success", "Danh mục đã được xóa thành công!");

        } else {
            // Nếu xóa không thành công, xử lý thông báo lỗi
            request.setAttribute("error", "Xóa danh mục không thành công!");
        }

        // Chuyển hướng về trang danh sách danh mục
        request.getRequestDispatcher("listBooks").forward(request, response);
    }
    } 
