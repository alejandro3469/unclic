package mx.com.endtoend.infrastructure.recharges.calzada.entities;

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
@Table(name = "recharge_operation_traces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperationTraceSeliaEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "executionDate", nullable = false)
	private Date executionDate;
	
	@Column(name = "request", nullable = false, length = 500)
	private String request;
	
	@Column(name = "response", nullable = false, length = 500)
	private String response;

}
