package shkond.server.model.school;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "school_works")
public class SchoolWork {
    @Id
    @Column(name = "school_work_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_name", nullable = true)
    private String image;
    /* Отношение "многие-к-одному" между SchoolWork и School */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    @Column(name = "author")
    private String author;
}
