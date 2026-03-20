package mx.com.endtoend.infrastructure.catalogue.address.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "states")
public class StateEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enable")
    private boolean isEnable;
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name")
    private String name;
}
