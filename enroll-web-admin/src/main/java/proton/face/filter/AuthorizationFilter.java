package proton.face.filter;

import lombok.extern.slf4j.Slf4j;
import proton.face.constant.RoleConstant;
import proton.face.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			// nếu người dùng đã đăng nhập
			boolean pubUri = reqURI.contains("/login.xhtml")
                    || reqURI.contains("/public/")
                    || reqURI.contains("/historySignedUp.xhtml")
                    || (reqURI.contains("/403.xhtml"))
                    || reqURI.contains("javax.faces.resource")
                    || (reqURI.contains("/register.xhtml"));
			if (ses != null && ses.getAttribute("username") != null) {
				if (pubUri) {
					chain.doFilter(request, response);
				} else {
					User user = (User) ses.getAttribute("user-info");
					if (RoleConstant.ADMIN.equals(user.getRole().getName())) {
						chain.doFilter(request, response);
					} else {
						resp.sendRedirect(reqt.getContextPath() + "/403.xhtml");
					}
				}
			} else {
				if (pubUri) {
					chain.doFilter(request, response);
				} else {
					resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServletException(e);
		}
	}

}