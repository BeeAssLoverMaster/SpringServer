package shkond.server.model.school;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "teachers")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* Отношение "многие-к-одному" между Teacher и School */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    @Column(name = "teacher_name")
    private String teacherName;
    @Column(name = "teacher_image")
    private String teacherImage;
}
