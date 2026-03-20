package mx.com.endtoend.infrastructure.company.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Entity
@Table(name = "email_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailConfigurationEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_code", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private CompanyCodes companyCode;
	
	@Column(name = "smtp_host", nullable = false)
	private String smtpHost;
	
	@Column(name = "smtp_port", nullable = false)
	private String smtpPort;
	
	@Column(name = "ssl_port", nullable = true)
	private String sslPort;
	
	@Column(name = "fom_email", nullable = false)
	private String fromEmail;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "encryption_protocol", nullable = false)
	private String encryptionProtocol;
	
	@Column(name = "encryption_protocol_version", nullable = false)
	private String encriptionProtocolVersion;
	
	@Column(name = "enable_auth", nullable = false)
	private boolean isEnableAuthentication;
	
	@Column(name = "active_configuration", nullable = false)
	private boolean isActiveConfiguration;
}
