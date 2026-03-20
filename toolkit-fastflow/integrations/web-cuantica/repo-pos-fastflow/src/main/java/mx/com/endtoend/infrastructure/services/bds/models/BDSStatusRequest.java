package mx.com.endtoend.infrastructure.services.bds.models;

public class BDSStatusRequest {

	private String token;

	private String transaction_id;

	public BDSStatusRequest() {
		super();
	}

	public BDSStatusRequest(String token, String transaction_id) {
		super();
		this.token = token;
		this.transaction_id = transaction_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	@Override
	public String toString() {
		return "BDSStatusRequest [token=" + token + ", transaction_id=" + transaction_id + "]";
	}

}
