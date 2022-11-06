package com.example.yachtRent.auth;

import com.example.yachtRent.config.SecurityConfiguration;
import com.example.yachtRent.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@Profile("!test")
@Slf4j
public class TokenFilter extends OncePerRequestFilter {
    public static final String AUTH_HEADER = "Authorization";
    public static final String LOGIN_PATH = "/api/v1/users/login";
    public static final String USER_REGISTER_PATH = "/api/v1/users/register";
    public static final String CLIENT_REGISTER_PATH = "/api/v1/client/register";
    public static final String ADMIN_REGISTER_PATH = "/api/v1/users/send-email";
    public static final String ADMIN_REGISTER_PATH_CHECK_HASH = "/api/v1/users/check-link/**";

    public static final String YACHT_PATH = "/api/v1/yachts/**";
    private static final String ADD_ROLE_TO_USER_PATH = "/api/v1/users/add-role";




    private UserService userService;
    private SecurityConfiguration securityConfiguration;

    public TokenFilter(UserService userService, SecurityConfiguration securityConfiguration) {
        this.userService = userService;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if(request.getMethod().equalsIgnoreCase("options")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (
                request.getRequestURI().equals(LOGIN_PATH) ||
                request.getRequestURI().equals(USER_REGISTER_PATH) ||
                request.getRequestURI().equals(ADMIN_REGISTER_PATH) ||
                request.getRequestURI().equals(CLIENT_REGISTER_PATH) ||
                securityConfiguration.allowedPattern(request.getRequestURI(), ADMIN_REGISTER_PATH_CHECK_HASH) ||
                (securityConfiguration.allowedPattern(request.getRequestURI(), YACHT_PATH)&& request.getMethod().equalsIgnoreCase("get"))
            )
        {
            filterChain.doFilter(request, response);
            return;
        }

        var token = request.getHeader(AUTH_HEADER);

        if (token == null || token.isBlank()) {
            response.setStatus(401);
            return;
        }
        token = token.replace("Bearer ", "");

        String[] tokenParts = token.split("_");
        if (tokenParts.length != 2) {
            response.setStatus(401);
            return;
        }
        var id = tokenParts[0];
        var tokenValue = tokenParts[1];
        userService.validateToken(Long.valueOf(id), tokenValue);
        request.setAttribute("userId",Long.parseLong(id));
        String[] roles = {"admin", "client"};
        var checkRole  = Arrays.stream(roles).anyMatch(role-> userService.validateRole(Long.valueOf(id),role));

        if (
                (
                        request.getRequestURI().equals(ADD_ROLE_TO_USER_PATH) ||
                        request.getMethod().equalsIgnoreCase("put") ||
                        request.getMethod().equalsIgnoreCase("delete")

                ) && !checkRole) {
            response.setStatus(403);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
