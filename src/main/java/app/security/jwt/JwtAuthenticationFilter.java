package app.security.jwt;

import app.exception.AuthorizationHeaderNotPresentException;
import app.service.user.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

/**
 * Класс для фильтрации поступающих на сервер запросов.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Список whitelist адресов.
     */
    private final List<String> allowedEndpoints = List.of(
            "/api/v1/auth",
            "/api/v1/swagger-ui",
            "/api/v1/api-docs"
    );

    /**
     * Метод фильтрации запросов.
     *
     * @param request     информация о входящем запросе.
     * @param response    ответ сервера.
     * @param filterChain интерфейс фильтра.
     * @throws IOException      при возникновении ошибок типа I/O.
     * @throws ServletException при возникновении остальных ошибок.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String jwt;
            String username;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                boolean isPathAllowed = allowedEndpoints.stream().anyMatch(str -> request.getServletPath().startsWith(str));
                if (!isPathAllowed) {
                    throw new AuthorizationHeaderNotPresentException("Authorization header not present");
                }
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException | AuthorizationHeaderNotPresentException exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}
