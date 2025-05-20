package com.lottefn.collateral.domain.configs.security;

import com.lottefn.collateral.domain.entities.Permission;
import com.lottefn.collateral.domain.entities.User;
import com.lottefn.collateral.domain.enums.Menu;
import com.lottefn.collateral.domain.enums.PermissionMethod;
import com.lottefn.collateral.domain.enums.SystemRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

import static com.lottefn.collateral.domain.utils.Constants.AUTH_WHITE_LIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authSupplier,
                                       RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String uri    = request.getRequestURI();
        String method = request.getMethod();
        String token = jwtTokenProvider.extractTokenFromCookie(request);

        log.debug("Checking request {} {}", method, uri);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            User userFromJWT = jwtTokenProvider.getUserFromJWT(token);
            if (AUTH_WHITE_LIST.contains(uri) || SystemRole.ADMIN.equals(userFromJWT.getRole().getName())) {
                return new AuthorizationDecision(true);
            }
            List<Permission> permissions = userFromJWT.getRole().getPermissions();

            for (Permission permission : permissions) {

                Menu menu = permission.getMenu();
                PermissionMethod permissionMethod = permission.getMethod();

                String expectedUri = menu.getUrl() + permissionMethod.getPath();
                String expectedMethod = permissionMethod.getMethod().name();

                if (method.equalsIgnoreCase(expectedMethod) && matchUrl(expectedUri, uri)) {
                    return new AuthorizationDecision(true);
                }
            }
        }
        return new AuthorizationDecision(false);
    }


    private boolean matchUrl(String expectedPattern, String actualUri) {
        String regex = expectedPattern.replaceAll("\\{[^/]+}", "[^/]+");
        return actualUri.matches(regex);
    }

}
