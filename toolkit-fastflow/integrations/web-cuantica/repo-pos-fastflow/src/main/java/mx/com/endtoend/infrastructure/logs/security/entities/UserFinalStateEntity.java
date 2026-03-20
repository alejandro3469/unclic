package mx.com.endtoend.infrastructure.logs.security.entities;

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
@Table(name = "users_final_states_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFinalStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_summary_id")
	private Long userSummaryId;

	@Column(name = "user_number")
	private Long userNumber;

	@Column(name = "name")
	private String name;

	@Column(name = "first_surname")
	private String firstSurname;

	@Column(name = "second_surname")
	private String secondSurname;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "branch")
	private String branch;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "is_session_active")
	boolean isSessionActive;

	@Column(name = "roles")
	private String roles;

	@Column(name = "is_configuration_complete")
	private Boolean isConfigurationComplete;
}
