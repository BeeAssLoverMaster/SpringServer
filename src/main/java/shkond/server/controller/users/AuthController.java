package shkond.server.controller.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import shkond.server.dto.JwtAuthenticationResponse;
import shkond.server.model.users.User;
import shkond.server.request.users.SignInRequest;
import shkond.server.request.users.SignUpRequest;
import shkond.server.security.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
/* Контроллер для аутентификации */
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    /**
     * Регистрация нового пользователя
     * @param request запрос на регистрацию
     * @return токен польлзователя
     * @throws IOException в случае ошибки ввода-вывода
     */
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) throws IOException {
        return authenticationService.signUp(request);
    }
    /**
     * Вход пользователя в систему
     * @param request запрос на вход
     * @return токен пользователя
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request,
                                    @RequestParam(defaultValue = "false") boolean isWeb) {
        try {
            JwtAuthenticationResponse response = authenticationService.signIn(request, isWeb);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Доступ запрещен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный email или пароль");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            User user = authenticationService.getUserFromToken(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}


