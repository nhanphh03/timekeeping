package com.lottefn.collateral.domain.services.impl;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.app.requests.FilterDTO;
import com.lottefn.collateral.app.requests.LoginRequest;
import com.lottefn.collateral.app.requests.user.UserFilterDTO;
import com.lottefn.collateral.app.requests.user.UserRequest;
import com.lottefn.collateral.app.responses.LoginResponse;
import com.lottefn.collateral.app.responses.UserInfoResponse;
import com.lottefn.collateral.domain.configs.security.JwtTokenProvider;
import com.lottefn.collateral.domain.entities.Role;
import com.lottefn.collateral.domain.entities.User;
import com.lottefn.collateral.domain.entities.data.CustomUserDetails;
import com.lottefn.collateral.domain.enums.SystemRole;
import com.lottefn.collateral.domain.exceptions.BusinessException;
import com.lottefn.collateral.domain.exceptions.ErrorMessage;
import com.lottefn.collateral.domain.repositories.PermissionRepository;
import com.lottefn.collateral.domain.repositories.RoleRepository;
import com.lottefn.collateral.domain.repositories.UserRepository;
import com.lottefn.collateral.domain.services.BaseAbtractService;
import com.lottefn.collateral.domain.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lottefn.collateral.domain.utils.Constants.COOKIE_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseAbtractService implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final PasswordEncoder encoder;

    private final JwtTokenProvider tokenProvider;

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, ErrorMessage.PASSWORD_INCORRECT);
        }

        String token = tokenProvider.generateToken(new CustomUserDetails(user));

        Cookie cookie = new Cookie(COOKIE_KEY, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return new LoginResponse(token);
    }

    @Override
    public UserInfoResponse getUserInfo(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        if (SystemRole.ADMIN.equals(user.getRole().getName())){
            Role role = new Role();
            role.setName(SystemRole.ADMIN);
            role.setPermissions(permissionRepository.findAll());
            user.setRole(role);
        }
        return modelMapper.map(user, UserInfoResponse.class);
    }

    @Override
    public Page<User> findAll(FilterDTO filterDTO, Pageable pageable) {
        UserFilterDTO userFilterDTO = modelMapper.map(filterDTO, UserFilterDTO.class);
        return userRepository.findAll(userFilterDTO, pageable);
    }

    @Override
    public User findById(HttpServletRequest request, Long id) {
        return findUserById(id);
    }

    @Override
    public User create(HttpServletRequest request, DTO dto) {
        validateDto(dto);
        User user = createUserFromDto(dto);
        return userRepository.save(user);
    }

    @Override
    public User update(HttpServletRequest request, Long id, DTO dto) {
        validateDto(dto);
        User existingUser = findUserById(id);
        updateUserFromDto(existingUser, dto);
        return userRepository.save(existingUser);
    }

    @Override
    public boolean delete(HttpServletRequest request, Long id) {
        User user = findUserById(id);
        user.setStatus("Inactive");
        userRepository.save(user);
        return true;
    }

    private void validateDto(DTO dto) {
        if (dto == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, ErrorMessage.REQUEST_NOT_EMPTY);
        }
    }

    private User createUserFromDto(DTO dto) {
        UserRequest userRequest = modelMapper.map(dto, UserRequest.class);
        User user = modelMapper.map(dto, User.class);
        configureUser(user, userRequest, 1);
        return user;
    }

    private void updateUserFromDto(User user, DTO dto) {
        UserRequest userRequest = modelMapper.map(dto, UserRequest.class);
        BeanUtils.copyProperties(userRequest, user, "id");
        configureUser(user, userRequest, 2);
    }

    private void configureUser(User user, UserRequest userRequest, int action) {
        List<String> usernameLst = userRepository.findAllUsernames();
        List<String> emailLst = userRepository.findAllEmail();
        if (usernameLst.contains(userRequest.getUsername()) && action == 1) {
            throw new BusinessException(HttpStatus.CONFLICT, ErrorMessage.DUPLICATE_USER);
        }
        if (emailLst.contains(userRequest.getEmail()) && action == 1) {
            throw new BusinessException(HttpStatus.CONFLICT, ErrorMessage.DUPLICATE_EMAIL);
        }
        Role role = findRoleById(userRequest.getRole());
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getStatus() == null) {
            user.setStatus("Active");
        }

    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));
    }

    private Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorMessage.ROLE_NOT_FOUND));
    }
}
