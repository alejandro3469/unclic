package mx.com.endtoend.infrastructure.catalogue.orders.common.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderEntity;

@Entity
@Table(name = "status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatusEntity {
	
	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name = "code", nullable = false)
	 private String code;
	 
	 @Column(name = "description", nullable = false)
	 private String description;

	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
	 private List<OrderEntity> orders;
	 
	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
	 private List<SaleOrderEntity> saleOrders;
}
