package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDAO;

import java.io.IOException;

@WebServlet("/user/add")
public class UserAdd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/add.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("userName");
        String email = req.getParameter("userEmail");
        String password = req.getParameter("userPassword");

        String validationError = UserValidator.validate(name, email, password);

        if (validationError != null) {
            req.setAttribute("error", validationError);
            req.getRequestDispatcher("/users/add.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setPassword(password);

        UserDAO userDao = new UserDAO();
        userDao.create(user);

        resp.sendRedirect(req.getContextPath() + "/user/list");

    }
}
