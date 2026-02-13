package com.devsenior.cdiaz.security.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devsenior.cdiaz.security.model.PrincipalInfo;
import com.devsenior.cdiaz.security.util.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener el header Authorization
        var header = request.getHeader("Authorization");

        // 2. Verificar si el header existe y tiene el formato Bearer
        if (header != null && header.startsWith("Bearer ")) {
            // Extra el token
            var token = header.substring(7);

            // 3. Valido el token
            if (jwtUtils.validateToken(token)) {
                // obtener la informacion del token que usaremos en el registro de usuario
                var username = jwtUtils.extractClaim(token, Claims::getSubject);
                var email = jwtUtils.extractClaim(token, (claims) -> claims.get("email", String.class));
                var name = jwtUtils.extractClaim(token, (claims) -> claims.get("name", String.class));
                var hireDate = jwtUtils.extractClaim(token, (claims) -> claims.get("hire_date", String.class));
                var roles = jwtUtils.extractClaim(token, (claims) -> {
                    var result = new ArrayList<SimpleGrantedAuthority>();
                    var info = claims.get("roles", List.class);
                    for (var item : info) {
                        result.add(new SimpleGrantedAuthority("ROLE_" + item.toString()));
                    }
                    return result;
                });

                var principal = new PrincipalInfo(username, name, email, hireDate);
                // var principal = Map.of("username", username, "email", email, "name", name, "hiredate", hireDate);

                // Agrego la informacion al contexto
                var userToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        "",
                        roles);
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }

        }
        // 4. Continuo en el siguiente paso
        filterChain.doFilter(request, response);
    }

}
