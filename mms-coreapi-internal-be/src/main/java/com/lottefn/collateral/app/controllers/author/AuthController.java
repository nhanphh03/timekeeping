package com.lottefn.collateral.app.controllers.author;

import com.lottefn.collateral.app.requests.LoginRequest;
import com.lottefn.collateral.app.responses.LoginResponse;
import com.lottefn.collateral.app.responses.UserInfoResponse;
import com.lottefn.collateral.domain.entities.data.CustomUserDetails;
import com.lottefn.collateral.domain.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.lottefn.collateral.domain.utils.Constants.COOKIE_KEY;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
    }

    @GetMapping("/info")
    public UserInfoResponse getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getUserInfo(userDetails);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
