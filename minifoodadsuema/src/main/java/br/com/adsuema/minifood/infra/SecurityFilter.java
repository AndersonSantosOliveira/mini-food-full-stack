package br.com.adsuema.minifood.infra;

import br.com.adsuema.minifood.config.TokenService;
import br.com.adsuema.minifood.repository.UsuariosRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                // Tenta validar o token e extrair o login (subject)
                var subject = tokenService.getSubject(tokenJWT);
                var usuarios = repository.findByLogin(subject);

                if (usuarios != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuarios, null, usuarios.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Token expirado ou inválido: limpa o contexto ou apenas ignora
                // para que o Spring Security trate como não autenticado nas rotas protegidas
                SecurityContextHolder.clearContext();

                // Opcional: Se quiser que retorne JSON com erro 401 direto no filtro para token inválido/expirado,
                // você pode tratar aqui, mas deixar o context limpo impede o erro 500.
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.replace("Bearer ", "");
        }
        return null;
    }
}