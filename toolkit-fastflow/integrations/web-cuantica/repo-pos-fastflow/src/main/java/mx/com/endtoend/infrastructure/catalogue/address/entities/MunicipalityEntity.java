package mx.com.endtoend.infrastructure.catalogue.address.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "municipalities")
public class MunicipalityEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enable")
    private boolean isEnable;
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
    @Column(name = "state_code", nullable = false)
    private String stateCode;

}