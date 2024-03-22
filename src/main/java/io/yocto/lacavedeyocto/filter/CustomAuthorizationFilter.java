package io.yocto.lacavedeyocto.filter;

import io.yocto.lacavedeyocto.provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String[] PUBLIC_ROUTES = { "/user/new/password", "/user/login", "/user/verify/code", "/user/register", "/user/refresh/token", "/user/image" };
    public static final String HTTP_OPTIONS_METHOD = "OPTIONS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        try {
            String token = getToken(request);
            Long userId = getUserId(request);
            log.info("userId : " + userId);
            if (tokenProvider.isTokenValid(userId, token)) {
                log.info("ici ca merde ? 0");
                List<GrantedAuthority> authorities = tokenProvider.getAuthorities(token);
                log.info("ici ca merde ? 1");
                Authentication authentication = tokenProvider.getAuthentication(userId, authorities, request);
                log.info("ici ca merde ? 2");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("ici ca merde ? 3");
            } else {
                SecurityContextHolder.clearContext();
                System.out.println("ici ca merde ? 4");
            }
            filter.doFilter(request, response);
        } catch (Exception exception) {
            log.info("error messaGGGeeeeee" + exception);
            log.error(exception.getMessage());
//            processError(request, response, exception);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getHeader(AUTHORIZATION) == null || !request.getHeader(AUTHORIZATION).startsWith(TOKEN_PREFIX) ||
                request.getMethod().equalsIgnoreCase(HTTP_OPTIONS_METHOD) || asList(PUBLIC_ROUTES).contains(request.getRequestURI());
    }

    private Long getUserId(HttpServletRequest request) {
        log.info("token PROVIDER subject id " + tokenProvider.getSubject(getToken(request), request));
        return tokenProvider.getSubject(getToken(request), request);
    }

    private String getToken(HttpServletRequest request) {
        return ofNullable(request.getHeader(AUTHORIZATION))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .map(token -> token.replace(TOKEN_PREFIX, EMPTY)).get();
    }
}
