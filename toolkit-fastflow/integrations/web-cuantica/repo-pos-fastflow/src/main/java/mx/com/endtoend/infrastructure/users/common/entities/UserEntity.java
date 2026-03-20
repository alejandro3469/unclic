package mx.com.endtoend.infrastructure.users.common.entities;

import lombok.*;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_number", unique = false, nullable = false)
    private Long userNumber;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "first_surname", unique = false, nullable = false)
    private String firstSurname;

    @Column(name = "second_surname", unique = false, nullable = false)
    private String secondSurname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @JoinColumn(name = "id_branch", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BranchEntity branch;

    @Column(name = "session_active")
    boolean sessionActive;

    @Column(name = "time_active")
    private Date timeActive;

    @Column(name = "configuration_complete")
    private boolean configurationComplete;

    @Column(name = "enabled", nullable = false, updatable = true)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

}
