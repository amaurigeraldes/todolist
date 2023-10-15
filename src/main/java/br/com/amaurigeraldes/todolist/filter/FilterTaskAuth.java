package br.com.amaurigeraldes.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.amaurigeraldes.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {

            // 1 - Pegar autenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");
            
            // Excluindo a palavra Basic e os espaços em branco
            var authEnconded = authorization.substring("Basic".length()).trim();
            
            // Fazendo a Decodificação
            byte[] authDecode = Base64.getDecoder().decode(authEnconded);

            // Converter o Array de Bytes para uma String
            var authString = new String(authDecode);

            // Separando o nome do usuário da senha
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // 2 - Validar usuário
            var user = this.userRepository.findByUsername(username);
            if(user == null) {
                response.sendError(401);
            } else {
                // 3 - Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified == true) {
                    // 4 - Segue viagem
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
