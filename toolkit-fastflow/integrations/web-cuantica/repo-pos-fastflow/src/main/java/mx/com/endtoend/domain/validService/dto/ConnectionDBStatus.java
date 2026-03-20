package mx.com.endtoend.domain.validService.dto;

public class ConnectionDBStatus {

	private String database;

	private String Status;

	public String getDatabase() {
		return database;
	}

	public String getStatus() {
		return Status;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "ConnectionDBStatus [database=" + database + ", Status=" + Status + "]";
	}

}
