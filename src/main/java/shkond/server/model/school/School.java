package shkond.server.model.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shkond.server.model.arts.ArtCategory;
import shkond.server.model.quizzes.Quiz;
import shkond.server.model.users.Role;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "schools")
public class School {
    @Id
    @Column(name = "school_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "school_name")
    private String schoolName;
    /* Отношение "многие-к-одному" между School и ArtCategory */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_category_id")
    private ArtCategory artCategory;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "school_image")
    private String imageName;
    /* Отношение "многие-ко-многим" между School и Program */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "school_programs",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id"))
    private List<Program> programs;
    public School(){};
    public School(String schoolName, ArtCategory artCategory, String description, String city, String street, String imageName, List<Program> programs) {
        this.schoolName = schoolName;
        this.artCategory = artCategory;
        this.description = description;
        this.city = city;
        this.street = street;
        this.imageName = imageName;
        this.programs = programs;
    }
}
