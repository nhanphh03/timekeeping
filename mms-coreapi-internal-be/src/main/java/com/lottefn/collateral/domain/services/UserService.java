package com.lottefn.collateral.domain.services;

import com.lottefn.collateral.app.requests.LoginRequest;
import com.lottefn.collateral.app.responses.LoginResponse;
import com.lottefn.collateral.app.responses.UserInfoResponse;
import com.lottefn.collateral.domain.entities.User;
import com.lottefn.collateral.domain.entities.data.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService extends BaseService<User, Long> {
    LoginResponse login(LoginRequest loginRequest, HttpServletResponse response);

    UserInfoResponse getUserInfo(CustomUserDetails userDetails);
}
