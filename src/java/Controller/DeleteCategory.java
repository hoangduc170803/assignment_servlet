package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dal.CategoryDAO;
import Model.Category;
import java.util.Map;

public class DeleteCategory extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy id của danh mục cần xóa từ request parameter
        int categoryId = Integer.parseInt(request.getParameter("id"));

        // Khởi tạo đối tượng CategoryDAO
        CategoryDAO categoryDAO = new CategoryDAO();

        // Kiểm tra xem danh mục có tồn tại trong cơ sở dữ liệu hay không
        Category categoryToDelete = categoryDAO.getCategoryById(categoryId);
        if (categoryToDelete == null) {
            // Nếu danh mục không tồn tại, xử lý lỗi và chuyển về trang listCategory
            request.setAttribute("error", "Danh mục không tồn tại hoặc đã bị xóa trước đó!");
            request.getRequestDispatcher("listCategory").forward(request, response);
            return; // Kết thúc xử lý request
        }

        // Xóa danh mục từ cơ sở dữ liệu
        boolean deleteSuccess = categoryDAO.deleteCategory(categoryId);

        if (deleteSuccess) {
            // Nếu xóa thành công, cập nhật lại danh sách danh mục
            Map<Integer, Category> updatedCategoryList = categoryDAO.getAllCategories();

            // Lưu danh sách đã cập nhật vào request để hiển thị trên trang
            request.setAttribute("list", updatedCategoryList);
            request.setAttribute("success", "Danh mục đã được xóa thành công!");

        } else {
            // Nếu xóa không thành công, xử lý thông báo lỗi
            request.setAttribute("error", "Xóa danh mục không thành công!");
        }

        // Chuyển hướng về trang danh sách danh mục
        request.getRequestDispatcher("listCategory").forward(request, response);
    }
}
