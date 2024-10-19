/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Model.Loan;
import dal.LoansDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ReturnBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy id của loan cần xử lý từ request parameter
        int loanId = Integer.parseInt(request.getParameter("loanId"));

        // Khởi tạo đối tượng LoansDAO
        LoansDAO lDAO = new LoansDAO();

        // Cập nhật return_date cho loan
        boolean updateSuccess = lDAO.returnBook(loanId);

        if (updateSuccess) {
            // Nếu cập nhật thành công, cập nhật lại danh sách loans
            List<Loan> listL = lDAO.getAllLoans();

            // Lưu danh sách đã cập nhật vào request để hiển thị trên trang
            request.setAttribute("loanDetails", listL);
            request.setAttribute("success", "Loan đã được xử lý thành công!");
        } else {
            // Nếu cập nhật không thành công, xử lý thông báo lỗi
            request.setAttribute("error", "Xử lý loan không thành công!");
        }

        // Chuyển hướng về trang danh sách loans
        request.getRequestDispatcher("listLoan").forward(request, response);
    }
}
