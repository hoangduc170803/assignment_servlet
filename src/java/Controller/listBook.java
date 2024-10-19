/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Book;
import Model.Category;
import dal.BooksDAO;
import dal.CategoryDAO;
import dal.PublisherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class listBook extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BooksDAO bDao = new BooksDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        PublisherDAO publisherDAO = new PublisherDAO();
        List<Book> listB = bDao.getAllBooks();
        Map<Integer, Category> listC = categoryDAO.getAllCategories();
        Map<Integer, String> publisherNames = new HashMap<>();
        for (Book book : listB) {
            String publisherName = publisherDAO.getPublisherNameByPublisherId(book.getPublisherId());
            publisherNames.put(book.getPublisherId(), publisherName);
        }
        request.setAttribute("listB", listB);
        request.setAttribute("listC", listC);
        request.setAttribute("publisherNames", publisherNames);
        request.getRequestDispatcher("listBook.jsp").forward(request, response);
    }

}
