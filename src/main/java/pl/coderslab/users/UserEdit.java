package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDAO;

import java.io.IOException;

@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        UserDAO userDao = new UserDAO();
        User read = userDao.read(Integer.parseInt(id));
        request.setAttribute("user", read);
        getServletContext().getRequestDispatcher("/users/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setId(Integer.parseInt(req.getParameter("id")));
        user.setUserName(req.getParameter("userName"));
        user.setEmail(req.getParameter("userEmail"));
        user.setPassword(req.getParameter("userPassword"));
        UserDAO userDao = new UserDAO();
        userDao.update(user);
        resp.sendRedirect(req.getContextPath() + "/user/list");
    }
}
