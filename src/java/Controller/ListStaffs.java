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
public class ListStaffs extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        StaffDAO sDao = new StaffDAO();
        Map<Integer, Staff> list = sDao.getAllStaff();
        request.setAttribute("list", list);
        request.getRequestDispatcher("listStaff.jsp").forward(request, response);
    }

    } 

   
