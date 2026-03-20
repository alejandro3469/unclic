package mx.com.endtoend.infrastructure.debug.calzada.mysql.entities;

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
@Table(name = "debug_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DebugEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(name = "date")
	private Date date;

	@Column(name = "module")
	private String module;

	@Column(name = "id_instance")
	private String idInstance;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "log")
	private String log;
	
	@Column(name = "id_operation")
	private String idOperation;

}
