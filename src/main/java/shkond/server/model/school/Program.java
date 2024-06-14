package shkond.server.model.school;

import jakarta.persistence.*;
import lombok.*;
import shkond.server.model.arts.ArtCategory;

@Entity
@Getter
@Setter
@Table(name = "programs")
public class Program {
    @Id
    @Column(name = "program_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "program_name")
    private String programName;
    /* Отношение "многие-к-одному" между Program и ArtCategory */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_category_id")
    private ArtCategory artCategory;
    public Program() {}
    public Program(String programName, ArtCategory artCategory) {
        this.programName = programName;
        this.artCategory = artCategory;
    }
}
