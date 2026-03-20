package mx.com.endtoend.infrastructure.catalogue.address.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "colonies")
public class ColonyEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enable")
    private boolean isEnable;
    @Column(name = "cp", nullable = false)
    private String cp;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "state_code", nullable = false)
    private String stateCode;

    @Column(name = "city")
    private String city;
}
