package mx.com.endtoend.infrastructure.warehouse.demo.entities;

import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

import javax.persistence.Table;

@Table(name = "F0006", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F0006 extends mx.com.endtoend.infrastructure.warehouse.common.entities.F0006 {

}
