package mx.com.endtoend.domain.reports.sales.closingOperation.dto;

public class AccountingTicketRecord {

	private Long transactionId;

	private String movementType;

	public AccountingTicketRecord(Long transactionId, String movementType) {
		super();
		this.transactionId = transactionId;
		this.movementType = movementType;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	@Override
	public String toString() {
		return "AccountingTicketRecord [transactionId=" + transactionId + ", movementType=" + movementType + "]";
	}

}
