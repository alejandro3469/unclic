package mx.com.endtoend.infrastructure.orders.common.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderHistoryEntity {


	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "branch_code", unique = false, nullable = false)
	private String branchCode;

	@Column(name = "order_type", unique = false, nullable = false)
	private String orderType;

	@Column(name = "order_number", unique = false, nullable = false)
	private BigDecimal orderNumber;

	@Column(name = "modification_date", unique = false, nullable = false)
	private Date modificationDate;

	@Column(name = "action", unique = false, nullable = false)
	private String action;

	@Column(name = "username", unique = false, nullable = false)
	private String username;

	@Column(name = "user_number", unique = false, nullable = false)
	private Long userNumber;

	@Column(name = "params", unique = false, nullable = false)
	private String params;

	@Column(name = "retention_order", nullable = false)
	private boolean isRetentionOrder;
	
}
