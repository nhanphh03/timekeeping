package nhanph.timekeeping.admin.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.admin.entity.Users;
import nhanph.timekeeping.admin.repository.UsersRepository;
import nhanph.timekeeping.admin.util.SessionUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class LoginController implements Serializable {

    private Users user = new Users();

    private final UsersRepository usersRepository;

    @Inject
    public LoginController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // validate login
    public String validateUsernamePassword() {
        Users valid = usersRepository.findByUsername(user.getUsername());

        if (user.getPassword().equals(valid.getPassword())) {
            Users userLogin = new Users();
            userLogin.setId(valid.getId());
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
}
