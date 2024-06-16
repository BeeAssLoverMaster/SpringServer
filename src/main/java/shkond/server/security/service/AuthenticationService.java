package shkond.server.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shkond.server.dto.JwtAuthenticationResponse;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.enums.EnumRole;
import shkond.server.model.users.Role;
import shkond.server.model.users.User;
import shkond.server.repository.arts.ArtCategoryRepository;
import shkond.server.repository.users.RoleRepository;
import shkond.server.repository.users.UserRepository;
import shkond.server.request.users.SignInRequest;
import shkond.server.request.users.SignUpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ArtCategoryRepository artCategoryRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    UserRepository userRepository;

    @Value("${image.profiles-dir}")
    private String profileDir;

    @Value("${image.assets-dir}")
    private String assetDir;

    public String getUsernameByEmail(String email) {
        User user = userService.getByEmail(email);
        return user != null ? user.getUsername() : null;
    }

    public JwtAuthenticationResponse signUp(SignUpRequest request) throws IOException {

        ArtCategory artCategory = artCategoryRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ArtCategory not found"));

        List<Role> rolesList = new ArrayList<>();

        Role userRole = roleRepository.findByRole(EnumRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        rolesList.add(userRole);

        File file = new File(assetDir + "no_profile_img.png");
        FileInputStream inputStream = new FileInputStream(file);

        MultipartFile multipartFile;
        try {
            multipartFile = new MockMultipartFile("file",
                    file.getName(), "text/plain", inputStream);
        } finally {
            // Закрываем поток, чтобы избежать утечки ресурсов
            inputStream.close();
        }

        String defaultImage = imageService.saveImage(multipartFile, profileDir);

        User user = new User(request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                defaultImage,
                0,
                0,
                rolesList,
                artCategory
                );

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request, boolean isWeb) {
        String username = getUsernameByEmail(request.getEmail());

        if (username == null) {
            throw new UsernameNotFoundException("Неверный email или пароль");
        }

        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);

        if (isWeb && !user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Доступ запрещен");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public User getUserFromToken(String token) {
        String username = jwtService.extractUserName(token.replace("Bearer", ""));
        return userService.getByUsername(username);
    }
}
