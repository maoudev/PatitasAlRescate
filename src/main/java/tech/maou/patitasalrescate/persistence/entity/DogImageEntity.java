package tech.maou.patitasalrescate.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DogImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dog")
    private DogEntity dog;
}
