package tech.maou.patitasalrescate.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.maou.patitasalrescate.persistence.audit.AuditableEntity;

import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "dogs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DogEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_dog")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String sex;

    @Column(name = "is_vaccinated", nullable = false)
    private boolean vaccinated;

    @Column(name = "is_sterilized", nullable = false)
    private boolean sterilized;

    @Column(name = "is_adopted", columnDefinition = "DEFAULT true", nullable = false)
    private boolean adopted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
    private Set<DogImageEntity> images;

}
