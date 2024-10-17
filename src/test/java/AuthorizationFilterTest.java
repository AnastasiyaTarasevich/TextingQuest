import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.textingquest.filtres.AuthorizationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthorizationFilterTest {
    private AuthorizationFilter filter;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private FilterChain mockChain;
    private HttpSession mockSession;

    @BeforeEach
    void setUp() {
        filter = new AuthorizationFilter();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockChain = Mockito.mock(FilterChain.class);
        mockSession = Mockito.mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);
    }
    @Test
    void testDoFilter_PublicPath() throws IOException, ServletException {

        when(mockRequest.getRequestURI()).thenReturn("/");


        filter.doFilter(mockRequest, mockResponse, mockChain);


        verify(mockChain).doFilter(mockRequest, mockResponse);
        verify(mockResponse, never()).sendRedirect(any());
    }

    @Test
    void testDoFilter_UserLoggedIn() throws IOException, ServletException {

        when(mockRequest.getRequestURI()).thenReturn("/private");
        when(mockSession.getAttribute("user")).thenReturn(new Object());


        filter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain).doFilter(mockRequest, mockResponse);
        verify(mockResponse, never()).sendRedirect(any());
    }
    @Test
    void testDoFilter_NotLoggedIn_RedirectToLogin() throws IOException, ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/private");
        when(mockSession.getAttribute("user")).thenReturn(null);

        filter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockResponse).sendRedirect("/login");
        verify(mockChain, never()).doFilter(mockRequest, mockResponse);
    }

    @Test
    void testDoFilter_StaticResource() throws IOException, ServletException {

        when(mockRequest.getRequestURI()).thenReturn("/static/styles.css");


        filter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain).doFilter(mockRequest, mockResponse);
        verify(mockResponse, never()).sendRedirect(any());
    }
}
