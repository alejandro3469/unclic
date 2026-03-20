package mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;


@Entity
@Data
@Table(name = "F0118", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F0118 {
    @Id
    private long a7ukid;
    private String a7addz;
    private String a7add1;
    private String a7add2;
    private String a7add3;
    private String a7add4;
    private String a7user;
    private String a7pid;
    private long a7upmj;
    private long a7upmt;
    private String a7mkey;
    private String a7cty1;
    private String a7coun;
}
