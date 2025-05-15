package proton.face.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import proton.face.entity.Role;
import proton.face.entity.User;
import proton.face.repository.LoginRepository;
import proton.face.util.SessionUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class LoginController implements Serializable {

    private User user = new User();

    private final LoginRepository loginRepository ;

    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // validate login
    public String validateUsernamePassword() {
        User valid = loginRepository.validate(user.getUsername(), user.getPassword());
        if (valid != null) {
            User user = new User();
            user.setId(valid.getId());
            user.setRole(valid.getRole());
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("user-info", user);
            return  "listPeople.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Sai tài khoản hoặc mật khẩu", "Vui lòng nhập lại tài khoản hoặc mật khẩu"));
            return "";
        }
    }

    // logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public static String isUserInRoles() {
        User user = getSessionUser();
        if (user == null) {
            return null;
        }
        return user.getRole().getName();
    }

    public static User getSessionUser() {
        User user;
        try {
            HttpSession session = SessionUtils.getSession();
            user = (User) session.getAttribute("user-info");
        } catch (Exception ex) {
            return null;
        }
        return user;
    }

}
