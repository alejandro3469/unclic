package mx.com.endtoend.infrastructure.logs.security.entities;

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

@Entity
@Table(name = "users_summary_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user", nullable = false)
	private String user;

	@Column(name = "updated_date", nullable = false)
	private Date updatedDate;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "username", nullable = false)
	private String username;
}
