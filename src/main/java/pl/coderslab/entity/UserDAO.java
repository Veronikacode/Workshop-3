package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class UserDAO {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?";

    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";

    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";

    private static final String FIND_USER_BY_EMAIL_QUERY =
            "SELECT * FROM users WHERE email = ?";


    public User create(User user) {
        try (Connection conn = DbUtil.getConnection ()) {
            PreparedStatement preStmt =
                    conn.prepareStatement (CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preStmt.setString (1, user.getUserName ());
            preStmt.setString (2, user.getEmail ());
            preStmt.setString (3, hashPassword (user.getPassword ()));
            preStmt.executeUpdate ();

            ResultSet rs = preStmt.getGeneratedKeys ();
            if (rs.next ()) {
                int id = rs.getInt (1);
                user.setId (id);
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace ();
            return null;
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw (password, BCrypt.gensalt ());
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection ();
             PreparedStatement stmt = conn.prepareStatement (READ_USER_QUERY)) {

            stmt.setInt (1, userId);
            ResultSet rs = stmt.executeQuery ();

            if (rs.next ()) {
                User user = new User ();
                user.setId (rs.getInt ("id"));
                user.setUserName (rs.getString ("username"));
                user.setEmail (rs.getString ("email"));
                user.setPassword (rs.getString ("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection ();
             PreparedStatement stmt = conn.prepareStatement (UPDATE_USER_QUERY)) {

            stmt.setString (1, user.getUserName ());
            stmt.setString (2, user.getEmail ());
            stmt.setString (3, hashPassword (user.getPassword ()));
            stmt.setInt (4, user.getId ());
            stmt.executeUpdate ();

        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection ();
             PreparedStatement stmt = conn.prepareStatement (DELETE_USER_QUERY)) {

            stmt.setInt (1, userId);
            stmt.executeUpdate ();

        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    public User[] findAll() {
        User[] users = new User[0];
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL_USERS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users = addToArray(user, users);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public User findUserByEmail(String email) {
        try (Connection conn = DbUtil.getConnection ();
             PreparedStatement stmt = conn.prepareStatement (FIND_USER_BY_EMAIL_QUERY)) {

            stmt.setString (1, email);
            ResultSet rs = stmt.executeQuery ();

            if (rs.next ()) {
                User user = new User ();
                user.setId (rs.getInt ("id"));
                user.setUserName (rs.getString ("username"));
                user.setEmail (rs.getString ("email"));
                user.setPassword (rs.getString ("password"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return null;
    }
}
