package mx.com.endtoend.domain.reports.sales.models;

import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticlePersistencePort;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportPersistencePort;
import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeePersistencePort;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationPersistencePort;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;

public class SaleReportInterfaceService {

	private PaymentPersistencePort paymentPersistencePort;

	private ClientPersistencePort clientPersistencePort;

	private ClientOracleServicePort clientOracleServicePort;

	private BranchPersistencePort branchPersistencePort;

	private SaleReportPersistencePort saleReportPersistencePort;

	private ReportSaleBranchEmployeePersistencePort reportSaleBranchEmployeePersistencePort;

	private ReportSaleArticlePersistencePort reportSaleArticlePersistencePort;

	private ReportClosingOperationPersistencePort reportClosingOperationPersistencePort;

	private UserPersistencePort userPersistencePort;

	public SaleReportInterfaceService(PaymentPersistencePort paymentPersistencePort,
			ClientPersistencePort clientPersistencePort, ClientOracleServicePort clientOracleServicePort,
			BranchPersistencePort branchPersistencePort) {
		super();
		this.paymentPersistencePort = paymentPersistencePort;
		this.clientPersistencePort = clientPersistencePort;
		this.clientOracleServicePort = clientOracleServicePort;
		this.branchPersistencePort = branchPersistencePort;
	}

	public SaleReportInterfaceService(PaymentPersistencePort paymentPersistencePort,
			ClientPersistencePort clientPersistencePort, ClientOracleServicePort clientOracleServicePort,
			BranchPersistencePort branchPersistencePort, UserPersistencePort userPersistencePort) {
		super();
		this.paymentPersistencePort = paymentPersistencePort;
		this.clientPersistencePort = clientPersistencePort;
		this.clientOracleServicePort = clientOracleServicePort;
		this.branchPersistencePort = branchPersistencePort;
		this.userPersistencePort = userPersistencePort;
	}

	public BranchPersistencePort getBranchPersistencePort() {
		return branchPersistencePort;
	}

	public void setBranchPersistencePort(BranchPersistencePort branchPersistencePort) {
		this.branchPersistencePort = branchPersistencePort;
	}

	public PaymentPersistencePort getPaymentPersistencePort() {
		return paymentPersistencePort;
	}

	public ClientPersistencePort getClientPersistencePort() {
		return clientPersistencePort;
	}

	public ClientOracleServicePort getClientOracleServicePort() {
		return clientOracleServicePort;
	}

	public void setPaymentPersistencePort(PaymentPersistencePort paymentPersistencePort) {
		this.paymentPersistencePort = paymentPersistencePort;
	}

	public void setClientPersistencePort(ClientPersistencePort clientPersistencePort) {
		this.clientPersistencePort = clientPersistencePort;
	}

	public void setClientOracleServicePort(ClientOracleServicePort clientOracleServicePort) {
		this.clientOracleServicePort = clientOracleServicePort;
	}

	public SaleReportPersistencePort getSaleReportPersistencePort() {
		return saleReportPersistencePort;
	}

	public void setSaleReportPersistencePort(SaleReportPersistencePort saleReportPersistencePort) {
		this.saleReportPersistencePort = saleReportPersistencePort;
	}

	public ReportSaleBranchEmployeePersistencePort getReportSaleBranchEmployeePersistencePort() {
		return reportSaleBranchEmployeePersistencePort;
	}

	public void setReportSaleBranchEmployeePersistencePort(
			ReportSaleBranchEmployeePersistencePort reportSaleBranchEmployeePersistencePort) {
		this.reportSaleBranchEmployeePersistencePort = reportSaleBranchEmployeePersistencePort;
	}

	public UserPersistencePort getUserPersistencePort() {
		return userPersistencePort;
	}

	public void setUserPersistencePort(UserPersistencePort userPersistencePort) {
		this.userPersistencePort = userPersistencePort;
	}

	public ReportSaleArticlePersistencePort getReportSaleArticlePersistencePort() {
		return reportSaleArticlePersistencePort;
	}

	public void setReportSaleArticlePersistencePort(ReportSaleArticlePersistencePort reportSaleArticlePersistencePort) {
		this.reportSaleArticlePersistencePort = reportSaleArticlePersistencePort;
	}

	public ReportClosingOperationPersistencePort getReportClosingOperationPersistencePort() {
		return reportClosingOperationPersistencePort;
	}

	public void setReportClosingOperationPersistencePort(
			ReportClosingOperationPersistencePort reportClosingOperationPersistencePort) {
		this.reportClosingOperationPersistencePort = reportClosingOperationPersistencePort;
	}

}
