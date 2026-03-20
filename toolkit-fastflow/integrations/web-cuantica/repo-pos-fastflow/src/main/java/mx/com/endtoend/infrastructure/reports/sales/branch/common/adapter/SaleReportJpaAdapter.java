package mx.com.endtoend.infrastructure.reports.sales.branch.common.adapter;

import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.GenericSaleReportRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.common.factory.SaleReportRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.reports.sales.branch.dto.BranchSaleReportDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleReportJpaAdapter implements SaleReportPersistencePort {

	@Autowired
	private SaleReportRepositoryFactory saleReportRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(SaleReportJpaAdapter.class);

	/**
	 * Método que se encarga de obtener la impleentación concreta de la interfaz
	 * GenericSaleReportRepository, para la generación del reporte venta sucursal
	 * por compañía
	 */
	@Override
	public ResponseModel generateReportBySaleBranchAndCompanyCode(BranchSaleReportDto branchSaleReport, String format,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateReportBySaleBranchAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ branchSaleReport: %s , companyCode: %s ] ", idOperation,
				branchSaleReport.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateReportBySaleBranchAndCompanyCode(branchSaleReport, format, idOperation);
	}

	/**
	 * Método que se engarga de obtener la implementación concreta de la interfaz
	 * GenericSaleReportRepository para la recuperación de los datos operativos de
	 * cobros con efectivo por compañía
	 */
	@Override
	public ResponseModel searchPaymentCashSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaymentCashSummaryByCompanyCodeAndParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleReportParams: %s , companyCode: %s ] ", idOperation,
				saleReportParams.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchPaymentCashSummaryByParams(saleReportParams, idOperation));

	}

	/**
	 * Método que se engarga de obtener la implementación concreta de la interfaz
	 * GenericSaleReportRepository para la recuperación de los datos operativos de
	 * cobros con transferencia por compañía
	 */
	@Override
	public ResponseModel searchPaymentTransferSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaymentTransferSummaryByCompanyCodeAndParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleReportParams: %s , companyCode: %s ] ", idOperation,
				saleReportParams.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchPaymentTransferSummaryByParams(saleReportParams, idOperation));
	}

	/**
	 * Método que se engarga de obtener la implementación concreta de la interfaz
	 * GenericSaleReportRepository para la recuperación de los datos operativos de
	 * cobros con cheques por compañía
	 */
	@Override
	public ResponseModel searchPaymentCheckSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaymentCheckSummaryByCompanyCodeAndParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleReportParams: %s , companyCode: %s ] ", idOperation,
				saleReportParams.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchPaymentCheckSummaryByParams(saleReportParams, idOperation));
	}

	/**
	 * Método que se engarga de obtener la implementación concreta de la interfaz
	 * GenericSaleReportRepository para la recuperación de los datos operativos de
	 * cobros con tarjetas de crédito por compañía
	 */
	@Override
	public ResponseModel searchPaymentCreditCardSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaymentCreditCardSummaryByCompanyCodeAndParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleReportParams: %s , companyCode: %s ] ", idOperation,
				saleReportParams.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchPaymentCreditCardSummaryByParams(saleReportParams, idOperation));
	}

	/**
	 * Método que se engarga de obtener la implementación concreta de la interfaz
	 * GenericSaleReportRepository para la recuperación de los datos operativos de
	 * cobros con notas de crédito por compañía
	 */
	@Override
	public ResponseModel searchPaymentCreditNoteSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaymentCreditNoteSummaryByCompanyCodeAndParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleReportParams: %s , companyCode: %s ] ", idOperation,
				saleReportParams.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchPaymentCreditNoteSummaryByParams(saleReportParams, idOperation));
	}

	@Override
	public ResponseModel searchPaymentCreditSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaymentCreditSummaryByCompanyCodeAndParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleReportParams: %s , companyCode: %s ] ", idOperation,
				saleReportParams.toString(), companyCode));
		GenericSaleReportRepository repository = saleReportRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchPaymentCreditSummaryByParams(saleReportParams, idOperation));
	}

}
