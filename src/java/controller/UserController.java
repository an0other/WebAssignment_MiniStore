/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDAO;
import model.UserDTO;

/**
 *
 * @author an0other
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    public static final String LOGIN_PAGE="login.jsp";
    public static final String REGISTER_PAGE="register.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=LOGIN_PAGE;
        try {
            String action=request.getParameter("action");
            if (action.equals("login")){
                url=handleLogin(request);
            } else 
            if ("logout".equals(action)){
                url=handleLogout(request);
            } else 
            if ("register".equals(action)){
                url=handleRegister(request);
            }
        } catch (Exception e) {
            System.err.print(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleLogin(HttpServletRequest request) {
        HttpSession session= request.getSession();
        UserDAO userdao=new UserDAO();
        String message="";
        String username=(String) request.getParameter("username");
        String password=(String) request.getParameter("password");
        if (userdao.login(username, password)){
            UserDTO user=userdao.getUserByUsername(username);
            session.setAttribute("user", user);
            return "index.jsp";
        } else {
            message="Incorrect Username or Password";
        }
        session.setAttribute("message", message);
        return "login.jsp";
    }

    private String handleLogout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        UserDTO user= (UserDTO) session.getAttribute("user");
        if (user!=null){
            user=null;
            session.invalidate();
        }
        return "index.jsp";
    }

    private String handleRegister(HttpServletRequest request) {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        String fullname=request.getParameter("fullname");
        UserDTO user=new UserDTO(username, password, email, fullname, "MEMBER", true);
        UserDAO userdao=new UserDAO();
        if (userdao.createNewUser(user, request)) return "login.jsp";
        return "register.jsp";
    }

}
