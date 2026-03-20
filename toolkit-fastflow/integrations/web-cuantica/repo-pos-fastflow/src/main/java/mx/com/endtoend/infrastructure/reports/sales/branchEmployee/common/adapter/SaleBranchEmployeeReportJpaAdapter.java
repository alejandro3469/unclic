package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.adapter;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.*;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.GenericSaleBranchEmployeeRepository;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.factory.SaleBranchEmployeeRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

import java.util.List;

public class SaleBranchEmployeeReportJpaAdapter implements ReportSaleBranchEmployeePersistencePort {

	@Autowired
	private SaleBranchEmployeeRepositoryFactory saleBranchEmployeeRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(SaleBranchEmployeeReportJpaAdapter.class);

	@Override
	public ResponseModel generateSaleBranchEmployeeReportByCompanyCode(ReportBranchEmployeeDto reportBranchEmployee,
			String format, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateSaleBranchEmployeeReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ reportBranchEmployee: %s , companyCode: %s ] ", idOperation,
				reportBranchEmployee.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateSaleBranchEmployeeReport(reportBranchEmployee, format, idOperation);
	}

    @Override
    public ResponseModel generateSaleBranchEmployeeReportByCompanyCodeV2(List<ReportSalesDto> reportBranchEmployee,
                                                                         String format, String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT generateSaleBranchEmployeeReportByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS:[ reportBranchEmployee: %s , companyCode: %s ] ", idOperation,
                reportBranchEmployee.toString(), companyCode));
        GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
                .getRepositoryByCompanyCode(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        return repository.generateSaleBranchEmployeeReportV2(reportBranchEmployee, format, idOperation);
    }

	@Override
	public ResponseModel generateSCreditNotesReportByParams(List<ReportNotesDto> reportBranchEmployee,
															String format, String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT generateSaleBranchEmployeeReportByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS:[ reportBranchEmployee: %s , companyCode: %s ] ", idOperation,
                reportBranchEmployee.toString(), companyCode));
        GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
                .getRepositoryByCompanyCode(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        return repository.generateCreditNotesReport(reportBranchEmployee, format, idOperation);
    }

	@Override
	public ResponseModel searchSaleCashByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchSaleCashByEmployeeAndParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
				saleBranchEmployeeParams.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchSaleCashByEmployeeAndParams(saleBranchEmployeeParams, idOperation));
	}

	@Override
	public ResponseModel searchSaleCreditCardByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchSaleCreditCardByEmployeeAndParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
				saleBranchEmployeeParams.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(
				repository.searchSaleCreditCardByEmployeeAndParams(saleBranchEmployeeParams, idOperation));
	}

	@Override
	public ResponseModel searchSaleCredittNoteByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchSaleCredittNoteByEmployeeAndParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
				saleBranchEmployeeParams.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(
				repository.searchSaleCredittNoteByEmployeeAndParams(saleBranchEmployeeParams, idOperation));
	}

	@Override
	public ResponseModel searchSaleTransferByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchSaleTransferByEmployeeAndParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
				saleBranchEmployeeParams.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(
				repository.searchSaleTransferByEmployeeAndParams(saleBranchEmployeeParams, idOperation));
	}

	@Override
	public ResponseModel searchSaleCheckByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchSaleCheckByEmployeeAndParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
				saleBranchEmployeeParams.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchSaleCheckByEmployeeAndParams(saleBranchEmployeeParams, idOperation));
	}

	@Override
	public ResponseModel searchSaleCreditByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchSaleCreditByEmployeeAndParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
				saleBranchEmployeeParams.toString(), companyCode));
		GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.searchSaleCreditByEmployeeAndParams(saleBranchEmployeeParams, idOperation));
	}

    @Override
    public ResponseModel searchSaleCreditByEmployeeAndParamsAndCompanyCodeV2(
            SaleAndNotesReportParamsDto saleBranchEmployeeParams, String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT searchSaleCreditByEmployeeAndParamsAndCompanyCodeV2()", idOperation));
        LOG.info(String.format("%s PARAMS:[ saleBranchEmployeeParams: %s , companyCode: %s ] ", idOperation,
                saleBranchEmployeeParams.toString(), companyCode));
        GenericSaleBranchEmployeeRepository repository = saleBranchEmployeeRepositoryFactory
                .getRepositoryByCompanyCode(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
		String format = saleBranchEmployeeParams.getFormat();
		if("notas".equals(format)) {
			return new ResponseModel(repository.findCreditNotesByParams(saleBranchEmployeeParams, idOperation));
		}
        else {
            return new ResponseModel(repository.searchSaleCreditByEmployeeAndParamsV2(saleBranchEmployeeParams, idOperation));
        }
    }
}
