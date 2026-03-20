package mx.com.endtoend.domain.cash.emailReport.dto;

public class EmailReportCashDto {

	private Long id;

	private String email;

	private Integer closeAttemp;

	public EmailReportCashDto() {
		super();
	}

	public EmailReportCashDto(Long id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCloseAttemp() {
		return closeAttemp;
	}

	public void setCloseAttemp(Integer closeAttemp) {
		this.closeAttemp = closeAttemp;
	}

	@Override
	public String toString() {
		return "EmailReportCashDto [id=" + id + ", email=" + email + ", closeAttemp=" + closeAttemp + "]";
	}

}
