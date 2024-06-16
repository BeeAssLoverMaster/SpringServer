package shkond.server.model.users;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.quizzes.Quiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Builder // Добавление паттерна Builder для создания объектов User
@Getter
@Setter
@NoArgsConstructor // Генерирует конструктор без параметров
@AllArgsConstructor // Генерирует конструктор со всеми параметрами
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true, nullable = true)
    private String username;
    @Column(name = "email", unique = true, nullable = true)
    private String email;
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "user_bio", nullable = true)
    private String bio;
    @Column(name = "profile_image") //nullable по дефолту true, сделать проверку на то, и если true - вставить заглушку
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_category_id")
    private ArtCategory artCategory;
    @Column(name = "points")
    private int points;
    @Column(name = "correct_answers")
    private int correctAnswers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    /**
     * @return роли пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        return authorities;
    }

    public User(String username, String email, String password, String image, int points, int correctAnswers, List<Role> roles, ArtCategory artCategory) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.image = image;
        this.points = points;
        this.correctAnswers = correctAnswers;
        this.roles = roles;
        this.artCategory = artCategory;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}