package pl.coderslab.users;

public class UserValidator {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static String validate(String name, String email, String password) {
        if (!isValidName(name)) {
            return "Nazwa użytkownika jest wymagana.";
        }
        if (!isValidEmail(email)) {
            return "Niepoprawny adres e-mail.";
        }
        if (!isValidPassword(password)) {
            return "Hasło musi mieć co najmniej 6 znaków.";
        }
        return null;
    }
}
