package mx.com.endtoend.infrastructure.warehouse.common.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "F0006")
public class F0006 {

    @Id
    @Column(name = "MCMCU")
    private String mcmcu;

    @Column(name = "MCDC")
    private String mcdc;

    @Column(name = "MCCO")
    private String mcco;

    @Column(name = "MCSTYL")
    private String mcstyl;

    @Column(name = "MCAN8")
    private BigDecimal mcan8;

    @Column(name = "MCDL01")
    private String mcdl01;

    @Column(name = "MCRP03")
    private String mcrp03;

    @Column(name = "MCRP06")
    private String mcrp06;

    @Column(name = "MCRP31")
    private String mcrp31;
}
