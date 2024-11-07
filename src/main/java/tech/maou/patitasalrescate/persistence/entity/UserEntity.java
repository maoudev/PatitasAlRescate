package tech.maou.patitasalrescate.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.maou.patitasalrescate.persistence.audit.AuditableEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 9)
    private int phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DogEntity> dogs;
}
