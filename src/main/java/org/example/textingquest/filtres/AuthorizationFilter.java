package org.example.textingquest.filtres;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static org.example.textingquest.utils.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(HOME,LOGIN,REGISTRATION);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        System.out.println("Requested URI: " + uri);
        System.out.println("URI: " + uri + ", isPublicPath: " + isPublicPath(uri));

        if (isPublicPath(uri) || isUserLoggedIn(servletRequest) || isStaticResource(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::equals);
    }
    private boolean isStaticResource(String uri) {
        return uri.startsWith("/resources/") || uri.startsWith("/static/") || uri.startsWith("/assets/") || uri.startsWith("/css/") || uri.startsWith("/js/");
    }
}
