package mx.com.endtoend.domain.debug.dto;

import java.util.Date;

public class DebugDto {

	private String module;

	private String idInstance;

	private String companyCode;

	private String log;

	private Date date;

	private String idOperation;

	public DebugDto() {
		super();
	}

	public DebugDto(String module, String idInstance, String companyCode, String log, String idOperation) {
		super();
		this.module = module;
		this.idInstance = idInstance;
		this.companyCode = companyCode;
		this.log = log;
		this.idOperation = idOperation;
		this.date = new Date();
	}

	public String getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getModule() {
		return module;
	}

	public String getIdInstance() {
		return idInstance;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public Date getDate() {
		return date;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setIdInstance(String idInstance) {
		this.idInstance = idInstance;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "DebugDto [module=" + module + ", idInstance=" + idInstance + ", companyCode=" + companyCode + ", log="
				+ log + ", date=" + date + ", idOperation=" + idOperation + "]";
	}
}
